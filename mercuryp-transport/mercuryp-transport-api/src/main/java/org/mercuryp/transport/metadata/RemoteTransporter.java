package org.mercuryp.transport.metadata;

import lombok.Getter;
import lombok.Setter;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * @Description 定义远程传输对象，统一管理注册，服务以及消费的信息交流
 * @Author luobingkai
 * @Date 2019/7/22 11:48
 * @Version 1.0
 **/
@Setter
@Getter
@Message
public class RemoteTransporter implements Serializable {

    /**
     * 信息Id，用于精确分配，由于选择的是nio的方式，需要唯一标志来实现一一对应
     **/
    private String requestMark;

    /**
     * 信息ip
     **/
    private String ip;

    /**
     * 信息port
     **/
    private int port;

    /**
     * 信息类型
     **/
    private String requestyType;

    /**
     * 信息时间戳
     **/
    private long timestamp;

    /**
     * 信息体的字节长度
     **/
    private int contentSize;

    /**
     * 信息体
     **/
    private String content;

    @Override
    public String toString() {
        return "requestMark:" + requestMark + "," +
                "requestyType:" + requestyType + "," +
                "ip:" + ip + "," +
                "port:" + port + "," +
                "timestamp:" + timestamp + "," +
                "content:" + contentSize + "," +
                "content:" + content;
    }

    public static RemoteTransporter createRemoteTransporter(String _requestMark, String _requestyType, String _ip, int _port, CommonCustomerBody _commonCustomerBody) {
        RemoteTransporter remoteTransporter = new RemoteTransporter();
        remoteTransporter.setRequestMark(_requestMark);
        remoteTransporter.setRequestyType(_requestyType);
        remoteTransporter.setTimestamp(System.nanoTime());
        remoteTransporter.setIp(_ip);
        remoteTransporter.setPort(_port);
        remoteTransporter.setContent(_commonCustomerBody.toString());
        // TODO 信息体的字节长度
        return remoteTransporter;
    }
}
