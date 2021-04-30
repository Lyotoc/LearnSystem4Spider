package org.Lyoto;

import com.sun.javaws.Main;
import org.Lyoto.Spider.DefaultSpider;
import org.Lyoto.Spider.Strategy.impl.CatrgoryInfo;
import org.Lyoto.Spider.Strategy.impl.IteratorProcessor;
import org.Lyoto.Utils.MOOCUtils.MOOCEntity;
import org.Lyoto.Utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.RedisPriorityScheduler;

/**
 * @author Lyoto
 * @Date 2021-04-29 9:24
 **/
@SpringBootApplication
@EnableScheduling
public class Application{
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
