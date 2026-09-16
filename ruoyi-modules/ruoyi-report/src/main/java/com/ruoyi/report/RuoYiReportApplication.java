package com.ruoyi.report;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * @author meigx
 * @version 1.0
 * @description: TODO
 * @date 2026/1/31 20:23
 */
@EnableCustomConfig
@EnableRyFeignClients
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},
//                    scanBasePackages = {"org.jeecg.modules","com.ruoyi.report"})
@SpringBootApplication(exclude = {MongoAutoConfiguration.class},
        scanBasePackages = {"org.jeecg.modules","com.ruoyi.report"})
public class RuoYiReportApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiReportApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  报表设计模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}
