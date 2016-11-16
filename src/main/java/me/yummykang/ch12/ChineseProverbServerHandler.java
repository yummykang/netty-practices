package me.yummykang.ch12;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

/**
 * write some dec. here.
 * Created by Demon on 2016/11/16 0016.
 */
public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final String[] DICTIONARY = {"只要功夫深，铁棒磨成针。", "旧时王谢堂前燕，飞入寻常百姓家。",
        "洛阳亲友如相问，一片冰心在玉壶。", "老骥伏枥，志在千里。烈士暮年，壮心不已。"};

    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String request = msg.content().toString(CharsetUtil.UTF_8);
        System.out.println(request);
        if ("谚语字典查询?".equals(request)) {
            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语查询结果：" + nextQuote(), CharsetUtil.UTF_8), msg.sender()));
        }
    }

    private String nextQuote() {
        int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quoteId];
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
