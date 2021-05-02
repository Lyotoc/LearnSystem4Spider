package org.Lyoto.Utils.MOOCUtils;

import org.Lyoto.Utils.StrUtils;
import org.Lyoto.Utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * @author Lyoto
 * @Date 2021-05-02 10:11
 **/
@Component
public class LoadJsonFile {
    String properties = "JsonAddr.properties";
    @Autowired
    Catgory catgory;
    @Autowired
    UrlUtils urlUtils;

    /**
     * 监测目标文件夹中是否存在大类目Json文件
     * @param spider
     * @return
     */
    public Spider jsonCheck(Spider spider){
        ClassPathResource pathResource  = new ClassPathResource(this.properties);
        try {
            InputStreamReader reader = new InputStreamReader(pathResource.getInputStream());
            Properties pro = new Properties();
            pro.load(reader);
            reader.close();
            String address = pro.getProperty("address");
            File file = new File(address+"/JsonFile");
            //当前文件夹是否存在
            if(file.exists()) {
                File[] files = file.listFiles();
                for(File file1:files){
                    String file_name =file1.getAbsolutePath();
                    Date lastmodfiyTimeDate = null;
//                    Date createTimeDate = null;
                    Path path = Paths.get(file_name);
                    BasicFileAttributeView view = Files.getFileAttributeView(path,BasicFileAttributeView.class,
                            LinkOption.NOFOLLOW_LINKS);
                    BasicFileAttributes attr;
                    try{
                        attr = view.readAttributes();
                        lastmodfiyTimeDate = new Date(attr.lastModifiedTime().toMillis());
//                        createTimeDate = new Date(attr.creationTime().toMillis());
                    }catch (Exception e){

                    }

//                    文件修改时间的Calendar
                    //判断当前Json文件修改日期是否已经过期
                    if(lastmodfiyTimeDate.getDay() != Calendar.SATURDAY){
                            break;
                    }
                    //过期重新获取
                        spider =this.addTartoSpider(StrUtils.catchTarget(file1.getName(),"(.+\\d)"),spider);
                    //未过期则直接获取并进行下一步操作

                }
                return spider;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //不存在或已过期就重新获取
            firstLoad(spider);
            return spider;
    }

    public void firstLoad(Spider spider){
        ArrayList<String> catgoryLsit = new ArrayList<>();
        catgoryLsit.addAll(urlUtils.catgoryId(catgory.getCatgoryList()));
        for(String catgory:catgoryLsit){
            try {
                spider = addTartoSpider(catgory,spider);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public Spider addTartoSpider(String catgory,Spider spider) throws UnsupportedEncodingException {
        HttpRequestBody custom = new HttpRequestBody().custom(("channelId=" + catgory).getBytes("UTF8"), MOOCHeader.ContentType, "UTF8");
        Request request = urlUtils.mocChannelUrl_post();
        request.setRequestBody(custom);
        return spider.addRequest(request);
    }
}
