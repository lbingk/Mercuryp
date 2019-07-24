package org.mercuryp.transport.metadata;


import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 定义 bean：暴露的远程服务实现信息
 * @Author luobingkai
 * @Date 2019/7/20 13:07
 * @Version 1.0
 **/
@Message
public class ServiceConsumerBeanDefinationInfo extends CommonCustomerBody implements Serializable {
    private List<String> consumerServiceBeanDefinationClassNameList=new ArrayList<>();

    public List<String> getConsumerServiceBeanDefinationClassNameList(){
        return consumerServiceBeanDefinationClassNameList;
    }

    public void addConsumerServiceBeanDefinationClassName(String _consumerServiceBeanDefinationClassName){
        consumerServiceBeanDefinationClassNameList.add(_consumerServiceBeanDefinationClassName);
    }

    private static ServiceConsumerBeanDefinationInfo singleton = new ServiceConsumerBeanDefinationInfo();

    private ServiceConsumerBeanDefinationInfo(){}

    public static ServiceConsumerBeanDefinationInfo getSingleton(){
        return singleton;
    }


}
