package org.mercuryp.authority.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.mercuryp.authority.registry.SpringContextHolder;
import org.mercuryp.authority.springextensible.AuthorityBean;

/**
 * @Description 定义注册中心的 netty 通讯服务端的启动类
 * @Author luobingkai
 * @Date 2019/7/20 2:43
 * @Version 1.0
 **/

public class AuthorityNettyServer {

    public static void run() {
        // 获取配置的参数：port 以及 timetout
        AuthorityBean bean = SpringContextHolder.getBean(AuthorityBean.class);
        // 创建Boss：作用于客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 创建woker：作用于迭代器可用的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 创建 ServerBootstrap：启动类
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 配置参数
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // TODO: 解码，编码以及业务逻辑处理链
                        socketChannel.pipeline().addLast();
                    }
                }).
                // 缓冲区
                        option(ChannelOption.SO_BACKLOG, 128);
        try {
            ChannelFuture channelFuture = bootstrap.bind(Integer.valueOf(bean.getPort())).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 异常情况优雅关闭
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
