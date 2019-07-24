package org.mercuryp.rpc.springextensible.consumer;


import lombok.Getter;
import lombok.Setter;
import org.mercuryp.rpc.consumer.toauthority.netty.base.CusProxyFactory;
import org.mercuryp.rpc.springextensible.BaseComunicationBeanDefination;
import org.mercuryp.rpc.springextensible.authority.AuthoritySocketBeanDefination;
import org.mercuryp.rpc.util.SpringContextHolder;
import org.springframework.beans.factory.FactoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 1.配置远程调用的接口元数据，需要实现 FactoryBean 接口，并利用动态代理技术来生成代理对象，注入到spring上下文里面
 * @Author luobingkai
 * @Date 2019/7/19 17:22
 * @Version 1.0
 **/
@Setter
@Getter
public class ConsumerServiceBeanDefination extends BaseComunicationBeanDefination implements FactoryBean {
    /**
     * 暴露的服务ClassName
     */
    private String serviceClassName;
    /**
     * 暴露的服务别名
     */
    private String ref;
    /**
     * 暴露的服务Class
     */
    private Class<?> aClass;
    /**
     * 配置注册中心
     **/
    private BaseComunicationBeanDefination baseComunicationBeanDefination = null;

    /**
     * 此方法会获取对应的bean的时候，自动调用此方法，在此生成代理对象
     **/
    @Override
    public Object getObject() throws Exception {
        // 校验参数，实际不用在此校验，因为模板上已经设置了：requred属性为true
        if (serviceClassName == null || ref == null) {
            throw new IllegalArgumentException("配置的远程服务接口参数缺失");
        }
        // 初始化 Class
        aClass = Class.forName(serviceClassName);
        // 动态代理生成代理对象：暴露的服务接口代理字节码
        Object proxy = createProxy();
        return proxy;
    }

    private Object createProxy() {
        return CusProxyFactory.createProxy(aClass, baseComunicationBeanDefination);
    }


    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        // 默认为单例（不可配置）
        return true;
    }
}
