package com.ssk.netty.protocol.server;


import com.ssk.netty.protocol.codec.NettyMessageDecoder;
import com.ssk.netty.protocol.codec.NettyMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {


    public static void main(String[] args) throws Exception {
        //ONE:
        //1 用于接受客户端连接的线程工作组
        EventLoopGroup boss = new NioEventLoopGroup();
        //2 用于对接受客户端连接读写操作的线程工作组
        EventLoopGroup work = new NioEventLoopGroup();

        //TWO:
        //3 辅助类。用于帮助我们创建NETTY服务
        ServerBootstrap b = new ServerBootstrap();
        b.group(boss, work)    //绑定两个工作线程组
                .channel(NioServerSocketChannel.class)    //设置NIO的模式
                .option(ChannelOption.SO_BACKLOG, 1024)    //设置TCP缓冲区
                //.option(ChannelOption.SO_SNDBUF, 32*1024)	// 设置发送数据的缓存大小
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)    // 设置接受数据的缓存大小
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)    // 设置保持连接
                .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                // 初始化绑定服务通道
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {

                        sc.pipeline().addLast(new NettyMessageDecoder(1024 * 1024 * 5, 4, 4));
                        sc.pipeline().addLast(new NettyMessageEncoder());
                        sc.pipeline().addLast(new ServerHandler());
                    }
                });

        ChannelFuture cf = b.bind(8765).sync();

        System.err.println("Server start... ");

        //释放连接
        cf.channel().closeFuture().sync();
        work.shutdownGracefully();
        boss.shutdownGracefully();
    }
}















