package org.Lyoto.Spider.Strategy.impl;

import com.alibaba.fastjson.JSON;
import org.Lyoto.Pojo.CourseInfo;
import org.Lyoto.Spider.PoJo.MocCourseQueryVo;
import org.Lyoto.Spider.Strategy.ProcessStrategy;
import org.Lyoto.Utils.MOOCUtils.Catgory;
import org.Lyoto.Utils.StrUtils;
import org.Lyoto.Utils.UrlUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Lyoto
 * @Date 2021-04-29 9:21
 **/
@Component
public class CategoryInfo implements ProcessStrategy {
    @Autowired
    UrlUtils urlUtils;
    @Autowired
    Catgory catgory;
    @Override
    public void doProcess(Page page) {
        //保存二级类目Json
        if (StringUtils.startsWith(page.getRequest().getUrl(), "https://www.icourse163.org/web/j/channelBean.listMocChannelCategoryRel.rpc?csrfKey=")) {
        this.parseCategory(page);
    }
//        对二级目录响应体数据进行解析
        if(StringUtils.startsWith(page.getRequest().getUrl(),"https://www.icourse163.org/web/j/mocSearchBean.searchCourseCardByChannelAndCategoryId.rpc?csrfKey=")){

        }
    }

    /***
     对一级类目进行解析，初次加载时将获取的Json数据保存在指定的位置，下次直接在指定位置调用Json爬取二级类目下的课程
     * @param page
     */
    public void parseCategory(Page page){

        //首次加载大类目中的Json数据

            boolean checkSave;
            String catgoryId = StrUtils.catchTarget(StringUtils.toEncodedString(page.getRequest().getRequestBody().getBody(), Charset.forName("utf8")),"(?<=\\=)\\d.+");
            do {
                checkSave = urlUtils.setJsonFile(catgoryId, page.getJson().toString());
            }while(checkSave == false);
            List<String> secondary_UrlNodes = page.getJson().jsonPath("$.result[*]").all();
            for(String secondary_Url : secondary_UrlNodes){
                //首次加载后对数据进行清洗，加入下一轮的request
                MocCourseQueryVo mocCourseQueryVo = new MocCourseQueryVo();
                mocCourseQueryVo.setCategoryId(Long.valueOf(new JsonPathSelector("$.id").select(secondary_Url)));
                mocCourseQueryVo.setCategoryChannelId(Integer.valueOf(catgoryId));
                mocCourseQueryVo.setCategoryName(new JsonPathSelector("$.name").select(secondary_Url));
                String toJSONString = "mocCourseQueryVo="+JSON.toJSON(mocCourseQueryVo);
                HttpRequestBody json = new HttpRequestBody().json(toJSONString,"utf8");
                Request request = urlUtils.mocSearchBeanUrl_Post();
                request.setRequestBody(json);
                page.addTargetRequest(request);
            }
    }

    /**
     * 解析二级类目的响应体，获取课程信息以及一级类目、二级类目Id
     * @param page
     */
    public void praseSecondCategory(Page page){
        String bodyContent = StringUtils.toEncodedString(page.getRequest().getRequestBody().getBody(), Charset.forName("UTF8"));
        //请求体的Json
        String jsonData = StrUtils.catchTarget(bodyContent, "(?<=\\=)(.+)");
        //获取一级类目id
        Integer channelId = Integer.valueOf(new JsonPathSelector("$.categoryChannelId").select(jsonData));
        //赋予响应的一级类目名
        String channelName = catgory.checkChannelId(channelId);
        //获取二级类目Id
        Integer categoryId = Integer.valueOf(new JsonPathSelector("$.categoryId").select(jsonData));
        //获取二级类目名
        String categoryName = new JsonPathSelector("$.categoryName").select(jsonData);
        //解析Json中的list部分
        List<String> courseList = page.getJson().jsonPath("$.result.list[*].mocCourseBaseCardVo").all();
        //for循环遍历list
        for(String course:courseList){
            //对每个分组进行属性填充
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.setChannelId(channelId);
            courseInfo.setChannelName(channelName);
            courseInfo.setCategoryId(categoryId);
            courseInfo.setCategoryName(categoryName);
            //课程Id
            courseInfo.setCourseId(Integer.valueOf(new JsonPathSelector("$.id").select(course)));
            //课程名
            courseInfo.setCourseName(new JsonPathSelector("$.name").select(course));
            //封面图链接
            courseInfo.setImageUrl(new JsonPathSelector("$.bigPhoto").select(course));
            //老师名
            courseInfo.setTeacherName(new JsonPathSelector("$.teacherName").select(course));
            //学校
            courseInfo.setSchoolName(new JsonPathSelector("$.schoolName").select(course));
            //当前任期
            courseInfo.setCurrentTermId(Integer.valueOf(new JsonPathSelector("$.currentTermId").select(course)));
            //发布日期
            Integer time = Integer.valueOf(new JsonPathSelector("$.firstPublishTime").select(course));
            Timestamp timestamp = new Timestamp(time);
            courseInfo.setPublishDate(timestamp);
            page.putField("courseInfo",courseInfo);
        //将封装对象数据传入持久层

    //进行下一步，对课程的m3u8详细数据进行爬取
        }

        //获取query
            //当前页面数
            //该类目下的总页面数
            //判断当前页面是否小于总页面数
                //是
                //新建request加入spider
                    //requestBody下添加页面总数
                //否

    }

    @Override
    public Site addSite() {
        return Site.me();
    }


}
