package org.Lyoto.Spider.Stak;

import org.Lyoto.Spider.DefaultSpider;
import org.Lyoto.Spider.Strategy.impl.CatrgoryInfo;
import org.Lyoto.Spider.Strategy.impl.IteratorProcessor;
import org.Lyoto.Utils.MOOCUtils.Catgory;
import org.Lyoto.Utils.MOOCUtils.LoadJsonFile;
import org.Lyoto.Utils.MOOCUtils.MOOCEntity;
import org.Lyoto.Utils.MOOCUtils.MOOCHeader;
import org.Lyoto.Utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.RedisPriorityScheduler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @author Lyoto
 * @Date 2021-04-29 19:19
 **/
@Component
public class SpiderStak {

    @Autowired
    DefaultSpider defaultSpider;
    @Autowired
    LoadJsonFile loadJsonFile;



    @Scheduled(initialDelay = 1000,fixedDelay = 100*1000)
    public void run(){
        Spider spider = Spider.create(defaultSpider);
            //判断当前Json文件状态
        spider = loadJsonFile.jsonCheck(spider);
        if(loadJsonFile.isJsonExist()){
            spider = loadJsonFile.loadJson(spider);
        }
        spider.setScheduler(new QueueScheduler().setDuplicateRemover(new RedisPriorityScheduler("localhost")))
                .thread(10)
                .run();
    }



}
