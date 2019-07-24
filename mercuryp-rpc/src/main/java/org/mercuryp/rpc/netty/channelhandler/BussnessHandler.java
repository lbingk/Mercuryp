package org.mercuryp.rpc.netty.channelhandler;

import io.netty.channel.ChannelHandlerContext;
import org.mercuryp.rpc.netty.BaseBussnessHandler;
import org.mercuryp.rpc.springextensible.authority.AuthoritySocketBeanDefination;
import org.mercuryp.rpc.util.SpringContextHolder;
import org.mercuryp.transport.metadata.AuthorityResponse;
import org.mercuryp.transport.metadata.TransportDataConstant;
import org.mercuryp.transport.metadata.RemoteTransporter;
import org.msgpack.MessagePack;

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
        String requestMark = transporter.getRequestMark();
        String content = transporter.getContent();
        RemoteTransporter remoteTransporter = null;

        String preMsg = "";
        if (TransportDataConstant.AUTHORITY_REGISTRY_RESPONE.getCode().equals(requestyType)) {
            // 如果是注册消息
            preMsg = "服务成功注册后收到注册中心的相应信息: ";
        }
        if (TransportDataConstant.COSUMER_SUBSCRIBE_RESPONE.getCode().equals(requestyType)) {
            // 如果是订阅消息
            preMsg = "服务成功注册后收到注册中心的相应信息: ";
        }

        System.out.println(preMsg + transporter.toString());
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}

