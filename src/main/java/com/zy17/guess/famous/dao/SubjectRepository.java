package com.zy17.guess.famous.dao;

import com.zy17.guess.famous.entity.Subject;
import com.zy17.guess.famous.entity.Topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubjectRepository extends JpaRepository<Subject, Long> {
  Page<Subject> findAllBySubjectIdGreaterThan(long subjectId, Pageable pageable);
}