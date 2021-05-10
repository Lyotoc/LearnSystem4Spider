package org.Lyoto.Spider.Strategy;

import org.Lyoto.Spider.Strategy.ProcessStrategy;
import org.Lyoto.Utils.MOOCUtils.MOOCEntity;
import org.Lyoto.Utils.MOOCUtils.MOOCHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.io.FilterInputStream;

/**
 * FilterProcessor 是ProcessStrategy的装饰类，用来扩展ProcessStrategy的爬虫方法。
 * 这是一个伪实现类，真正的装饰类需要继承这个类。
 * 设计参考 {@link FilterInputStream}
 */
@Component
public class FilterProcessor implements ProcessStrategy {

    protected volatile ProcessStrategy processStrategy;
    @Autowired
    MOOCEntity moocEntity;
    @Autowired

    public FilterProcessor(@Qualifier("iteratorProcessor") ProcessStrategy processStrategy){
        this.processStrategy = processStrategy;
    }

    @Override
    public void doProcess(Page page) {
        processStrategy.doProcess(page);
    }

    /**
     * 通用的要设置的site,具体在具体类中增加；
     * @return
     */
    @Override
    public Site addSite() {
        return Site.me()
                .setCharset("utf8")
                .setUserAgent(MOOCHeader.UserAgent)
                .addHeader("Content-Type",MOOCHeader.ContentType)
                .addHeader("Connection", MOOCHeader.Connection)
                .addCookie("NTESSTUDYSI",moocEntity.setCookies()[0])
                .addCookie("EDUWEBDEVICE",moocEntity.setCookies()[1])
                ;


    }
}