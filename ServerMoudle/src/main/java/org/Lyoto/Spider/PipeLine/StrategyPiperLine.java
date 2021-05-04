package org.Lyoto.Spider.PipeLine;

import org.Lyoto.Pojo.CourseInfo;
import org.Lyoto.Spider.PipeLine.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

/**
 * @author Lyoto
 * @Date 2021-05-04 13:53
 **/
@Component
public class StrategyPiperLine implements CoursePiperLine {
    @Autowired
    ArtCoursePiperLine artCoursePiperLine;
    @Autowired
    ComputerCoursePiperLine computerCoursePiperLine;
    @Autowired
    EconomicCoursePiperLine economicCoursePiperLine;
    @Autowired
    EngineeringCoursePiperLine engineeringCoursePiperLine;
    @Autowired
    EnglishCoursePiperLine englishCoursePiperLine;
    @Autowired
    LhpCoursePiperLine lhpCoursePiperLine;
    @Autowired
    MdCoursePiperLine mdCoursePiperLine;
    @Autowired
    MedicalCoursePiperLine medicalCoursePiperLine;
    @Autowired
    PsychologyCoursePiperLine psychologyCoursePiperLine;
    @Autowired
    ScienceCoursePiper scienceCoursePiper;
    @Override
    public void process(ResultItems resultItems, Task task) {
        CourseInfo courseInfo = (CourseInfo) resultItems.get("courseInfo");
        switch (courseInfo.getChannelId()){
            case 3002:computerCoursePiperLine.process(resultItems,task);
            break;
            case 2002:englishCoursePiperLine.process(resultItems, task);
            break;
            case 2003:scienceCoursePiper.process(resultItems, task);
                break;
            case 3003:engineeringCoursePiperLine.process(resultItems, task);
                break;
            case 3004:economicCoursePiperLine.process(resultItems, task);
                break;
            case 3007:psychologyCoursePiperLine.process(resultItems, task);
                break;
            case 3005:lhpCoursePiperLine.process(resultItems, task);
                break;
            case 3006:artCoursePiperLine.process(resultItems, task);
                break;
            case 3008:medicalCoursePiperLine.process(resultItems, task);
                break;
            case 15001:mdCoursePiperLine.process(resultItems,task);
                break;
        }

    }
}
