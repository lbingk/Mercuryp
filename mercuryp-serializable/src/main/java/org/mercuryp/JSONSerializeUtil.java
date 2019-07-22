package org.mercuryp;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description 定义 json 序列化工具
 * @Author luobingkai
 * @Date 2019/7/22 1:25
 * @Version 1.0
 **/
public class JSONSerializeUtil {
    public static String serializer(Object obj) {
        return JSONObject.toJSONString(obj);
    }


    public static <T> T deserializer(String str, Class<T> c) {
        return JSONObject.parseObject(str, c);
    }
}
