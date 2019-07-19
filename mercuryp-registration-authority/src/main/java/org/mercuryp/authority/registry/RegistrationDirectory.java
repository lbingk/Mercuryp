package org.mercuryp.authority.registry;

import org.mercuryp.authority.metadata.ServiceProviderBeanDefination;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 定义懒汉式下的装载注册服务的地址：Map
 * @Author luobingkai
 * @Date 2019/7/20 1:54
 * @Version 1.0
 **/
public class RegistrationDirectory {

    private volatile static RegistrationDirectory instance = null;

    private RegistrationDirectory() {
    }

    public static RegistrationDirectory getInstance() {
        if (instance == null) {
            synchronized (RegistrationDirectory.class) {
                if (instance == null) {
                    instance = new RegistrationDirectory();
                }
            }
        }
        return instance;
    }

    /**
     * 用 ConcurrentHashMap 来装载对应的注册服务:key 值为注册的地址:ip+"$"+port
     **/
    public static Map<String, List<ServiceProviderBeanDefination>> ServiceProviderBeanDefinationMap = new ConcurrentHashMap<>();


}
