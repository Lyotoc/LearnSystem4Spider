package org.Lyoto.Utils.MOOCUtils;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Lyoto
 * @Date 2021-04-26 12:14
 **/
@Component
public class CookiesGet {
     private static final String DOMAIN = "https://www.icourse163.org";

    // 0- NTESSTUDYSI  1 -EDUWEBDEVICE
     private  String[] cookies = new String[2];


    /**
     *获取 NTESSTUDYSI 和 EDUWEBDEVICE
     * @exception 获取cookies失败
     * @return 0-NTESSTUDYSI 1-EDUWEBDEVICE
     */
     public String[] getCookies(){
         CloseableHttpClient  httpClient = HttpClients.createDefault();
//         创建httpGet请求对象，设置url地址
         HttpGet doget = new HttpGet(DOMAIN);

//         添加配置信息
         doget.setConfig(getConfig());

//        使用httpClient发起请求，获取响应
         try(CloseableHttpResponse execute = httpClient.execute(doget)) {
             //正常请求
             if(execute.getStatusLine().getStatusCode() == 200){
                 Header[] headers = execute.getHeaders("Set-Cookie");
                 for(Header header : headers){
                     HeaderElement[] elements = header.getElements();
//                    cookies[0]存储NTESSTUDYSI的值
                     cookies[0] = elements[0].getName().equals("NTESSTUDYSI") ? elements[0].getValue() : cookies[0];
//                    cookies[1]存储  EDUWEBDEVICE的值
                     cookies[1] = elements[0].getName().equals("EDUWEBDEVICE") ? elements[0].getValue() : cookies[1];
                 }
             }else{
                 new IOException();
             }

         } catch (IOException e) {
             System.err.print("获取cookies失败");
             e.printStackTrace();
         }
         return cookies;
     }

    /**
     *  设置请求信息
     * @return
     */
    private static RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000) //创建连接的最长时间
                .setConnectionRequestTimeout(1000) //获取连接的最长时间
                .setSocketTimeout(10000)//数据传输的最长时间
                .build();
        return config;
    }
}
