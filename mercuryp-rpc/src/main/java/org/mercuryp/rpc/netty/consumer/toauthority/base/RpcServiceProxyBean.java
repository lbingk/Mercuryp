package org.mercuryp.rpc.netty.consumer.toauthority.base;

import lombok.Getter;
import lombok.Setter;
import org.mercuryp.rpc.springextensible.BaseComunicationBeanDefination;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
     *  v d  * 在调用对象的真实方法时候，网络通信给服务提供者
     **/
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 实现方式：此处需要用到网络编程，新建消费者客户端来与服务提供者进行远程调用，并接受结果，注意由于走的是异步（NIO）,异常需要建立一个单例的对象，
        // 用并发包的Map存储起发货的结果，用唯一标志与原来的请求的对应起来，在处理结果的时候，需要将线程挂起，因此我们只需要加锁（锁的对象粒度要精确到Map的value）
        return null;
    }
}
