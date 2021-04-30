package org.Lyoto.Utils;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lyoto
 * @Date 2021-04-20 14:35
 **/
public class StrUtils {
    /**
     * 获取对应的Json数据
     * @param json 数据
     * @param jsonPathUrl 匹配的jsonPathUrl
     * @return
     */
    static public List<String> getNodes(String json,String jsonPathUrl){
        List<String> list = new ArrayList<>();
        //判断该数据是否为Json格式
        if(isJson(json)){
            //是则通过jsonPathUrl获取对应的数据
            list = new JsonPathSelector(jsonPathUrl).selectList(json);
        }
        return list;
    }
    /**
     * 判断该字符串是否为Json格式数据
     * @param content
     * @return
     */
    static public boolean isJson(String content){
        try{
            if(content==null || content.equals("")){
                return false;
            }
            JSONObject.parseObject(content);
            return true;
        }catch (RuntimeException e){
            return false;
        }
    }

    /**
     *Jsoup解析页面
     * @param html
     * @param selector
     * @param attr
     * @return
     */
     public static String jsoupParse(Html html,String selector, String attr){
        //适用于无属性
        if(attr==null || attr.equals("")){
            //匹配到时
         if(html.css(selector).match()){
             return Jsoup.parse(html.css(selector).get()).text();
         }
         return "";
        }
        //有属性
        if(html.css(selector,attr).match()){
            return html.css(selector,attr).get();
        }
    return "";
    }

     public static String jsoupParse(Html html,String selector){
        String attr = null;
        return jsoupParse(html,selector,attr);
    }


}
