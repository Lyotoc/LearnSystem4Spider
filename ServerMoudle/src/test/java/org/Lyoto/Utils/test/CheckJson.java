package org.Lyoto.Utils.test;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Lyoto
 * @Date 2021-05-01 22:57
 **/
public class CheckJson {
    String properties = "JsonAddr.properties";
    @Test
    public void jsonCheck(){
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
                        //执行大类目的Json文件重新获取

                    }
                    //过期重新获取

                    //未过期则直接获取并进行下一步操作
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //不存在或已过期就重新获取

    }
}
