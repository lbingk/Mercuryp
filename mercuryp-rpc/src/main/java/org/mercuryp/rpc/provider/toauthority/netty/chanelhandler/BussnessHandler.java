package org.mercuryp.rpc.provider.toauthority.netty.chanelhandler;

import io.netty.channel.ChannelHandlerContext;
import org.mercuryp.rpc.provider.toauthority.netty.base.BaseBussnessHandler;
import org.mercuryp.rpc.springextensible.authority.AuthorityBeanDefination;
import org.mercuryp.rpc.util.SpringContextHolder;
import org.mercuryp.transport.metadata.AuthorityResponse;
import org.mercuryp.transport.metadata.DataConstant;
import org.mercuryp.transport.metadata.RemoteTransporter;
import org.msgpack.MessagePack;

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
        String requestMark = transporter.getRequestMark();
        String content = transporter.getContent();
        RemoteTransporter remoteTransporter = null;
        System.out.println("provider server channelRead: " + transporter.toString());

        if (DataConstant.AUTHORITY_REGISTRY_RESPONE.getCode().equals(requestyType)) {
            // 如果是注册消息，证明已经注册成功了，应该回复消息给注册的服务器
            remoteTransporter = RemoteTransporter.createRemoteTransporter(
                    requestMark,
                    DataConstant.AUTHORITY_REGISTRY_RESPONE.getCode(),
                    authorityBeanDefination.getIp(),
                    authorityBeanDefination.getPort(),
                    AuthorityResponse.createAuthorityResponse(DataConstant.AUTHORITY_REGISTRY_RESPONE_CONTENT.getCode()));
        }

//        if (remoteTransporter == null) {
//            throw new IllegalArgumentException("消息不符合设定的请求类型");
//        }
//        ctx.writeAndFlush(remoteTransporter);
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

