package com.heartape.live.im.jpa;

import com.heartape.live.im.jpa.entity.SingleVideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSingleVideoRepository extends JpaRepository<SingleVideoEntity, String>, QuerydslPredicateExecutor<SingleVideoEntity> {
}
