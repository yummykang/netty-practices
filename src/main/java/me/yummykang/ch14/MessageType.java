package me.yummykang.ch14;

/**
 * 消息类型.
 *
 * @author demon
 * @Date 2016/11/17 15:42
 */
public enum MessageType {
    // 握手请求
    LOGIN_REQ((byte) 1),
    // 握手回应
    LOGIN_RESP((byte) 2),
    // 心跳请求
    HEART_BEAT_REQ((byte) 3),
    // 心跳回应
    HEART_BEAT_RESP((byte) 4);


    private byte type;

    MessageType(byte type) {
        this.type = type;
    }

    public byte value() {
        return type;
    }
}
