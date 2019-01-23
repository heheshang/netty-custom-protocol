package com.ssk.netty.protocol.codec;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-01-23-上午 9:47
 */
public final class MarshallingCodeCFactory {

    public static Marshaller buildMarshaller() throws IOException {

        //首先通过Marshalling工具类的精通方法获取Marshalling实例对象 参数serial标识创建的是java序列化工厂对象。
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        //创建了MarshallingConfiguration对象，配置了版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();

        configuration.setVersion(5);

        Marshaller marshaller = factory.createMarshaller(configuration);

        return marshaller;
    }

    public static Unmarshaller buildUnMarshaller() throws IOException {

        //首先通过Marshalling工具类的精通方法获取Marshalling实例对象 参数serial标识创建的是java序列化工厂对象。
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        //创建了MarshallingConfiguration对象，配置了版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();

        configuration.setVersion(5);

        Unmarshaller unmarshaller = factory.createUnmarshaller(configuration);

        return unmarshaller;
    }

}
