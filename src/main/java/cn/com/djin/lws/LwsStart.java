package cn.com.djin.lws;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *   即时通讯模块启动类
 */
@SpringBootApplication(scanBasePackages = "cn.com.djin.lws.*")
@MapperScan("cn.com.djin.lws.mapper")
public class LwsStart {

    public static void main(String[] args) {
        SpringApplication.run(LwsStart.class);
    }

}
