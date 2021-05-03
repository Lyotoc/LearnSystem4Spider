package org.Lyoto.Utils.MOOCUtils;

import org.Lyoto.Utils.StrUtils;
import org.Lyoto.Utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Lyoto
 * @Date 2021-04-29 18:38
 **/
@Component
public class Catgory {
//    计算机
    public static final String COMPUTER = "https://www.icourse163.org/channel/3002.htm";
//    外语
    public static final String ENGLISH = "https://www.icourse163.org/channel/2002.htm";
//    理学
    public static final String SCIENCE= "https://www.icourse163.org/channel/2003.htm";
//    工学
    public static final String ENGINEERING= "https://www.icourse163.org/channel/3003.htm";
//    经济
    public static final String ECONOMIC= "https://www.icourse163.org/channel/3004.htm";
//    心理学
    public static final String PSYCHOLOGY= "https://www.icourse163.org/channel/3007.htm";
//    文史哲
    public static final String LHP= "https://www.icourse163.org/channel/3005.htm";
//    艺术
    public static final String ART= "https://www.icourse163.org/channel/3006.htm";
//    医药卫生
    public static final String MEDICAL= "https://www.icourse163.org/channel/3008.htm";
//音乐与舞蹈
    public static final String MD= "https://www.icourse163.org/channel/15001.htm";
@Autowired
    UrlUtils urlUtils;
    public static HashMap<Integer,String> channelMap = new HashMap<>();
    static{
        channelMap.put(3002,"COMPUTER");
        channelMap.put(2002,"ENGLISH");
        channelMap.put(2003,"SCIENCE");
        channelMap.put(3003,"ENGINEERING");
        channelMap.put(3004,"ECONOMIC");
        channelMap.put(3007,"PSYCHOLOGY");
        channelMap.put(3005,"LHP");
        channelMap.put(3006,"ART");
        channelMap.put(3008,"MEDICAL");
        channelMap.put(15001,"MD");
    }
    /***
     *
     * @return 大类别组
     */
    public ArrayList<String> getCatgoryList(){
       final ArrayList<String> catgoryList = new ArrayList<>();
        catgoryList.add(COMPUTER);
        catgoryList.add(ENGLISH);
        catgoryList.add(SCIENCE);
        catgoryList.add(ENGINEERING);
        catgoryList.add(ECONOMIC);
        catgoryList.add(PSYCHOLOGY);
        catgoryList.add(LHP);
        catgoryList.add(ART);
        catgoryList.add(MEDICAL);
        catgoryList.add(MD);
        return catgoryList;
    }
    public  HashSet<String> getCatgoryId() {
        final HashSet<String> hashSet = new HashSet<>();
       hashSet.addAll(urlUtils.catgoryId(this.getCatgoryList()));
       return hashSet;
        }
    public String checkChannelId (Integer ChannelId){
        return channelMap.get(ChannelId);
        }
    }

