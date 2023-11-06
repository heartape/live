package com.heartape.live.im.jpa;

import com.heartape.live.im.jpa.entity.SingleFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSingleFileRepository extends JpaRepository<SingleFileEntity, String>, QuerydslPredicateExecutor<SingleFileEntity> {
}