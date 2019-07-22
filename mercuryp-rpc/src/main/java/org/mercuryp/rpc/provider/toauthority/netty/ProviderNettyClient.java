package org.mercuryp.rpc.provider.toauthority.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.mercuryp.rpc.annotation.RpcInvokerService;
import org.mercuryp.transport.handler.MsgpackDecoder;
import org.mercuryp.transport.handler.MsgpackEncoder;
import org.mercuryp.rpc.provider.toauthority.netty.chanelhandler.BussnessHandler;
import org.mercuryp.rpc.provider.toauthority.netty.chanelhandler.ConnectionWatchDogHandler;
import org.mercuryp.rpc.springextensible.authority.AuthorityBeanDefination;
import org.mercuryp.rpc.springextensible.provider.ProviderBeanDefination;
import org.mercuryp.rpc.util.SpringContextHolder;
import org.mercuryp.transport.metadata.*;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description 定义服务提供端与注册中心的 netty 客户端启动类
 * @Author luobingkai
 * @Date 2019/7/20 2:43
 * @Version 1.0
 **/

public class ProviderNettyClient {

    private static Bootstrap bootstrap = null;
    private Channel channel = null;
    private static AuthorityBeanDefination authorityBeanDefination = SpringContextHolder.getBean(AuthorityBeanDefination.class);
    private static ProviderBeanDefination providerBeanDefination = SpringContextHolder.getBean(ProviderBeanDefination.class);

    public void run() {
        // 配置 netty 的启动引导类
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.group(eventLoopGroup);
        bootstrap.remoteAddress(authorityBeanDefination.getIp(), authorityBeanDefination.getPort());
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
                channelPipeline.addLast(new ConnectionWatchDogHandler(bootstrap, ProviderNettyClient.this));
                channelPipeline.addLast(new BussnessHandler());
            }
        });
        doConnect();
    }

    public void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }
        ChannelFuture channelFuture = bootstrap.connect(authorityBeanDefination.getIp(), authorityBeanDefination.getPort());
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    Channel localChanel = future.channel();
                    RemoteTransporter remoteTransporter = getRemoteTransporter();
                    localChanel.writeAndFlush(remoteTransporter);
                    System.out.println("注册消息" + remoteTransporter.toString());
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

    private RemoteTransporter getRemoteTransporter() {
        return RemoteTransporter.createRemoteTransporter(UUID.randomUUID().toString(),
                DataConstant.PROVIDER_REGISTRY_REQUEST.getCode(),
                providerBeanDefination.getIp(), providerBeanDefination.getPort(),
                new ServiceProviderBeanDefinationInfo(getServiceProviderBean()));
    }

    private List<ServiceProviderBeanDefination> getServiceProviderBean() {
        List<ServiceProviderBeanDefination> serviceProviderBeanList = new ArrayList<>();
        Map<String, Object> beansWithAnnotationMap = SpringContextHolder.getBeansWithAnnotation(RpcInvokerService.class);
        if (!CollectionUtils.isEmpty(beansWithAnnotationMap)) {
            for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
                Class<?> aClass = entry.getValue().getClass();
                String aClassName = aClass.getName();
                Class<?>[] interfaces = aClass.getClass().getInterfaces();
                Method[] methods = aClass.getClass().getMethods();

                ServiceProviderBeanDefination beanDefination = new ServiceProviderBeanDefination();
                beanDefination.setInterfaceImplClassName(aClassName);
                for (Class<?> anInterface : interfaces) {
                    beanDefination.getInterfaceClassName().add(anInterface.getName());
                }
                for (Method method : methods) {
                    beanDefination.getMethodNameList().add(method.getName());
                }
                serviceProviderBeanList.add(beanDefination);
            }
        }
        return serviceProviderBeanList;
    }
}
