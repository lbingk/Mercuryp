package org.mercuryp;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * @Description 定义Protostuff 的序列化工具
 * @Author luobingkai
 * @Date 2019/7/21 23:15
 * @Version 1.0
 **/
public class ProtostuffSeriailizeUtil {
    public static <T> byte[] serializer(T t) {
        Schema schema = RuntimeSchema.getSchema(t.getClass());
        return ProtostuffIOUtil.toByteArray(t, schema,
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

    }


    public static <T> T deserializer(byte[] bytes, Class<T> c) {
        T t = null;
        try {
            t = c.newInstance();
            Schema schema = RuntimeSchema.getSchema(t.getClass());
            ProtostuffIOUtil.mergeFrom(bytes, t, schema);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

}
