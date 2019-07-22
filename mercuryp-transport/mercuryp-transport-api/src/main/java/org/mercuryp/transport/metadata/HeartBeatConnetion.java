package org.mercuryp.transport.metadata;

import lombok.Getter;
import lombok.Setter;
import org.msgpack.annotation.Message;

/**
 * @Description 定义心跳包
 * @Author luobingkai
 * @Date 2019/7/22 19:42
 * @Version 1.0
 **/

@Setter
@Getter
@Message
public class HeartBeatConnetion extends CommonCustomerBody {

    private String ip;
    private int port;

    public static HeartBeatConnetion createHeartBeatConnetion(String _ip, int _port) {
        HeartBeatConnetion heartBeatConnetion = new HeartBeatConnetion();
        heartBeatConnetion.setIp(_ip);
        heartBeatConnetion.setPort(_port);
        return heartBeatConnetion;
    }

    public HeartBeatConnetion() {
    }

    @Override
    public String toString() {
        return "ip:" + ip + ";" + "port" + port;
    }
}
