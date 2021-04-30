package org.Lyoto.Spider.Strategy.impl;

/**
 * @author Lyoto
 * @Date 2021-04-28 20:28
 **/


import org.Lyoto.Spider.Strategy.FilterProcessor;
import org.Lyoto.Spider.Strategy.ProcessStrategy;
import org.Lyoto.Utils.StrUtils;
import org.Lyoto.Utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

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


    public IteratorProcessor(@Qualifier("catrgoryInfo") ProcessStrategy processStrategy) {
        super(processStrategy);
    }

    @Override
    public void doProcess(Page page) {

        if (StringUtils.startsWith(page.getRequest().getUrl(), "https://www.icourse163.org/channel/3002.htm")) {
            requestCache.addAll(page.getHtml().regex("").all());
            page.addTargetRequests(new ArrayList<>(requestCache));
            requestCache.clear();
            page.setSkip(true);
        } else {
            processStrategy.doProcess(page);
        }
    }
}