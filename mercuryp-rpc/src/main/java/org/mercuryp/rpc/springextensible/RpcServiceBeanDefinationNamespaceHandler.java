package org.mercuryp.rpc.springextensible;


import org.mercuryp.rpc.springextensible.consumer.ConsumerServiceBeanDefination;
import org.mercuryp.rpc.springextensible.consumer.ConsumerServiceBeanDefinationParser;
import org.mercuryp.rpc.springextensible.consumer.ConsumerSocketBeanDefination;
import org.mercuryp.rpc.springextensible.consumer.ConsumerSocketBeanDefinationParser;
import org.mercuryp.rpc.springextensible.provider.ProviderSocketBeanDefination;
import org.mercuryp.rpc.springextensible.provider.ProviderSocketBeanDefinationParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Description spring 扩展命名空间类
 * @Author luobingkai
 * @Date 2019/7/19 17:34
 * @Version 1.0
 **/
public class RpcServiceBeanDefinationNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("consumerSocketBean", new ConsumerSocketBeanDefinationParser(ConsumerSocketBeanDefination.class));
        registerBeanDefinitionParser("consumerServiceBean", new ConsumerServiceBeanDefinationParser(ConsumerServiceBeanDefination.class));
        registerBeanDefinitionParser("providerSocketBean", new ProviderSocketBeanDefinationParser(ProviderSocketBeanDefination.class));
    }
}
