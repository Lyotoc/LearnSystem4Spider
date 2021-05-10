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
    public static final String CONTENTURL ="/web/j/";
    public static final String SCRIPT ="/dwr/call/plaincall/";
    private  String content = CONTENTURL;
    //一级页面参数
    public static final String CONTENTURL_frist ="mocSearchBean";
    public static final String CATEGORY_frist ="channelBean";
    public static final String SCRIPT_frist ="CourseBean";
    public static final String SIGN_frist ="resourceRpcBean";
    private String index_frist = CONTENTURL_frist;
    //二级参数
    public static final String CONTENTURL_second ="searchCourseCardByChannelAndCategoryId";
    public static final String SCRIPT_second ="getMocTermDto";
    public static final String CATEGORY_second ="listMocChannelCategoryRel";
    public static final String SIGN_second ="getResourceToken";
    private String index_second = CONTENTURL_second;

    //后缀
    public static final String DEFAULT_SUFFIX =".rpc";
    private  String suffix =DEFAULT_SUFFIX;
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
                .addCookie("NTESSTUDYSI",this.setCookies()[0])
                .addCookie("EDUWEBDEVICE",this.setCookies()[1])
                .setUrl(this.method_GET());
        return request;
    }

    /**
     * post方式加入请求
     * @return
     */
    public Request getCourseContent(){
        Request request = new Request();
        request.setMethod("post")
                .addCookie("NTESSTUDYSI",this.setCookies()[0])
                .addCookie("EDUWEBDEVICE",this.setCookies()[1])
                .setUrl(this.domian+content+index_frist+"."+index_second+suffix);
        return request;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
