package org.Lyoto.Spider.Strategy.impl;

import com.alibaba.fastjson.JSON;
import org.Lyoto.Spider.Strategy.ProcessStrategy;
import org.Lyoto.Utils.StrUtils;
import org.Lyoto.Utils.UrlUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Json;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Lyoto
 * @Date 2021-04-29 9:21
 **/
@Component
public class CatrgoryInfo implements ProcessStrategy {
    @Autowired
    UrlUtils urlUtils;
    @Override
    public void doProcess(Page page) {
        //保存小类目Json
        if (StringUtils.startsWith(page.getRequest().getUrl(), "https://www.icourse163.org/web/j/channelBean.listMocChannelCategoryRel.rpc?csrfKey=")) {
        this.praseCatrgory(page);
    }
    }
    //对小类目进行解析 --获取CatgoryId
    public boolean praseCatrgory(Page page){

        String catgoryId = StrUtils.catchTarget(StringUtils.toEncodedString(page.getRequest().getRequestBody().getBody(), Charset.forName("UTF-8")),"(\\d+)");
        String JsonData = page.getJson().toString();
        return urlUtils.setJsonFile(catgoryId,JsonData);


    }

    @Override
    public Site addSite() {
        return Site.me();
    }


}
