package com.heartape.live.im.jpa;


import com.heartape.live.im.jpa.entity.GroupSoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSoundRepository extends JpaRepository<GroupSoundEntity, String>, QuerydslPredicateExecutor<GroupSoundEntity> {
}
