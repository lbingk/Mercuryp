package org.mercuryp.transport.metadata;

public enum TransportDataConstant {
    // 服务提供注册类
    PROVIDER_REGISTRY_REQUEST("provider_registry_request", "provider_registry_request"),
    // 注册中心响应服务注册
    AUTHORITY_REGISTRY_RESPONE("authority_registry_respone", "authority_registry_respone"),
    // 服务消费订阅类
    COSUMER_SUBSCRIBE("cosumer_subscribe", "cosumer_subscribe"),
    // 注册中心响应服务订阅
    COSUMER_SUBSCRIBE_RESPONE("cosumer_subscribe_respone", "cosumer_subscribe_respone"),

    // 消费者调用RPC
    CONSUMER_INVOKE_PROVIDER("consumer_invoke_provider", "consumer_invoke_provider"),

    // 服务提供注册成功后的返回的消息
    AUTHORITY_REGISTRY_RESPONE_CONTENT("authority_registry_respone_content", "authority_registry_respone_content"),
    // 注册中心发给消费者的心跳包标识
    AUTHORITY_HEARTBEAT("authorityr_heartbeat", "authorityr_heartbeat"),
    // 与注册中心的通讯：用于没有远程调用服务
    CLIENT_AUTHORITY("client_authority", "client_authority");

    private String code;
    private Object msg;

    TransportDataConstant(String _code, Object _msg) {
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