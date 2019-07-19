package org.mercuryp.authority.springextensible;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * @Description spring 扩展解析类
 * @Author luobingkai
 * @Date 2019/7/19 17:24
 * @Version 1.0
 **/
public class AuthorityBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class getBeanClass(Element element) {
        return AuthorityBean.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        String ip = element.getAttribute("ip");
        String timeout = element.getAttribute("timeout");

        bean.addPropertyValue("ip", ip);
        bean.addPropertyValue("timeout", timeout);

    }
}
