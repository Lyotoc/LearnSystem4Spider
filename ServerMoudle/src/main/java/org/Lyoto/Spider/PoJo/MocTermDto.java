package org.Lyoto.Spider.PoJo;

import java.util.UUID;

/**
 * @author Lyoto
 * @Date 2021-05-06 20:36
 **/
public class MocTermDto {
    private Integer callCount = 1;
    private String scriptSessionId = "${scriptSessionId}190";
    private String httpSessionId;
    private Integer currentTermId;

    public MocTermDto(String NTESSTUDYSI,Integer currentTermId){
        this.httpSessionId = NTESSTUDYSI;
        this.currentTermId = currentTermId;
    }

    public String toString (){
        return "callCount="+callCount+"\n" +
                "scriptSessionId="+scriptSessionId+"\n" +
                "httpSessionId="+httpSessionId+"\n" +
                "c0-scriptName=CourseBean\n" +
                "c0-methodName=getMocTermDto\n" +
                "c0-id=0\n" +
                "c0-param0=number:"+this.currentTermId+"\n" +
                "c0-param1=number:0\n" +
                "c0-param2=boolean:true\n"+
                "batchId=0000000";
    }
}
