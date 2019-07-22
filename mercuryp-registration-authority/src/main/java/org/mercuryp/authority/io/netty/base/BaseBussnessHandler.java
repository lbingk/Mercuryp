package org.mercuryp.authority.io.netty.base;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.mercuryp.authority.registry.RegistrationDirectory;

import java.net.InetSocketAddress;


/**
 * @Description 定义处理读的处理
 * @Author luobingkai
 * @Date 2019/7/20 3:09
 * @Version 1.0
 **/
public class BaseBussnessHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                // 获取失去连接的客户端，此时认为已经断开了，需要客户端重新注册
                Channel channel = ctx.channel();
                InetSocketAddress ipSocket = (InetSocketAddress) channel.remoteAddress();
                String clientIp = ipSocket.getAddress().getHostAddress();
                synchronized (channel){
                    // 移出存储的服务提供者信息
                    RegistrationDirectory.ServiceProviderBeanDefinationMap.remove(clientIp);
                    // 关闭通道
                    ctx.close();
                }
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("BussnessHandler channelActive");
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("BussnessHandler channelInactive");
    }
}
