package org.mercuryp.rpc.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @Description 定义注解：暴露的远程服务
 * @Author luobingkai
 * @Date 2019/7/20 13:07
 * @Version 1.0
 **/

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
public @interface RpcInvokerService {
    String value() default "";
}
