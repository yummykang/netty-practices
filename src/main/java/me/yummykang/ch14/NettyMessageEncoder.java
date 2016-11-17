package me.yummykang.ch14;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.Map;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/11/17 10:28
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

    private NettyMarshallingEncoder encoder;

    public NettyMessageEncoder() {
        this.encoder = MarshallingCodecFactory.buildMarshallingEncoder();
    }

    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("The encode msg is null");
        }
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(msg.getHeader().getCrcCode());
        buffer.writeInt(msg.getHeader().getLength());
        buffer.writeLong(msg.getHeader().getSessionId());
        buffer.writeByte(msg.getHeader().getType());
        buffer.writeByte(msg.getHeader().getPriority());
        buffer.writeInt(msg.getHeader().getAttachment().size());

        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes("UTF-8");
            buffer.writeInt(keyArray.length);
            buffer.writeBytes(keyArray);
            value = param.getValue();
            encoder.encode(ctx, value, buffer);
        }
        key = null;
        keyArray = null;
        value = null;
        if (msg.getBody() != null) {
            encoder.encode(ctx, msg.getBody(), buffer);
        }
        int readableBytes = buffer.readableBytes();
        buffer.setInt(4, readableBytes);
        out.add(buffer);
    }
}
