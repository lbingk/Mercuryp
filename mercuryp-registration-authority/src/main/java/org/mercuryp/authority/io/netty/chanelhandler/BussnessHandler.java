package org.mercuryp.authority.io.netty.chanelhandler;

import io.netty.channel.ChannelHandlerContext;
import org.mercuryp.authority.io.netty.base.BaseBussnessHandler;
import org.mercuryp.authority.registry.RegistrationDirectory;
import org.mercuryp.rpc.springextensible.BaseComunicationBeanDefination;
import org.mercuryp.rpc.springextensible.authority.AuthoritySocketBeanDefination;
import org.mercuryp.rpc.util.SpringContextHolder;
import org.mercuryp.transport.metadata.AuthorityResponse;
import org.mercuryp.transport.metadata.TransportDataConstant;
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

    private static AuthoritySocketBeanDefination authoritySocketBeanDefination = SpringContextHolder.getBean(AuthoritySocketBeanDefination.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RemoteTransporter transporter = MessagePack.unpack(MessagePack.pack(msg), RemoteTransporter.class);
        String requestyType = transporter.getRequestyType();
        BaseComunicationBeanDefination baseComunicationBeanDefination = new BaseComunicationBeanDefination();
        baseComunicationBeanDefination.setIp(transporter.getIp());
        baseComunicationBeanDefination.setPort(transporter.getPort());
        String requestMark = transporter.getRequestMark();
        String content = transporter.getContent();
        RemoteTransporter remoteTransporter = null;

        if (TransportDataConstant.AUTHORITY_HEARTBEAT.getCode().equals(requestyType)) {
            System.out.printf("接收到客户端的心跳包");
            remoteTransporter = RemoteTransporter.createRemoteTransporter(
                    UUID.randomUUID().toString(),
                    TransportDataConstant.AUTHORITY_HEARTBEAT.getCode(),
                    authoritySocketBeanDefination,
                    HeartBeatConnetion.createHeartBeatConnetion(authoritySocketBeanDefination));
        }

        if (TransportDataConstant.PROVIDER_REGISTRY_REQUEST.getCode().equals(requestyType)) {
            // 如果是注册消息，证明已经注册成功了，这里可以不用回复消息给注册的服务器，
            // 因为已经做了心跳以及重连，如果发生中断的话，服务端会自动申请注册
            remoteTransporter = RemoteTransporter.createRemoteTransporter(requestMark,
                    TransportDataConstant.AUTHORITY_REGISTRY_RESPONE.getCode(), baseComunicationBeanDefination,
                    AuthorityResponse.createAuthorityResponse(TransportDataConstant.AUTHORITY_REGISTRY_RESPONE_CONTENT.getCode()));
            // 解析 content 内容，获取注册的信息
            RegistrationDirectory.ServiceProviderBeanDefinationMap.put(transporter.getIp(), content);
        }

        if(TransportDataConstant.COSUMER_SUBSCRIBE.getCode().equals(requestyType)){
            System.out.printf("接收到消费者的订阅信息");
            remoteTransporter = RemoteTransporter.createRemoteTransporter(
                    UUID.randomUUID().toString(),
                    TransportDataConstant.COSUMER_SUBSCRIBE_RESPONE.getCode(),
                    authoritySocketBeanDefination,
                    HeartBeatConnetion.createHeartBeatConnetion(authoritySocketBeanDefination));
        }

        if(TransportDataConstant.CLIENT_AUTHORITY.getCode().equals(requestyType)){
            // 证明只是与注册中心建立连接而已，并没有注册服务以及订阅服务
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

