package org.mercuryp.rpc;

import org.mercuryp.rpc.provider.toauthority.netty.ProviderNettyClient;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * @Description 实现所有的bean加载以及初始化之后执行 start 里面自定义的方法
 * @Author luobingkai
 * @Date 2019/7/20 16:41
 * @Version 1.0
 **/

@Component
public class MercurypProviderInit implements SmartLifecycle {

    @Override
    public void start() {
        new ProviderNettyClient().run();
    }


    @Override
    public boolean isAutoStartup() {
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
