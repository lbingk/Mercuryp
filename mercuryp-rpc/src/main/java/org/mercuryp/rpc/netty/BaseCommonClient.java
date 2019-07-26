package org.mercuryp.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.mercuryp.rpc.netty.channelhandler.BussnessHandler;
import org.mercuryp.rpc.netty.channelhandler.ConnectionWatchDogHandler;
import org.mercuryp.rpc.springextensible.BaseComunicationBeanDefination;
import org.mercuryp.rpc.springextensible.authority.AuthoritySocketBeanDefination;
import org.mercuryp.rpc.util.SpringContextHolder;
import org.mercuryp.transport.handler.MsgpackDecoder;
import org.mercuryp.transport.handler.MsgpackEncoder;
import org.mercuryp.transport.metadata.HeartBeatConnetion;
import org.mercuryp.transport.metadata.RemoteTransporter;
import org.mercuryp.transport.metadata.TransportDataConstant;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description 抽取客户端相同的代码
 * @Author luobingkai
 * @Date 2019/7/23 12:13
 * @Version 1.0
 **/
public abstract class BaseCommonClient {
    private Bootstrap bootstrap = null;
    private Channel channel = null;
    private RemoteTransporter remoteTransporterProvider = null;
    private RemoteTransporter remoteTransporterConsumer = null;
    private AuthoritySocketBeanDefination authoritySocketBeanDefination = SpringContextHolder.getBean(AuthoritySocketBeanDefination.class);

    public BaseCommonClient(RemoteTransporter _remoteTransporterProvider, RemoteTransporter _remoteTransporterConsumer) {
        remoteTransporterProvider = _remoteTransporterProvider;
        remoteTransporterConsumer = _remoteTransporterConsumer;
    }

    public void run() {
        // 配置 neety 的启动引导类
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.group(eventLoopGroup);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline channelPipeline = socketChannel.pipeline();
                // 心跳机制:4s周期没有发生事件，就触发userEventTriggered方法
                socketChannel.pipeline().addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                socketChannel.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                socketChannel.pipeline().addLast("MessagePack encoder", new MsgpackEncoder());
                socketChannel.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                socketChannel.pipeline().addLast("MessagePack Decoder", new MsgpackDecoder());
                // 调用自定义的看门狗重连处理类，主要处理正常连接以及异常重连
                channelPipeline.addLast(ConnectionWatchDogHandler.createConnectionWatchDogHandler(bootstrap, BaseCommonClient.this));
                channelPipeline.addLast(new BussnessHandler());
            }
        });
        doConnect();
    }

    public void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }
        ChannelFuture channelFuture = bootstrap.connect(authoritySocketBeanDefination.getIp(), authoritySocketBeanDefination.getPort());
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    Channel localChanel = future.channel();
                    // 发送消息给配置中心
                    if (remoteTransporterProvider != null) {
                        localChanel.writeAndFlush(remoteTransporterProvider);
                        System.out.println("注册消息" + remoteTransporterProvider.toString());
                    }
                    if (remoteTransporterConsumer != null) {
                        localChanel.writeAndFlush(remoteTransporterConsumer);
                        System.out.println("订阅消息" + remoteTransporterConsumer.toString());
                    }
                    if (remoteTransporterProvider == null && remoteTransporterConsumer == null) {

                        BaseComunicationBeanDefination baseComunicationBeanDefination = new BaseComunicationBeanDefination();
                        InetSocketAddress localAddress = (InetSocketAddress) localChanel.localAddress();
                        baseComunicationBeanDefination.setIp(localAddress.getHostString());
                        baseComunicationBeanDefination.setPort(localAddress.getPort());
                        RemoteTransporter remoteTransporter = RemoteTransporter.createRemoteTransporter(UUID.randomUUID().toString(),
                                TransportDataConstant.CLIENT_AUTHORITY.getCode(), baseComunicationBeanDefination,  HeartBeatConnetion.createHeartBeatConnetion(baseComunicationBeanDefination));
                        System.out.println("注册消息" + remoteTransporter.toString());
                    }
                    System.out.println("链路连接成功");

                } else {

                    future.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect();
                        }
                    }, 5, TimeUnit.SECONDS);
                    System.out.println("正在尝试重连链路中......");
                }
            }
        });
    }

}
