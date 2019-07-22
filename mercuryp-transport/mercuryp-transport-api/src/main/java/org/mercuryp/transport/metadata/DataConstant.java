package org.mercuryp.transport.metadata;

public enum DataConstant {
    // 服务提供注册类
    PROVIDER_REGISTRY_REQUEST("provider_registry_request", "provider_registry_request"),
    // 服务提供注册类
    AUTHORITY_REGISTRY_RESPONE("authority_registry_respone", "authority_registry_respone"),
    // 服务消费订阅类
    COSUMER_SUBSCRIBE("cosumer_subscribe", "cosumer_subscribe"),
    // 服务提供注册成功后的返回的消息
    AUTHORITY_REGISTRY_RESPONE_CONTENT("authority_registry_respone_content", "authority_registry_respone_content"),
    // 注册中心发给消费者的心跳包标识
    AUTHORITY_PROVIDER_HEARTBEAT("authority_provider_heartbeat", "authority_provider_heartbeat");

    private String code;
    private Object msg;

    DataConstant(String _code, Object _msg) {
        code = _code;
        msg = _msg;
    }

    public String getCode() {
        return code;
    }

    public Object getMsg() {
        return msg;
    }
}