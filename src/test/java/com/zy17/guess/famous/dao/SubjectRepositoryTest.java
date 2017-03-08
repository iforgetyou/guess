package com.zy17.guess.famous.dao;

import com.zy17.guess.famous.entity.Subject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 2017/3/8 zy17
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class SubjectRepositoryTest {
  @Autowired
  TopicRepository topicRepository;
  @Autowired
  SubjectRepository subjectRepository;

  @Test
  public void findAllByTopicIdAndSubjectIdGreaterThan() throws Exception {
    Subject entity = new Subject();
    String topicId = "topicId";
    entity.setTopicId(topicId);
    subjectRepository.save(entity);
    Pageable pageable = new PageRequest(0, 1);
    Page<Subject> allByTopicIdAndSubjectIdGreaterThan = subjectRepository.findAllByTopicIdAndSubjectIdGreaterThan(topicId, "", pageable);
    System.out.println(allByTopicIdAndSubjectIdGreaterThan);
  }

}