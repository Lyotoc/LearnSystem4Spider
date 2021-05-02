package org.Lyoto.Spider.Strategy.impl;

/**
 * @author Lyoto
 * @Date 2021-04-28 20:28
 **/


import com.alibaba.fastjson.JSON;
import org.Lyoto.Spider.PoJo.MocCourseQueryVo;
import org.Lyoto.Spider.Strategy.FilterProcessor;
import org.Lyoto.Spider.Strategy.ProcessStrategy;
import org.Lyoto.Utils.MOOCUtils.MOOCHeader;
import org.Lyoto.Utils.StrUtils;
import org.Lyoto.Utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.Selectable;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 装饰者，用来装饰book的迭代爬取功能
 */
@Component
public class IteratorProcessor extends FilterProcessor {

    private Set<String> requestCache = new HashSet<>(32);
    @Autowired
    UrlUtils urlUtils;

    public IteratorProcessor(@Qualifier("catrgoryInfo") ProcessStrategy processStrategy) {
        super(processStrategy);
    }

    @Override
    public void doProcess(Page page) {
        //首次加载大类目中的Json数据--
        if (StringUtils.startsWith(page.getRequest().getUrl(), "https://www.icourse163.org/web/j/channelBean.listMocChannelCategoryRel.rpc?csrfKey=")) {
            List<String> secondary_UrlNodes = page.getJson().jsonPath("$.result[*]").all();
                String catgoryId = StrUtils.catchTarget(StringUtils.toEncodedString(page.getRequest().getRequestBody().getBody(), Charset.forName("utf8")),"(?<=\\=)\\d.+");
            for(String secondary_Url : secondary_UrlNodes){
                MocCourseQueryVo mocCourseQueryVo = new MocCourseQueryVo();
                mocCourseQueryVo.setCategoryId(Long.valueOf(new JsonPathSelector("$.id").select(secondary_Url)));
                mocCourseQueryVo.setCategoryChannelId(Integer.valueOf(catgoryId));
                String toJSONString = "mocCourseQueryVo:"+JSON.toJSONString(mocCourseQueryVo);
                HttpRequestBody json = new HttpRequestBody().json(toJSONString,"utf8");
                Request request = urlUtils.mocSearchBeanUrl_Post();
                request.setRequestBody(json);
                page.addTargetRequest(request);
            }
                urlUtils.setJsonFile(catgoryId,page.getJson().jsonPath("$.result[*]").toString());
//            requestCache.addAll(page.getHtml().regex("").all());
//            page.addTargetRequests(new ArrayList<>(requestCache));
//            requestCache.clear();
//            page.setSkip(true);
        }
        if(StringUtils.startsWith(page.getRequest().getUrl(),"https://www.icourse163.org/web/j/channelBean.listMocChannelCategoryRel.rpc?csrfKey=")){

        }else{
            processStrategy.doProcess(page);
        }
    }
}