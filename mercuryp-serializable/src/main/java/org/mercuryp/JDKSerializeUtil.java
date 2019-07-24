package org.mercuryp;


import org.mercuryp.util.TransportDataConstant;

import java.io.*;


/**
 * @Description 定义Java序列化工具
 * @Author luobingkai
 * @Date 2019/7/20 0:44
 * @Version 1.0
 **/
public class JDKSerializeUtil {

    //序列化
    public static String serializeToString(Object obj) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = null;
        String str = null;
        try {
            objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(obj);
            //此处只能是ISO-8859-1,但是不会影响中文使用
            str = byteOut.toString(TransportDataConstant.valueOf("ISO_8859_1").getMsg().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }

    //反序列化
    public static Object deserializeToObject(String str) {
        ByteArrayInputStream byteIn = null;
        Object obj = null;
        try {
            byteIn = new ByteArrayInputStream(str.getBytes(TransportDataConstant.valueOf("ISO_8859_1").getMsg().toString()));
            ObjectInputStream objIn = new ObjectInputStream(byteIn);
            obj = objIn.readObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
