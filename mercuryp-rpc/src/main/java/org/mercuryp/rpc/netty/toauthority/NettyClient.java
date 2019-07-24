package org.mercuryp.rpc.netty.toauthority;

import org.mercuryp.rpc.annotation.RpcInvokerService;
import org.mercuryp.rpc.netty.BaseCommonClient;
import org.mercuryp.rpc.springextensible.consumer.ConsumerServiceBeanDefination;
import org.mercuryp.rpc.springextensible.provider.ProviderSocketBeanDefination;
import org.mercuryp.rpc.util.SpringContextHolder;
import org.mercuryp.transport.metadata.*;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description 定义服务提供端与注册中心的 netty 客户端启动类
 * @Author luobingkai
 * @Date 2019/7/20 2:43
 * @Version 1.0
 **/

public class NettyClient extends BaseCommonClient {

    public NettyClient(RemoteTransporter _remoteTransporterProvider, RemoteTransporter _remoteTransporterConsumer) {
        super(_remoteTransporterProvider, _remoteTransporterConsumer);
    }

    public static void startup() {
        NettyClient providerNettyClient = new NettyClient(getRemoteTransporterProvider(), getRemoteTransporterConsumer());
        providerNettyClient.run();
    }

    /**
     * 获取需要订阅的服务的信息
     **/
    private static RemoteTransporter getRemoteTransporterConsumer() {
        RemoteTransporter remoteTransporter = null;
        ServiceConsumerBeanDefinationInfo serviceConsumerBeanDefinationInfo = ServiceConsumerBeanDefinationInfo.getSingleton();
        List<String> consumerServiceBeanDefinationClassNameList = serviceConsumerBeanDefinationInfo.getConsumerServiceBeanDefinationClassNameList();
        if (!CollectionUtils.isEmpty(consumerServiceBeanDefinationClassNameList)) {
            remoteTransporter = RemoteTransporter.createRemoteTransporter(UUID.randomUUID().toString(),
                    TransportDataConstant.COSUMER_SUBSCRIBE.getCode(),
                    SpringContextHolder.getBean(ConsumerServiceBeanDefination.class),
                    serviceConsumerBeanDefinationInfo);
        }
        return remoteTransporter;
    }

    /**
     * 获取向外暴露的服务的信息
     **/
    private static RemoteTransporter getRemoteTransporterProvider() {
        RemoteTransporter remoteTransporter = null;
        List<ServiceProviderBeanDefination> serviceProviderBeanDefinationList = null;
        try {
            serviceProviderBeanDefinationList = getServiceProviderBean();
        } catch (Exception e) {
            System.out.println("容器不存在需要暴露的服务");
        }

        if (!CollectionUtils.isEmpty(serviceProviderBeanDefinationList)) {
            remoteTransporter=RemoteTransporter.createRemoteTransporter(UUID.randomUUID().toString(),
                    TransportDataConstant.PROVIDER_REGISTRY_REQUEST.getCode(),
                    SpringContextHolder.getBean(ProviderSocketBeanDefination.class),
                    new ServiceProviderBeanDefinationInfo(serviceProviderBeanDefinationList));
        }
        return remoteTransporter;
    }

    /**
     * 获取向外暴露的服务信息这种
     **/
    private static List<ServiceProviderBeanDefination> getServiceProviderBean() throws Exception {
        List<ServiceProviderBeanDefination> serviceProviderBeanList = new ArrayList<>();
        Map<String, Object> beansWithAnnotationMap = SpringContextHolder.getBeansWithAnnotation(RpcInvokerService.class);
        if (!CollectionUtils.isEmpty(beansWithAnnotationMap)) {
            for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
                Class<?> aClass = entry.getValue().getClass();
                String aClassName = aClass.getName();
                Class<?>[] interfaces = aClass.getInterfaces();
                Method[] methods = aClass.getMethods();

                ServiceProviderBeanDefination beanDefination = new ServiceProviderBeanDefination();
                beanDefination.setInterfaceImplClassName(aClassName);
                for (Class<?> anInterface : interfaces) {
                    beanDefination.getInterfaceClassName().add(anInterface.getName());
                }

                if (CollectionUtils.isEmpty(beanDefination.getInterfaceClassName())) {
                    throw new IllegalArgumentException("暴露的服务：" + aClassName + " 注解未配置默认的接口");
                }

                for (Method method : methods) {
                    beanDefination.getMethodNameList().add(method.getName());
                }

                serviceProviderBeanList.add(beanDefination);
            }
        }
        return serviceProviderBeanList;
    }
}
