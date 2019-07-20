package org.mercuryp.authority.springextensible;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @Description spring 扩展解析类
 * @Author luobingkai
 * @Date 2019/7/19 17:24
 * @Version 1.0
 **/
public class AuthorityBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(AuthorityBean.class);
        beanDefinition.setLazyInit(false);
        beanDefinition.getPropertyValues().add("port", element.getAttribute("port"));
        beanDefinition.getPropertyValues().add("timeout", element.getAttribute("timeout"));
        BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
        //注册bean到BeanDefinitionRegistry中
        beanDefinitionRegistry.registerBeanDefinition(AuthorityBean.class.getName(), beanDefinition);
        return beanDefinition;
    }
}
