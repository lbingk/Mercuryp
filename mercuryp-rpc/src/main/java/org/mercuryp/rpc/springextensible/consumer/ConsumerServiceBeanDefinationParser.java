package org.mercuryp.rpc.springextensible.consumer;

import org.mercuryp.rpc.springextensible.BaseBeanDefinationParser;
import org.mercuryp.transport.metadata.ServiceConsumerBeanDefinationInfo;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @Description spring 扩展解析类
 * @Author luobingkai
 * @Date 2019/7/19 17:24
 * @Version 1.0
 **/
public class ConsumerServiceBeanDefinationParser extends BaseBeanDefinationParser {


    public ConsumerServiceBeanDefinationParser(Class<?> _clz) {
        super(_clz);
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(clz);
        beanDefinition.setLazyInit(false);
        beanDefinition.getPropertyValues().add("serviceClassName", element.getAttribute("serviceClassName"));
        beanDefinition.getPropertyValues().add("ref", element.getAttribute("ref"));

        BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
        //注册bean到BeanDefinitionRegistry中
        beanDefinitionRegistry.registerBeanDefinition(clz.getName(), beanDefinition);

        //储存配置的消费者
        ServiceConsumerBeanDefinationInfo.getSingleton().addConsumerServiceBeanDefinationClassName(element.getAttribute("serviceClassName"));

        return beanDefinition;
    }

}
