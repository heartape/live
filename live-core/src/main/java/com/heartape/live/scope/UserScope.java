package com.heartape.live.scope;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserScope {
    private String id;
    private List<String> scopes;
}
