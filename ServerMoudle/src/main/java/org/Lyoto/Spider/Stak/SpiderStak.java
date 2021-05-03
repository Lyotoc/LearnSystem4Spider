package org.Lyoto.Spider.Stak;

import org.Lyoto.Spider.DefaultSpider;
import org.Lyoto.Utils.MOOCUtils.LoadJsonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.RedisPriorityScheduler;

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
