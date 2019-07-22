package org.mercuryp.transport.metadata;


import org.msgpack.annotation.Message;

import java.util.List;

/**
 * @Description 定义服务提供注册对象List
 * @Author luobingkai
 * @Date 2019/7/22 12:59
 * @Version 1.0
 **/

@Message
public class ServiceProviderBeanDefinationInfo  extends CommonCustomerBody {
    private List<ServiceProviderBeanDefination> serviceProviderBeanDefinationList = null;

    public ServiceProviderBeanDefinationInfo(List<ServiceProviderBeanDefination> _serviceProviderBeanDefinationList) {
        if (_serviceProviderBeanDefinationList == null) {
            throw new IllegalArgumentException("服务提供注册消息体信息错误，不可以为 null");
        }
        serviceProviderBeanDefinationList = _serviceProviderBeanDefinationList;
    }

    public ServiceProviderBeanDefinationInfo(){
    }

    @Override
    public String toString() {
        return serviceProviderBeanDefinationList.toString();
    }
}
