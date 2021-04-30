package org.Lyoto.Utils;

import org.Lyoto.Utils.MOOCUtils.MOOCEntity;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lyoto
 * @Date 2021-04-29 20:06
 **/
@Component
public class UrlUtils {
    @Autowired
    MOOCEntity moocEntity;

    protected static Properties properties;
    /**
     * 截取catgoryId
     * @return
     */
    public  ArrayList<String> catgoryId(ArrayList<String> catgoryList){
        ArrayList<String> catgoryIdList = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?<=\\/)[\\d]{4,}(?=\\.)");
        Iterator<String> iterator = catgoryList.iterator();
        while (iterator.hasNext()){
        Matcher matcher = pattern.matcher(iterator.next());
            if (matcher.find()){
                catgoryIdList.add(matcher.group(0));
            }
    }
        return catgoryIdList;
    }

    /**
     *
     * @param url
     * @return
     */
    public String catgoryId(String url){
        StringBuilder builder = new StringBuilder();
        Pattern pattern = Pattern.compile("(?<=\\/)[\\d]{4,}(?=\\.)");
        Matcher matcher = pattern.matcher(url);
        if(matcher.find()){
            builder.append(matcher.group(0));
        }
        return builder.toString();
    }
    /**
     * 获取大类目下的小类目
     */
    public Request mocChannelUrl_post(){
        moocEntity.setIndex_frist("channelBean");
        moocEntity.setIndex_second("listMocChannelCategoryRel");
        return moocEntity.request_POST();
//        https://www.icourse163.org/web/j/channelBean.listMocChannelCategoryRel.rpc?csrfKey=c1aa3b6a03654e76b4c818532a45d0a3

    }

    /**
     * 转存json为File
     * @param jsonData
     */
    public boolean setJsonFile(String catgoryId,String jsonData){
        String configFile = "JsonAddr.properties";
        ClassPathResource resource = new ClassPathResource(configFile);

        InputStreamReader isr;
        this.properties = new Properties();
        try {
            isr = new InputStreamReader(resource.getInputStream());
            properties.load(isr);
//            Enumeration fileName = properties.propertyNames();
//            while (fileName.hasMoreElements()) {
//                String strKey = (String) fileName.nextElement();
//                String strValue = properties.getProperty(strKey);
//                System.out.println(strKey + "," + strValue);
//            }
            isr.close();
            String jsonPath = properties.getProperty("address")+"/JsonFile";
            File file = new File(jsonPath);
            if(!file.exists()){
             file.mkdir();
            }
            file = new File(jsonPath+"/"+catgoryId+".json");
            if(!file.exists()){
                file.createNewFile();
            }
            FileUtils.writeStringToFile(file,jsonData,"UTF8");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
