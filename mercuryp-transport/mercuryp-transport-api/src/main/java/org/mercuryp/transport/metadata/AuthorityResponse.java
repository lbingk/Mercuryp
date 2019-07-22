package org.mercuryp.transport.metadata;

import lombok.Getter;
import lombok.Setter;
import org.msgpack.annotation.Message;

/**
 * @Description 定义注册中心相应消息体
 * @Author luobingkai
 * @Date 2019/7/22 19:20
 * @Version 1.0
 **/
@Setter
@Getter
@Message
public class AuthorityResponse extends CommonCustomerBody {
    private String content;

    public static AuthorityResponse createAuthorityResponse(String _content) {
        AuthorityResponse authorityResponse = new AuthorityResponse();
        authorityResponse.setContent(_content);
        return authorityResponse;
    }

    @Override
    public String toString() {
        return content;
    }

    private AuthorityResponse() {
    }
}
