package org.mercuryp.transport.metadata;

import lombok.Getter;
import lombok.Setter;
import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 定义服务日提供者注册类
 * @Author luobingkai
 * @Date 2019/7/20 2:10
 * @Version 1.0
 **/
@Setter
@Getter
@Message
public class ServiceProviderBeanDefination implements Serializable {
    private String interfaceImplClassName;
    private List<String> interfaceClassName = new ArrayList<>();
    private List<String> methodNameList = new ArrayList<>();

    @Override
    public String toString() {
        return  "interfaceImplClassName:" + interfaceImplClassName + ";" +
                "interfaceClassName:" + interfaceClassName + ";" + "methodNameList:" + methodNameList + ";";
    }
}
