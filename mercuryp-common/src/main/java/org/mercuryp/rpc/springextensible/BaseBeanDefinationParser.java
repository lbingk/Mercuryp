package org.mercuryp.rpc.springextensible;

import org.mercuryp.rpc.springextensible.authority.AuthorityBeanDefination;
import org.mercuryp.rpc.springextensible.consumer.ComsumerBeanDefination;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @Description 定义基本抽象父类实现 BeanDefinitionParser 完成注册中心，服务提供者以及消费者的注册网络信息
 * @Author luobingkai
 * @Date 2019/7/20 16:05
 * @Version 1.0
 **/
public abstract class BaseBeanDefinationParser implements BeanDefinitionParser {

    private Class<?> clz;

    public BaseBeanDefinationParser(Class<?> _clz) {
        clz = _clz;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(clz);
        beanDefinition.setLazyInit(false);
        beanDefinition.getPropertyValues().add("ip", element.getAttribute("ip"));
        beanDefinition.getPropertyValues().add("port", element.getAttribute("port"));
        beanDefinition.getPropertyValues().add("timeout", element.getAttribute("timeout"));
        BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
        //注册bean到BeanDefinitionRegistry中
        beanDefinitionRegistry.registerBeanDefinition(clz.getName(), beanDefinition);
        return beanDefinition;
    }
}
