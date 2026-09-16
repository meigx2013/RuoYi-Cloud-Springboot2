package com.ruoyi.ai;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author meigx
 * @version 1.0
 * @description: TODO
 * @date 2026/2/3 22:27
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiAiApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiAiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Ai辅助模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
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
