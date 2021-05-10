package org.Lyoto;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Lyoto
 * @Date 2021-04-29 9:24
 **/
@SpringBootApplication
@EnableScheduling
@MapperScan("org.Lyoto.Dao")
public class Application{
    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);
    }

}
