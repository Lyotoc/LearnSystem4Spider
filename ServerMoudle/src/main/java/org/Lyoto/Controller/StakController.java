package org.Lyoto.Controller;

import org.Lyoto.Spider.Stak.Spider2GetCourseStak;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Lyoto
 * @Date 2021-05-09 10:37
 **/
@Controller
@RequestMapping("/spider")
public class StakController {
    @Autowired
    Spider2GetCourseStak spider2GetCourseStak;
    @ResponseBody
    @GetMapping("/test")
    public String test(){
        return "配置成功";
    }
    @GetMapping("/run")
    public void run(){
        spider2GetCourseStak.run();
    }
    @ResponseBody
    @GetMapping("/stop")
    public String close(){
        spider2GetCourseStak.close();
        return "爬取任务已暂停!!";
    }

}
