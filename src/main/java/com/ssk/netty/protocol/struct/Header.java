package com.ssk.netty.protocol.struct;

import lombok.Data;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-01-23-上午 9:37
 */
@Data
public class Header {
    /**
     * 唯一的通信标志
     */
    private int crcCode = 0xfffffff;

    /**
     * 总消息的长度  header + body
     */
    private int length;

    /**
     * 会话ID
     */
    private long sessionID;

    /**
     * 消息的类型
     */
    private byte type;

    /**
     * 消息的优先级
     */
    private byte priority;
}
