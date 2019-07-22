package org.mercuryp.rpc.provider.toauthority.netty.base;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.mercuryp.rpc.springextensible.authority.AuthorityBeanDefination;
import org.mercuryp.rpc.springextensible.provider.ProviderBeanDefination;
import org.mercuryp.rpc.util.SpringContextHolder;
import org.mercuryp.transport.metadata.DataConstant;
import org.mercuryp.transport.metadata.HeartBeatConnetion;
import org.mercuryp.transport.metadata.RemoteTransporter;

import java.util.UUID;

/**
 * @Description 定义处理chanel激活时的处理逻辑
 * @Author luobingkai
 * @Date 2019/7/20 3:09
 * @Version 1.0
 **/
public class BaseBussnessHandler extends ChannelInboundHandlerAdapter {

    private static ProviderBeanDefination providerBeanDefination = SpringContextHolder.getBean(ProviderBeanDefination.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                // 发送心跳包，证明两者在连接中，以便注册中心监测服务提供者
                RemoteTransporter remoteTransporter = RemoteTransporter.createRemoteTransporter(UUID.randomUUID().toString(),
                        DataConstant.AUTHORITY_PROVIDER_HEARTBEAT.getCode(),
                        providerBeanDefination.getIp(), providerBeanDefination.getPort(),
                        HeartBeatConnetion.createHeartBeatConnetion(providerBeanDefination.getIp(), providerBeanDefination.getPort()));

                System.out.println("发送心跳包");
                ctx.writeAndFlush(remoteTransporter);
            }
        }
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("BussnessHandler channelInactive");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("BussnessHandler channelActive");
        ctx.fireChannelActive();
    }

}
