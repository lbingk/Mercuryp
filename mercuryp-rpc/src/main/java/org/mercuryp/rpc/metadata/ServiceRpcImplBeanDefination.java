package org.mercuryp.rpc.metadata;


import lombok.Getter;
import lombok.Setter;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * @Description 定义 bean：暴露的远程服务实现信息
 * @Author luobingkai
 * @Date 2019/7/20 13:07
 * @Version 1.0
 **/

@Setter
@Getter
public class ServiceRpcImplBeanDefination implements Serializable {
    private Class<?> serviceClass;
    private String methodName;
    private Object[] paramaters;
}
