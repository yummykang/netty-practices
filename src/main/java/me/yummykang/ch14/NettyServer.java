package me.yummykang.ch14;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/11/17 16:05
 */
public class NettyServer {

    public void bind() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0))
                                .addLast(new NettyMessageEncoder())
                                .addLast(new LoginAuthRespHandler())
                                .addLast(new HeartBeatRespHandler());
                    }
                });
        bootstrap.bind("127.0.0.1", 8080).sync();
        System.out.println("Netty server start ok:" + "127.0.0.1/" + 8080);
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer().bind();
    }
}
