package com.heartape.live.scope;

import java.util.List;

/**
 * 用户权限储存库，用于管理观看权限等
 */
public interface UserScopeRepository {

    void insert(UserScope userScope);

    List<String> select(String id);

    /**
     * 通过凭证验证
     * @param certificate 凭证，可以是用户id，也可以是token等
     * @param liveScope 权限范围
     * @return 是否允许访问
     */
    boolean check(String certificate, LiveScope liveScope);

    void delete(String id);

}
