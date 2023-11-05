package com.heartape.live.im.jpa;

import com.heartape.live.im.jpa.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFileRepository extends JpaRepository<File, String>, QuerydslPredicateExecutor<File> {
}
