package org.mercuryp.rpc.springextensible;

import org.mercuryp.rpc.springextensible.authority.AuthorityBeanDefination;
import org.mercuryp.rpc.springextensible.authority.AuthorityBeanDefinationParser;
import org.mercuryp.rpc.springextensible.consumer.ComsumerBeanDefination;
import org.mercuryp.rpc.springextensible.consumer.ComsumerBeanDefinationParser;
import org.mercuryp.rpc.springextensible.provider.ProviderBeanDefination;
import org.mercuryp.rpc.springextensible.provider.ProviderBeanDefinationParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Description spring 扩展命名空间类
 * @Author luobingkai
 * @Date 2019/7/19 17:34
 * @Version 1.0
 **/
public class BeanDefinationNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("authoritybean", new AuthorityBeanDefinationParser(AuthorityBeanDefination.class));
        registerBeanDefinitionParser("providerbean", new ProviderBeanDefinationParser(ProviderBeanDefination.class));
        registerBeanDefinitionParser("consumerbean", new ComsumerBeanDefinationParser(ComsumerBeanDefination.class));
    }
}
