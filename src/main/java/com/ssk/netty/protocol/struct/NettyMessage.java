package com.ssk.netty.protocol.struct;

import lombok.Data;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-01-23-上午 9:38
 */
@Data
public final class NettyMessage {

    private Header header;

    private Object body;


}
