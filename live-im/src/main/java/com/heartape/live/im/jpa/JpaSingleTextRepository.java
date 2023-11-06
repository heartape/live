package com.heartape.live.im.jpa;

import com.heartape.live.im.jpa.entity.SingleTextEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSingleTextRepository extends JpaRepository<SingleTextEntity, String>, QuerydslPredicateExecutor<SingleTextEntity> {
}
