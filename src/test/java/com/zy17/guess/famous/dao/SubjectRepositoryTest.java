package com.zy17.guess.famous.dao;

import com.alibaba.fastjson.JSON;
import com.zy17.guess.famous.SpringBootTestBase;
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
public class SubjectRepositoryTest extends SpringBootTestBase{
  @Autowired
  TopicRepository topicRepository;
  @Autowired
  SubjectRepository subjectRepository;


  @Test
  public void findAllByTopicIdAndSubjectIdGreaterThan() throws Exception {
    String topicId = "topicId";
    Subject subject = new Subject();
    subject.setTopicId(topicId);
    subject.setAnswerA("A");
    subject.setAnswerB("B");
    subject.setAnswerC("C");
    subject.setAnswerD("D");
    subject.setCelebrityId("");
    subject.setAvatar("avatarurl");
    subject.setCelebrityUrl("");
    subject.setDescription("");
    subject.setRightAnswer("");


    System.out.println(JSON.toJSONString(subject));
    subjectRepository.save(subject);
    Pageable pageable = new PageRequest(0, 1);
    Page<Subject> allByTopicIdAndSubjectIdGreaterThan = subjectRepository.findAllByTopicIdAndSubjectIdGreaterThan(topicId, "", pageable);
    System.out.println(allByTopicIdAndSubjectIdGreaterThan);
    for (int i = 0; i < 10; i++) {
      subjectRepository.findOne(subject.getSubjectId());
//      System.out.println(one);
    }

  }


}