package com.zy17.guess.famous.dao;

import com.zy17.guess.famous.entity.Subject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubjectRepository extends JpaRepository<Subject, String> {
  Page<Subject> findAllByTopicIdAndSubjectIdGreaterThan(String topicId, String subjectId, Pageable pageable);
}