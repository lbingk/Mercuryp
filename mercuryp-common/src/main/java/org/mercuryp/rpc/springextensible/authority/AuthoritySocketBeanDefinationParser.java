package org.mercuryp.rpc.springextensible.authority;

import org.mercuryp.rpc.springextensible.BaseBeanDefinationParser;
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
public class AuthoritySocketBeanDefinationParser extends BaseBeanDefinationParser {
    public AuthoritySocketBeanDefinationParser(Class<?> _clz) {
        super(_clz);
    }
}
