package org.mercuryp.rpc.consumer.toauthority.netty.base;

import org.mercuryp.rpc.springextensible.BaseComunicationBeanDefination;

import java.lang.reflect.Proxy;

/**
 * @Description 定义工厂类，负责生成远程接口对象的代理类
 * @Author luobingkai
 * @Date 2019/7/23 15:37
 * @Version 1.0
 **/
public class CusProxyFactory {
    public static Object createProxy(Class<?> interfaceClass, BaseComunicationBeanDefination baseComunicationBeanDefination) {
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new RpcServiceProxyBean(baseComunicationBeanDefination));
    }

}
