package com.zy17.guess.famous.dao;

import com.zy17.guess.famous.entity.Subject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubjectRepository extends JpaRepository<Subject, String> {
  @Cacheable("subject")
  Subject findOne(String id);

  @Override
//  @CacheEvict("subject")
  Subject save(Subject entity);

  Page<Subject> findAllByTopicIdAndSubjectIdGreaterThan(String topicId, String subjectId, Pageable pageable);
}