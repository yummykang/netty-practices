package me.yummykang.ch14;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/11/17 15:52
 */
public class NettyClient {
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private EventLoopGroup group = new NioEventLoopGroup();

    public void connect(final String host, final int port) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0))
                                .addLast(new NettyMessageEncoder())
                                .addLast(new ReadTimeoutHandler(50))
                                .addLast(new LoginAuthReqHandler())
                                .addLast(new HeartBeatReqHandler());

                    }
                });
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            System.out.println("Netty time Client connected at port " + port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        connect(host, port);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        new NettyClient().connect("127.0.0.1", 8080);
    }
}
