package org.mercuryp.rpc.springextensible;


import lombok.Getter;
import lombok.Setter;

/**
 * @Description 模拟扩展：注册中心的网络配置
 * @Author luobingkai
 * @Date 2019/7/19 17:22
 * @Version 1.0
 **/

@Setter
@Getter
public class BaseComunicationBeanDefination {
    private String ip;
    private int port;
    private int timeout;
}
