package org.mercuryp.authority.io.netty.chanelhandler;

import io.netty.channel.ChannelHandlerContext;
import org.mercuryp.authority.io.netty.base.BaseBussnessHandler;
import org.mercuryp.authority.registry.RegistrationDirectory;
import org.mercuryp.rpc.springextensible.authority.AuthorityBeanDefination;
import org.mercuryp.rpc.util.SpringContextHolder;
import org.mercuryp.transport.metadata.AuthorityResponse;
import org.mercuryp.transport.metadata.DataConstant;
import org.mercuryp.transport.metadata.HeartBeatConnetion;
import org.mercuryp.transport.metadata.RemoteTransporter;
import org.msgpack.MessagePack;

import java.util.UUID;


/**
 * @Description 定义处理读的处理
 * @Author luobingkai
 * @Date 2019/7/20 3:09
 * @Version 1.0
 **/
public class BussnessHandler extends BaseBussnessHandler {

    private static AuthorityBeanDefination authorityBeanDefination = SpringContextHolder.getBean(AuthorityBeanDefination.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RemoteTransporter transporter = MessagePack.unpack(MessagePack.pack(msg), RemoteTransporter.class);
        String requestyType = transporter.getRequestyType();
        String ip = transporter.getIp();
        int port = transporter.getPort();
        String requestMark = transporter.getRequestMark();
        String content = transporter.getContent();
        RemoteTransporter remoteTransporter = null;

        if (DataConstant.PROVIDER_REGISTRY_REQUEST.getCode().equals(requestyType)) {
            // 如果是注册消息，证明已经注册成功了，这里可以不用回复消息给注册的服务器，
            // 因为已经做了心跳以及重连，如果发生中断的话，服务端会自动申请注册
            remoteTransporter = RemoteTransporter.createRemoteTransporter(requestMark,
                    DataConstant.AUTHORITY_REGISTRY_RESPONE.getCode(), ip, port,
                    AuthorityResponse.createAuthorityResponse(DataConstant.AUTHORITY_REGISTRY_RESPONE_CONTENT.getCode()));
            // 解析 content 内容，获取注册的信息
            RegistrationDirectory.ServiceProviderBeanDefinationMap.put(ip, content);
        }

        if (DataConstant.AUTHORITY_PROVIDER_HEARTBEAT.getCode().equals(requestyType)) {
            System.out.printf("接收到心跳包");
            remoteTransporter = RemoteTransporter.createRemoteTransporter(UUID.randomUUID().toString(),
                    DataConstant.AUTHORITY_PROVIDER_HEARTBEAT.getCode(),
                    authorityBeanDefination.getIp(), authorityBeanDefination.getPort(),
                    HeartBeatConnetion.createHeartBeatConnetion(authorityBeanDefination.getIp(), authorityBeanDefination.getPort()));
        }

        System.out.println("authority server channelRead: " + transporter.toString());
        if (remoteTransporter == null) {
            throw new IllegalArgumentException("消息不符合设定的请求类型");
        }
        ctx.writeAndFlush(remoteTransporter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

