package org.mercuryp.authority.metadata;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description 定义服务日提供者注册类
 * @Author luobingkai
 * @Date 2019/7/20 2:10
 * @Version 1.0
 **/
@Setter
@Getter
public class ServiceProviderBeanDefination {
    private String ip;
    private String port;
    private String interfaceImplClassName;
    private List<String> interfaceClassNameList;
    private List<String> methodNameList;
}
