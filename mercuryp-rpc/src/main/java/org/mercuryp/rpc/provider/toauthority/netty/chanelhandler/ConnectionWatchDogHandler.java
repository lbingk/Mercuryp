package org.mercuryp.rpc.provider.toauthority.netty.chanelhandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import org.mercuryp.rpc.provider.toauthority.netty.ProviderNettyClient;
import org.mercuryp.rpc.springextensible.authority.AuthorityBeanDefination;
import org.mercuryp.rpc.util.SpringContextHolder;


/**
 * @Description 定义看门狗重连处理类，当检查到断线时即可触发
 * @Author luobingkai
 * @Date 2019/7/21 17:08
 * @Version 1.0
 **/

public class ConnectionWatchDogHandler extends ChannelInboundHandlerAdapter {

    private Bootstrap bootstrap = null;
    private static ProviderNettyClient client = null;
    private AuthorityBeanDefination authorityBeanDefination = SpringContextHolder.getBean(AuthorityBeanDefination.class);

    public ConnectionWatchDogHandler(Bootstrap _bootstrap,ProviderNettyClient _client) {
        bootstrap = _bootstrap;
        client=_client;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        client.doConnect();
        ctx.fireChannelInactive();
    }

}
