package com.ssk.netty.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-01-23-上午 10:00
 */
public class MarshallingEncoder {

    /**
     * 空白占位: 用于预留设置 body的数据包长度
     */
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    private Marshaller marshaller;

    public MarshallingEncoder() throws IOException {

        this.marshaller = MarshallingCodeCFactory.buildMarshaller();
    }


    public void encode(Object body, ByteBuf out) throws IOException {

        try {
            //当前数据读取的位置 ：起始数据位置
            int pos = out.writerIndex();
            //
            out.writeBytes(LENGTH_PLACEHOLDER);

            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);

            marshaller.start(output);

            marshaller.writeObject(body);

            marshaller.finish();
            //总长度(结束位置) - 初始化长度(起始位置) - 预留的长度  = body数据长度
            out.setInt(pos, out.writerIndex() - pos - 4);

        } finally {
            marshaller.close();
        }

    }
}
