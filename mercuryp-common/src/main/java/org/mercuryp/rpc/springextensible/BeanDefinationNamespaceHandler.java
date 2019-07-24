package org.mercuryp.rpc.springextensible;

import org.mercuryp.rpc.springextensible.authority.AuthoritySocketBeanDefination;
import org.mercuryp.rpc.springextensible.authority.AuthoritySocketBeanDefinationParser;
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
        registerBeanDefinitionParser("authoritySocketBean", new AuthoritySocketBeanDefinationParser(AuthoritySocketBeanDefination.class));
    }
}
