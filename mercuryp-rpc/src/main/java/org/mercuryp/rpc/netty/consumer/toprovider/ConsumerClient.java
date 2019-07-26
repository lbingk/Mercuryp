package org.mercuryp.rpc.netty.consumer.toprovider;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.mercuryp.rpc.netty.channelhandler.BussnessHandler;
import org.mercuryp.transport.handler.MsgpackDecoder;
import org.mercuryp.transport.handler.MsgpackEncoder;
import org.mercuryp.transport.metadata.RemoteTransporter;
import org.mercuryp.transport.metadata.RemoteTransporterHolder;
import org.mercuryp.transport.metadata.TransportDataConstant;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description 抽取客户端相同的代码
 * @Author luobingkai
 * @Date 2019/7/23 12:13
 * @Version 1.0
 **/
public class ConsumerClient {
    private Bootstrap bootstrap = null;
    private Channel channel = null;
    private RemoteTransporter remoteTransporterProvider = null;
    private RemoteTransporter remoteTransporterConsumer = null;

    public ConsumerClient(RemoteTransporter _remoteTransporterProvider, RemoteTransporter _remoteTransporterConsumer) {
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
                // TODO:暂时不考虑消费端会记录存活的消费提供者的地址，因此不用心跳以及重连机制
                // 心跳机制:4s周期没有发生事件，就触发userEventTriggered方法
//                socketChannel.pipeline().addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                socketChannel.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                socketChannel.pipeline().addLast("MessagePack encoder", new MsgpackEncoder());
                socketChannel.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                socketChannel.pipeline().addLast("MessagePack Decoder", new MsgpackDecoder());
                // 调用自定义的看门狗重连处理类，主要处理正常连接以及异常重连
//                channelPipeline.addLast(ConnectionWatchDogHandler.createConnectionWatchDogHandler(bootstrap, ConsumerClient.this));
                channelPipeline.addLast(new BussnessHandler());
            }
        });
        doConnect();
    }

    public void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }
        // TODO:未完成服务注册解析的编码，因此在此处需要写死
        ChannelFuture channelFuture = bootstrap.connect("localhost", 20880);

        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    Channel localChanel = future.channel();
                    // 发送消息给服务提供者
                    String requestMark = UUID.randomUUID().toString();
                    RemoteTransporter.createRemoteTransporter(requestMark, TransportDataConstant.CONSUMER_INVOKE_PROVIDER,)

                    RemoteTransporterHolder remoteTransporterHolder = RemoteTransporterHolder.getInstance();
                    remoteTransporterHolder.addRemoteTransporter(requestMark, )

                    localChanel.writeAndFlush(remoteTransporterProvider);
                    System.out.println("注册消息" + remoteTransporterProvider.toString());

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
