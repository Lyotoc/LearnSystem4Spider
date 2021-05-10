package org.Lyoto.Spider.Strategy.impl;

/**
 * @author Lyoto
 * @Date 2021-04-28 20:28
 **/


import com.alibaba.fastjson.JSON;
import org.Lyoto.Pojo.CourseInfo;
import org.Lyoto.Spider.PoJo.MocCourseQueryVo;
import org.Lyoto.Spider.PoJo.MocTermDto;
import org.Lyoto.Spider.PoJo.VideoSignDto;
import org.Lyoto.Spider.Strategy.FilterProcessor;
import org.Lyoto.Spider.Strategy.ProcessStrategy;
import org.Lyoto.Utils.MOOCUtils.Catgory;
import org.Lyoto.Utils.MOOCUtils.MOOCEntity;
import org.Lyoto.Utils.StrUtils;
import org.Lyoto.Utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.*;

/**
 * 装饰者，用来装饰book的迭代爬取功能
 */
@Component
public class IteratorProcessor extends FilterProcessor {

    //存储当前页面的set,使用Hashset过滤
    private Set<CourseInfo> daoCache = new HashSet<>(32);

    public IteratorProcessor(@Qualifier("categoryInfo") ProcessStrategy processStrategy) {
        super(processStrategy);
    }

    @Autowired
    UrlUtils urlUtils;
    @Autowired
    Catgory catgory;
    @Autowired
    MOOCEntity moocEntity;

    @Override
    public void doProcess(Page page) {
        //保存二级类目Json
        if (StringUtils.startsWith(page.getRequest().getUrl(), "https://www.icourse163.org/web/j/channelBean.listMocChannelCategoryRel.rpc?csrfKey=")) {
            this.parseCategory(page);
        }
//        对二级目录响应体数据进行解析
        if(StringUtils.startsWith(page.getRequest().getUrl(),"https://www.icourse163.org/web/j/mocSearchBean.searchCourseCardByChannelAndCategoryId.rpc?csrfKey=")){
            this.praseSecondCategory(page);
        }
//获取单节课程的详细章节Id,并进行解析加入下一轮爬取队列
        if(StringUtils.startsWith(page.getRequest().getUrl(),"https://www.icourse163.org/dwr/call/plaincall/CourseBean.getMocTermDto.dwr")){
            this.parseCourseChapter(page);
        }
//        对详细课程章节进行视频链接爬取
        if(StringUtils.startsWith(page.getRequest().getUrl(),"https://www.icourse163.org/web/j/resourceRpcBean.getResourceToken.rpc?csrfKey=")){
            this.videoInfo(page);
        }
        if(StringUtils.startsWith(page.getRequest().getUrl(),"https://vod.study.163.com/eds/api/v1/vod/video")){
           this.processStrategy.doProcess(page);
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
            String toJSONString = "mocCourseQueryVo="+ JSON.toJSON(mocCourseQueryVo);
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
//        List<String> courseList1 = page.getJson().jsonPath("$.result.list[*].mocCourseKyCardBulkPurchaseVo").all();
        if(courseList.get(0).equals("null")){
//            courseList = catgory.moneyFilter(courseList1);
            return;
        }

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
            String imageUrl = StrUtils.catchTarget(new JsonPathSelector("$.bigPhoto").select(course),".+?(?=\\?)");
            courseInfo.setImageUrl(imageUrl);
            //老师名
            courseInfo.setTeacherName(new JsonPathSelector("$.teacherName").select(course));
            //学校
            courseInfo.setSchoolName(new JsonPathSelector("$.schoolName").select(course));
            //当前任期
            courseInfo.setCurrentTermId(Integer.valueOf(new JsonPathSelector("$.currentTermId").select(course)));
            //发布日期
            Long time = Long.valueOf(new JsonPathSelector("$.firstPublishTime").select(course));
            if(time==0){
                time = new Date().getTime();
            }
            Timestamp timestamp = new Timestamp(time);
            courseInfo.setPublishDate(timestamp);
            //将封装对象数据传入持久层缓存
            daoCache.add(courseInfo);
            //进行下一步，对课程的m3u8详细数据进行爬取
        }
        ArrayList<Request> requests = this.addCourseChapter(daoCache);
        for(Request request : requests){
            page.addTargetRequest(request);
        }
        Set<CourseInfo> target = new HashSet<>();
        target.addAll(daoCache);
        page.putField("courseInfoList",target);
        daoCache.clear();
        //爬取下一页数据
        this.addNextPage(page,bodyContent);
        //否

    }

