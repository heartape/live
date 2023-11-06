package com.heartape.live.im.jpa;

import com.heartape.live.im.jpa.entity.GroupLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLocationRepository extends JpaRepository<GroupLocationEntity, String>, QuerydslPredicateExecutor<GroupLocationEntity> {
}
