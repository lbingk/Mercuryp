package org.mercuryp.authority.springextensible;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * @Description 模拟扩展：注册中心配置
 * @Author luobingkai
 * @Date 2019/7/19 17:22
 * @Version 1.0
 **/

public class AuthorityBean {

    /**
     * 注册中心暴露的端口
     **/
    private String port;

    /**
     * 注册中心注册的超时时间
     **/
    private long timeout;

    public AuthorityBean(String _port, long _timrout) {
        port = _port;
        timeout = _timrout;
    }

    public AuthorityBean() {
    }


    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
