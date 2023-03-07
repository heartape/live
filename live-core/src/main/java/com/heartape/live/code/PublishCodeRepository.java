package com.heartape.live.code;

/**
 * 推流码储存库，用于管理推流权限。
 * <p>目前推流验证有两种比较简单的方式：
 * <li>基于推流码：与其他应用解耦；
 * <li>基于token：系统间权限实时性高，无需单独维护推流码；
 */
public interface PublishCodeRepository {

    void insert(PublishCode publishCode);

    String select(String code);

    void delete(String code);

}
