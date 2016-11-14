package me.yummykang.ch7;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * write some dec. here.
 * Created by Demon on 2016/11/14 0014.
 */
public class SubClientHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(req(i));
        }
        ctx.flush();
    }

    private SubscribeReq req(int i) {
        SubscribeReq req = new SubscribeReq();
        req.setSubReqId(i);
        req.setUserName("demon");
        req.setProductName("一包空气");
        req.setPhoneNumber("150569981XX");
        req.setAddress("中国的某个地方");
        return req;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive from server:[" + msg + "]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
