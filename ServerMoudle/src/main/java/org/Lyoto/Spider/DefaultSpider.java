package org.Lyoto.Spider;

import org.Lyoto.Spider.Strategy.ProcessStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author Lyoto
 * @Date 2021-04-28 20:07
 **/
@Component
public class DefaultSpider implements PageProcessor {
    /**
     * 如果访问超时，等两分钟
     */
    private static int ONE_MINUTES =2*1000*60;

    private static int ONE_SECOUND = 1000;
    protected ProcessStrategy processStrategy;

    public DefaultSpider (@Qualifier("iteratorProcessor") ProcessStrategy processStrategy) {
        this.processStrategy = processStrategy;
    }
    private Site setSite(){
        //重试三次，等待1秒
        return processStrategy
                .addSite()
                .setRetrySleepTime(3)
                .setSleepTime(2*ONE_SECOUND)
                .setSleepTime(2*ONE_MINUTES);
    }



    @Override
    public void process(Page page) {
        if(processStrategy == null)
            throw new NullPointerException();

//        preProcess(page);
        processStrategy.doProcess(page);
//        afterProcess(page);

    }

    @Override
    public Site getSite() {
        return this.setSite();
    }
}
