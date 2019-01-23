package com.ssk.netty.protocol.codec;

import com.ssk.netty.protocol.struct.Header;
import com.ssk.netty.protocol.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;

/**
 * 是为了解决 拆包粘包等问题的
 *
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-01-23-上午 9:47
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    private MarshallingDecoder marshallingDecoder;

    /**
     * @param maxFrameLength    第一个参数代表最大的长度   1024*1024*5
     * @param lengthFieldOffset 代表长度属性的偏移量 简单来说就是message中 总长度的起始位置   4
     * @param lengthFieldLength 代表长度属性的长度 整个属性占多长  4
     */
    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {

        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);

        this.marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        ByteBuf frame = (ByteBuf) super.decode(ctx, in);

        if (frame == null) {
            return null;
        }

        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionID(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());

        message.setHeader(header);

        if (frame.readableBytes() > 4) {
            message.setBody(marshallingDecoder.decode(frame));
        }

        return message;
    }
}
