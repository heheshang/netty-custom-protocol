package com.ssk.netty.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-01-23-上午 10:00
 */
public class MarshallingDecoder {

    /**
     * 空白占位: 用于预留设置 body的数据包长度
     */
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    private Unmarshaller unmarshaller;

    public MarshallingDecoder() throws IOException {

        this.unmarshaller = MarshallingCodeCFactory.buildUnMarshaller();
    }


    public Object decode(ByteBuf in) throws IOException, ClassNotFoundException {

        try {
            //读取body 的长度
            int bodySize = in.readInt();
            // 读取实际body 的缓冲内容

            ByteBuf buf = in.slice(in.readerIndex(), bodySize);
            //转换数据
            ChannelBufferByteInput input = new ChannelBufferByteInput(buf);
            //读取操作
            unmarshaller.start(input);

            Object ret = this.unmarshaller.readObject();

            unmarshaller.finish();
            // 读取完毕以后, 更新当前读取起始位置:
            in.readerIndex(in.readerIndex() + bodySize);

            return ret;
        } finally {
            unmarshaller.close();
        }

    }
}
