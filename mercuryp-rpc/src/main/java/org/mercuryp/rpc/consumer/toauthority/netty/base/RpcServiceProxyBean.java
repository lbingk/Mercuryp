package org.mercuryp.rpc.consumer.toauthority.netty.base;

import lombok.Getter;
import lombok.Setter;
import org.mercuryp.rpc.springextensible.BaseComunicationBeanDefination;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Description 定义生成调用远程对象的代理生成处理类
 * @Author luobingkai
 * @Date 2019/7/23 15:34
 * @Version 1.0
 **/

@Setter
@Getter
public class RpcServiceProxyBean implements InvocationHandler {
    // 此处采用父类：配置的本机ip以及port
    private BaseComunicationBeanDefination baseComunicationBeanDefination;

    public RpcServiceProxyBean(BaseComunicationBeanDefination _baseComunicationBeanDefination) {
        baseComunicationBeanDefination = _baseComunicationBeanDefination;
    }

    /**
     * 在调用对象的真实方法时候，网络通信给服务提供者
     **/
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
