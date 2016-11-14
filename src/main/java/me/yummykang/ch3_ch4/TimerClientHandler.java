package me.yummykang.ch3_ch4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/11/14 14:55
 */
public class TimerClientHandler extends ChannelHandlerAdapter {
    private static final Logger logger = Logger.getLogger(TimerClientHandler.class.getName());

    private int counter;

    private byte[] req;

    public TimerClientHandler() {
        req = ("Time" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("now is:" + body + ", the counter is:" + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("Unexpected exception from downstream:" + cause.getMessage());
        ctx.close();
    }
}
