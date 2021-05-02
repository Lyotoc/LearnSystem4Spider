package org.Lyoto.Utils.MOOCUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.HttpRequestBody;

/**
 * @author Lyoto
 * @Date 2021-04-25 21:36
 * 将MOOC网上必要的信息抽象为对象的成员变量
 *
 **/
@Component
public class MOOCEntity {
    //域名
    private final String domian = "https://www.icourse163.org";
    //内容
    private  String content = "/web/j/";
    //一级页面参数
    private String index_frist = "indexBeanV3";
    //二级参数
    private String index_second = "getCategoryInfo";

    //后缀
    private  String suffix =".rpc";
    //请求参数
    private String csrfKey = "";

    private static String[] cookies ;
    @Autowired
    CookiesGet cookiesGet;
    public static String[] getCookies() {
        return cookies;
    }

    public String[] setCookies() {
        if(cookies == null) {
            cookies = cookiesGet.getCookies();
        }
        return cookies;
    }


    public String getDomian() {
        return domian;
    }

    public String getContent() {
        return content;
    }

    public String getIndex_frist() {
        return index_frist;
    }

    public void setIndex_frist(String index_frist) {
        this.index_frist = index_frist;
    }

    public String getIndex_second() {
        return index_second;
    }

    public void setIndex_second(String index_second) {
        this.index_second = index_second;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getCsrfKey() {
        return csrfKey;
    }

    public void setCsrfKey(String csrfKey) {
        this.csrfKey = csrfKey;
    }


    /**
     *获取访问地址
     * @return 访问地址 --默认返回获取课程类别的信息
     */
    public String method_GET(){
        //设置crsfKey
     if(cookies == null){
         cookies=cookiesGet.getCookies();
     }
         this.csrfKey = cookies[0];
        return this.domian+content+index_frist+"."+index_second+suffix+"?csrfKey="+csrfKey;
    }

    /**
     *
     * @return
     */
    public Request request_POST(){
        Request request = new Request();
        request.setMethod("post")
                .setUrl(this.method_GET());
        return request;
    }

}
