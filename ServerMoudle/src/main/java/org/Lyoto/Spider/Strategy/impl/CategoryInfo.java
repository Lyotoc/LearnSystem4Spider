package org.Lyoto.Spider.Strategy.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.Lyoto.Pojo.CourseInfo;
import org.Lyoto.Pojo.courseVideoInfo;
import org.Lyoto.Spider.PoJo.MocCourseQueryVo;
import org.Lyoto.Spider.PoJo.MocTermDto;
import org.Lyoto.Spider.PoJo.Query;
import org.Lyoto.Spider.Strategy.ProcessStrategy;
import org.Lyoto.Utils.MOOCUtils.Catgory;
import org.Lyoto.Utils.MOOCUtils.MOOCEntity;
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
import us.codecraft.webmagic.selector.Selectable;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.*;

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
    @Autowired
    MOOCEntity moocEntity;
    @Override
    public void doProcess(Page page) {
        video2Save(page);
    }

    /**
     * 保存m3u8链接及视频的其他信息到数据库
     * @param page
     */
    public void video2Save(Page page) {
        //获取json
        Json videoJson = page.getJson();
        //判断
        if(!videoJson.jsonPath("$.code").get().equals("0")){
            return;
        }
        courseVideoInfo courseVideoInfo = new courseVideoInfo();
        //获取result结点
        courseVideoInfo.setVideoId(Integer.valueOf(videoJson.jsonPath("$.result.videoId").get()));

        //提取videoId
            //提取duration
        courseVideoInfo.setDuration(Integer.valueOf(videoJson.jsonPath("$.result.duration").get()));
            //提取name
        courseVideoInfo.setName(videoJson.jsonPath("$.result.name").get());
            //提取videoImgUrl
        courseVideoInfo.setVideoImgUrl(videoJson.jsonPath("$.result.videoImgUrl").get());
            //videos结点
        //获取最后的一个点
                //填充属性
                //size
        courseVideoInfo.setSize(videoJson.jsonPath("$.result.videos[-1:].size").get());
                //videoUrl
        String videoUrl = videoJson.jsonPath("$.result.videos[-1:].videoUrl").get();

        courseVideoInfo.setVideoUrl(StrUtils.catchTarget(videoUrl,"(.+?)(?=\\?)"));
                //format
        courseVideoInfo.setFormat(videoJson.jsonPath("$.result.videos[-1:].format").get());
                //currentTermId
        courseVideoInfo.setCurrentTermId(Integer.valueOf(page.getRequest().getExtra("currentTermId")));
        //封装对象，向持久层提交
        page.putField("videoInfo",courseVideoInfo);
    }

    @Override
    public Site addSite() {
        return Site.me();
    }
}
