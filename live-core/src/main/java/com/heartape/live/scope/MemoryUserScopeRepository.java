package com.heartape.live.scope;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 保存在内存中
 */
public class MemoryUserScopeRepository implements UserScopeRepository {

    private final Map<String, List<String>> map = new ConcurrentHashMap<>();

    public MemoryUserScopeRepository(UserScope... userScopes) {
        for (UserScope userScope : userScopes) {
            map.put(userScope.getId(), userScope.getScopes());
        }
    }

    @Override
    public void insert(UserScope userScope) {
        Objects.requireNonNull(userScope);
        List<String> scopes = userScope.getScopes();
        Objects.requireNonNull(scopes);
        String id = userScope.getId();
        Objects.requireNonNull(id);
        map.put(id, scopes);
    }

    @Override
    public List<String> select(String id) {
        return map.get(id);
    }

    @Override
    public boolean check(String id, LiveScope liveScope) {
        Objects.requireNonNull(id);
        List<String> list = map.get(id);
        return list != null && list.contains(liveScope.name());
    }

    @Override
    public void delete(String id) {
        map.remove(id);
    }
}
