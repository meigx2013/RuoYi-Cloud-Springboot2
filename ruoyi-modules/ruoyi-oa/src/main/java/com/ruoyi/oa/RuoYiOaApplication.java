package com.ruoyi.oa;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 15801
 * @version 1.0
 * @description: TODO
 * @date 2026/1/23 0:24
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiOaApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiOaApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  办公管理模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
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

