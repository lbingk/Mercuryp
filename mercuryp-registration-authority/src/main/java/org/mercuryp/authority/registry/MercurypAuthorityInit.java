package org.mercuryp.authority.registry;

import org.mercuryp.authority.io.netty.AuthorityNettyServer;
import org.mercuryp.authority.springextensible.AuthorityBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * @Description 实现所有的bean加载以及初始化之后执行 start 里面自定义的方法
 * @Author luobingkai
 * @Date 2019/7/20 1:34
 * @Version 1.0
 **/

@Component
public class MercurypAuthorityInit implements SmartLifecycle {
    @Override
    public void start() {
        // 启动需要检查客户是否已经配置了参数：ip,以及timeout
        checkyConfigureVerification();
        // 开始启动网络编程
        AuthorityNettyServer.run();
    }



    private void checkyConfigureVerification() {
        AuthorityBean bean = SpringContextHolder.getBean(AuthorityBean.class);
        if (bean == null) {
            throw new IllegalArgumentException("注册中心的参数未配置");
        }
    }

    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable runnable) {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 0;
    }
}

