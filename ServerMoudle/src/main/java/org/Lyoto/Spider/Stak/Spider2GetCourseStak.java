package org.Lyoto.Spider.Stak;

import org.Lyoto.Spider.DefaultSpider;
import org.Lyoto.Spider.PipeLine.StrategyPiperLine;
import org.Lyoto.Utils.MOOCUtils.LoadJsonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.RedisPriorityScheduler;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.util.List;

/**
 * @author Lyoto
 * @Date 2021-04-29 19:19
 **/
@Component
public class Spider2GetCourseStak {

    @Autowired
    DefaultSpider defaultSpider;
    @Autowired
    LoadJsonFile loadJsonFile;

    @Autowired
    StrategyPiperLine strategyPiperLine;
    private volatile Spider spider ;

    @Scheduled(cron = "0 0 0 ? * 6-7")//周六 周日 0点开始执行
    public void autoRun(){
        if(spider == null){
            this.spider = Spider.create(defaultSpider);
        }

            //判断当前Json文件状态
        spider = loadJsonFile.jsonCheck(spider);
        if(loadJsonFile.isJsonExist()){
            spider = loadJsonFile.loadJson(spider);
        }
        spider.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000
        )))
                .addPipeline(strategyPiperLine)
                .thread(100)
                /*.setDownloader(proxyClient())*/;
        spider.setEmptySleepTime(1000);
        spider.runAsync();
    }
    public void run(){
        this.autoRun();
    }
    public void close(){
        this.spider.stop();
    }

    public HttpClientDownloader proxyClient (){
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("u6584.b5.t.16yun.cn",6460,"16DHADGC","097832")));
        return httpClientDownloader;
    }



}
