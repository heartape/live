package com.heartape.live.im.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupRequest {

    private String groupId;
    private String uid;
    private String message;
    private String token;

}
