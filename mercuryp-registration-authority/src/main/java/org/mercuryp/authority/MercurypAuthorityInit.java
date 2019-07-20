package org.mercuryp.authority;

import org.mercuryp.authority.io.netty.AuthorityNettyServer;
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
        // 开始启动网络编程
        AuthorityNettyServer.run();
    }




    @Override
    public boolean isAutoStartup() {
        // 返回 true 才会执行 start 方法
        return true;
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

