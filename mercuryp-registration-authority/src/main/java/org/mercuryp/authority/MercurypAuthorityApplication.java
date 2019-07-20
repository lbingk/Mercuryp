package org.mercuryp.authority;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @Description 模拟注册中心的启动类
 * @Author luobingkai
 * @Date 2019/7/20 0:44
 * @Version 1.0
 **/
@SpringBootApplication
@ImportResource(locations = {"classpath*:META-INF/authority.xml"})
public class MercurypAuthorityApplication {
    public static void main(String[] args)  {
       SpringApplication.run(MercurypAuthorityApplication.class, args);
    }
}
