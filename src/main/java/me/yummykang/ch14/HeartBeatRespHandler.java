package me.yummykang.ch14;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/11/17 15:37
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {

    private NettyMessage buildHeartBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEART_BEAT_RESP.value());
        message.setHeader(header);
        return message;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEART_BEAT_REQ.value()) {
            System.out.println("Receive client heart beat message:---->" + message);
            NettyMessage heartBeat = buildHeartBeat();
            System.out.println("Send heart beat response message to client:---->" + heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
