package me.yummykang.ch14;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/11/17 11:26
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
    private NettyMarshallingDecoder decoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset,
                               int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        decoder = MarshallingCodecFactory.buildMarshallingDecoder();
    }

    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionId(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());

        int size = frame.readInt();
        if (size > 0) {
            Map<String, Object> attach = new HashMap<String, Object>(size);
            int keySize = 0;
            byte[] keyArr = null;
            String key = null;
            for (int i = 0; i < size; i++) {
                keySize = frame.readInt();
                keyArr = new byte[keySize];
                in.readBytes(keyArr);
                key = new String(keyArr, "UTF-8");
                attach.put(key, decoder.decode(ctx, frame));
            }
            key = null;
            keyArr = null;
            header.setAttachment(attach);
        }
        if (frame.readableBytes() > 0) {
            message.setBody(decoder.decode(ctx, frame));
        }
        message.setHeader(header);
        return message;
    }

}
