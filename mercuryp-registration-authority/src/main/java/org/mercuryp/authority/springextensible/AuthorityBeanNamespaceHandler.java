package org.mercuryp.authority.springextensible;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Description spring 扩展命名空间类
 * @Author luobingkai
 * @Date 2019/7/19 17:34
 * @Version 1.0
 **/
public class AuthorityBeanNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("authoritybean",new AuthorityBeanDefinitionParser());
    }
}
