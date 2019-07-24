package org.mercuryp.rpc.netty.channelhandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import lombok.Getter;
import lombok.Setter;
import org.mercuryp.rpc.netty.BaseCommonClient;
import org.mercuryp.rpc.springextensible.authority.AuthoritySocketBeanDefination;
import org.mercuryp.rpc.util.SpringContextHolder;


/**
 * @Description 定义看门狗重连处理类，当检查到断线时即可触发
 * @Author luobingkai
 * @Date 2019/7/21 17:08
 * @Version 1.0
 **/

@Setter
@Getter
public class ConnectionWatchDogHandler extends ChannelInboundHandlerAdapter {

    private Bootstrap bootstrap = null;
    private BaseCommonClient client = null;
    private AuthoritySocketBeanDefination authoritySocketBeanDefination = SpringContextHolder.getBean(AuthoritySocketBeanDefination.class);

    public static ConnectionWatchDogHandler createConnectionWatchDogHandler(Bootstrap _bootstrap, BaseCommonClient _client) {
        ConnectionWatchDogHandler connectionWatchDogHandler = new ConnectionWatchDogHandler();
        connectionWatchDogHandler.setBootstrap(_bootstrap);
        connectionWatchDogHandler.setClient(_client);
        return connectionWatchDogHandler;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        client.doConnect();
        ctx.fireChannelInactive();
    }

}