    /**
     *  下一页
     * @param page
     * @param bodyContent
     */
    public void addNextPage(Page page,String bodyContent){
        //获取query
        String queryData = page.getJson().jsonPath("$.result.query").get();
        //当前页面数
        Integer pageIndex = Integer.valueOf(new JsonPathSelector("$.pageIndex").select(queryData));
        //该类目下的总页面数
        Integer totlePageCount = Integer.valueOf(new JsonPathSelector("$.totlePageCount").select(queryData));
        //判断当前页面是否小于总页面数
        if(pageIndex < totlePageCount) {
            MocCourseQueryVo mocCourseQueryVo = JSON.parseObject(StrUtils.catchTarget(bodyContent,"(?<=\\=).+"),MocCourseQueryVo.class);
            //是
            mocCourseQueryVo.setPageIndex(++pageIndex);
            Request nextRequest = page.getRequest();
            String toJSONString = "mocCourseQueryVo="+JSON.toJSON(mocCourseQueryVo);
            HttpRequestBody httpRequestBody = new HttpRequestBody().json(toJSONString,"UTF8");
            //新建request加入spider
            nextRequest.setRequestBody(httpRequestBody);
            //requestBody下添加页面总数
            page.addTargetRequest(nextRequest);

        }
    }
    /***
     *对每个课程的章节获取，并加入下一轮Request
     * @return
     */
    public ArrayList<Request> addCourseChapter(Set<CourseInfo> courseInfoHashSet){
        ArrayList<Request> courseChapterList = new ArrayList<>();
        if(courseInfoHashSet.isEmpty()){
            return null;
        }
        for(CourseInfo courseInfo :courseInfoHashSet){
            //封装的请求体
            byte[] requestBody = StringUtils.getBytes(new MocTermDto(moocEntity.setCookies()[0], courseInfo.getCurrentTermId()).toString(), Charset.forName("UTF8"));
            //请求
            Request request = urlUtils.getMocTermDto_Post();
            HttpRequestBody httpRequestBody = HttpRequestBody.custom(requestBody,"text/plain","UTF-8");

            request.setRequestBody(httpRequestBody);
            courseChapterList.add(request);
        }
        return courseChapterList;
    }



    /**
     * 对课程章节爬取
     * @param page
     */
    public void parseCourseChapter(Page page){
        String rawText = page.getRawText();
        //正则匹配lessonId,加入List
        ArrayList<String> lessonList = StrUtils.catchTargetList(rawText, "(s\\d+\\.anchorQuestions.+\\n?)");
        for(String s:lessonList){
            //拿到capterId
            String chapterId = StrUtils.catchTarget(s, "(?<=\\w\\d\\.id=)(.+?)(?=\\;)");
            Request videoSignDto_post = urlUtils.getVideoSignDto_post();
            //表单数据
            Map<String,Object> formMap = new HashMap<>();
            formMap.put("bizId",chapterId);
            formMap.put("bizType","1");
            formMap.put("contentType","1");
            HttpRequestBody httpRequestBody = HttpRequestBody.form(formMap,"UTF8");
            String currentTermId = StrUtils.catchTarget(s, "(?<=termId=)(.+?)(?=\\;)");
            videoSignDto_post.putExtra("currentTermId",currentTermId);
            videoSignDto_post.setRequestBody(httpRequestBody);
            page.addTargetRequest(videoSignDto_post);
        }
    }

    /**
     * 保存视视频的相关信息
     * @param page
     */
    public void videoInfo(Page page){
        Json json = page.getJson();
        Integer codeStatus = Integer.valueOf(json.jsonPath("$.code").get());
        //非正常状态直接结束
        if(codeStatus != 0){
            return;
        }
        List<String> videoSignDtoList = json.jsonPath("$.result.videoSignDto").all();
        if(videoSignDtoList.isEmpty()){
            return;
        }
        for(String videoSignDtoStr:videoSignDtoList){
            //反序列化videoSignDto
            VideoSignDto videoSignDto = JSON.parseObject(videoSignDtoStr, VideoSignDto.class);
            //发起m3u8文件获取的Get请求
                //设置相关的Url
            String getVideoUrl = "https://vod.study.163.com/eds/api/v1/vod/video?videoId="+videoSignDto.getVideoId()
                    +"&signature="+videoSignDto.getSignature()+"&clientType=1";
            Request request =new Request()
                    .setMethod("GET")
                    .setUrl(getVideoUrl)
                    .putExtra("currentTermId",page.getRequest().getExtra("currentTermId"));
            page.addTargetRequest(request);
        }

    }
}