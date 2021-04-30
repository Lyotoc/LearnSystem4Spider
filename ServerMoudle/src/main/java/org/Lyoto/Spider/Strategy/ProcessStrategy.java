package org.Lyoto.Spider.Strategy;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * @author Lyoto
 * @Date 2021-04-28 20:07
 **/
public interface ProcessStrategy {
    /**
     * 爬虫具体执行的方法 {@link Page}
     */
    void doProcess(Page page);
    Site addSite();
}
