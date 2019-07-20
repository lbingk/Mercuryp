package org.mercuryp.util;

/**
 * @Description 定义枚举类
 * @Author luobingkai
 * @Date 2019/7/20 0:44
 * @Version 1.0
 **/
public enum DataConstant {
    // 编码格式
    UTF8("ISUTF-8","UTF-8"),
    ISO_8859_1("ISO_8859_1","ISO-8859-1");



    private String code;
    private Object msg;
    DataConstant(String _code, Object _msg) {
        code = _code;
        msg = _msg;
    }
    public String getCode() {
        return code;
    }
    public Object getMsg() {
        return msg;
    }
}
