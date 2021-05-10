package org.Lyoto.Utils.MOOCUtils;

import com.alibaba.fastjson.JSON;
import org.Lyoto.Spider.PoJo.MocCourseQueryVo;
import org.Lyoto.Utils.StrUtils;
import org.Lyoto.Utils.UrlUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * @author Lyoto
 * @Date 2021-05-02 10:11
 **/
@Component
public class LoadJsonFile {
    private String properties = "JsonAddr.properties";
    @Autowired
    private Catgory catgory;
    @Autowired
    private UrlUtils urlUtils;

    private ClassPathResource pathResource  = new ClassPathResource(this.properties);
    private String address = "";

    private boolean jsonExist;
    public boolean isJsonExist() {
        return jsonExist;
    }

    public void setJsonExist(boolean jsonExist) {
        this.jsonExist = jsonExist;
    }

    //
    private boolean loadProperties(){
        try { InputStreamReader reader = new InputStreamReader(pathResource.getInputStream());
        Properties pro = new Properties();
        pro.load(reader);
        reader.close();
         this.address = pro.getProperty("address");
    } catch(IOException e) {
        e.printStackTrace();
        return false;
    }
        return true;
    }

    /**
     * 监测目标文件夹中是否存在大类目Json文件
     * @param spider
     * @return
     */
    public Spider jsonCheck(Spider spider){
        if(address.equals("") || address == null){
            this.loadProperties();
        }

            File file = new File(address+"/JsonFile");
            //当前文件夹是否存在
            if(file.exists()) {
                File[] files = file.listFiles();
                if(files.length == 0){
                    firstLoad(spider);
                    this.jsonExist = false;
                    return spider;
                }
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
                        Date date = new Date();
//                    文件修改时间的Calendar
                    //判断当前Json文件修改日期是否已经过期
//                    未过期
                    if(!(lastmodfiyTimeDate.getDay()+7< date.getDay())){
                            continue;
                    }
                    //过期重新获取
                    try {
                        spider =this.addTartoSpider(StrUtils.catchTarget(file1.getName(),"(\\d+)"),spider);
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        unsupportedEncodingException.printStackTrace();
                    }
                    //过期则直接获取并进行下一步操作
                }
                    this.jsonExist = true;
                return spider;
            }

        //不存在或已过期就重新获取
            firstLoad(spider);
        this.jsonExist = false;
            return spider;
    }

    /**
     * Spider容器第一次加载各大类的Url
     * @param spider
     */
    public void firstLoad(Spider spider){
        ArrayList<String> catgoryLsit = new ArrayList<>();
        catgoryLsit.addAll(urlUtils.catgoryId(catgory.getCatgoryList()));
        for(String catgory:catgoryLsit){
            try {
                spider = this.addTartoSpider(catgory,spider);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @return
     */
    public Spider secondary_Url(File file,Spider spider){
        try {
            String catgoryID = StrUtils.catchTarget(file.getName(),"(\\d+)");

            String jsonData = FileUtils.readFileToString(file,Charset.forName("UTF8"));
            Json json = new Json(jsonData);
            List<String> secondary_UrlNodes = json.jsonPath("$.result[*]").all();

            for(int i = 0;i < secondary_UrlNodes.size();i++){
//                Secondary_Url secondary_urlEntity = new Secondary_Url();
//                //对二级目录的Pojo进行属性填充
//                secondary_urlEntity.setId(Long.parseLong(secondary_Url.jsonPath("$.id").toString()));
//                secondary_urlEntity.setName(secondary_Url.jsonPath("$.name").toString());
//                secondary_urlEntity.setType(Integer.parseInt(secondary_Url.jsonPath("$.type").toString()));
//                String courseCount = secondary_Url.jsonPath("$.courseCout").toString();
//                if(!courseCount.equals("null")||courseCount!=null){
//                    secondary_urlEntity.setCourseCout(Integer.parseInt(courseCount));
//                }
//                String iconUrl = secondary_Url.jsonPath("$.iconUrl").toString();
//                if(!(iconUrl.equals("null"))||iconUrl!=null){
//                    secondary_urlEntity.setIconUrl(iconUrl);
//                }
//                secondary_urlEntity.setParentId(Long.parseLong(secondary_Url.jsonPath("$.parentId").toString()));
//                secondary_urlEntity.setLinkName(secondary_Url.jsonPath("$.linkName").toString());
                MocCourseQueryVo mocCourseQueryVo = new MocCourseQueryVo();

                mocCourseQueryVo.setCategoryId(Long.valueOf(new JsonPathSelector("$.id").select(secondary_UrlNodes.get(i))));
                mocCourseQueryVo.setCategoryChannelId(Integer.parseInt(catgoryID));
                mocCourseQueryVo.setCategoryName(new JsonPathSelector("$.name").select(secondary_UrlNodes.get(i)));
                //将请求体需要的内容传入至requestBody
                spider = this.addTartoSpider4Secondary(mocCourseQueryVo,spider);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return spider;
    }




    /**
     * 获取小类目时设置其请求内容
     * @param catgory
     * @param spider
     * @return
     * @throws UnsupportedEncodingException
     */
    public Spider addTartoSpider(String catgory,Spider spider) throws UnsupportedEncodingException {
        HttpRequestBody custom = new HttpRequestBody().custom(("channelId=" + catgory).getBytes("UTF8"), MOOCHeader.ContentType, "UTF8");
        Request request = urlUtils.mocChannelUrl_post();
        request.setRequestBody(custom);
        return spider.addRequest(request);
    }

    /**
     * 对二级目录的详细课程解析
     * @param mocCourseQuery
     * @return
     */
    public Spider addTartoSpider4Secondary(MocCourseQueryVo mocCourseQuery,Spider spider){
        String toJSONString = "mocCourseQueryVo="+JSON.toJSON(mocCourseQuery);
        HttpRequestBody json = new HttpRequestBody().json(toJSONString,"utf8");
        Request request = urlUtils.mocSearchBeanUrl_Post();
        request.setRequestBody(json);
        return spider.addRequest(request);
    }
    public Spider loadJson(Spider spider){

        //加载JsonFile
        File file = new File(address+"/JsonFile");
        File[] files = file.listFiles();
        for(File file1: files) {
        spider = this.secondary_Url(file1,spider);
        }
        return spider;
        }
    }


