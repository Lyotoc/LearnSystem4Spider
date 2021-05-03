package org.Lyoto.Spider.Strategy.impl;

/**
 * @author Lyoto
 * @Date 2021-04-28 20:28
 **/


import org.Lyoto.Spider.Strategy.FilterProcessor;
import org.Lyoto.Spider.Strategy.ProcessStrategy;
import org.Lyoto.Utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.HashSet;
import java.util.Set;

/**
 * 装饰者，用来装饰book的迭代爬取功能
 */
@Component
public class IteratorProcessor extends FilterProcessor {

    private Set<String> requestCache = new HashSet<>(32);
    @Autowired
    UrlUtils urlUtils;

    public IteratorProcessor(@Qualifier("categoryInfo") ProcessStrategy processStrategy) {
        super(processStrategy);
    }

    @Override
    public void doProcess(Page page) {


            processStrategy.doProcess(page);
    }
}