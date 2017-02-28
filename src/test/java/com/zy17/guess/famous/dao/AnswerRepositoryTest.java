package com.zy17.guess.famous.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.zy17.guess.famous.entity.Answer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 2017/2/22 zy17
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AnswerRepositoryTest {
  @Autowired
  private AnswerRepository answerRepository;
  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void save() {
    Answer answer = new Answer();
    answer.setOpenId("fromuser");
    answer.setQuestionId("questId");
    answer.setResult(true);
    answer.setAnswer("无所谓");
    this.entityManager.persist(answer);
    Answer save = this.answerRepository.save(answer);
    assertThat(save.getOpenId()).isEqualTo(answer.getOpenId());
  }


  @Test
  public void hisAnswerTest() {
    Answer answer = new Answer();
    answer.setOpenId("1");
    answer.setQuestionId("questId1");
    answer.setResult(true);
    answer.setAnswer("无所谓");
    this.entityManager.persist(answer);
    this.answerRepository.save(answer);

    Answer answerB = new Answer();
    answerB.setOpenId("1");
    answerB.setQuestionId("questId2");
    answerB.setResult(true);
    answerB.setAnswer("无所谓");
    this.entityManager.persist(answerB);
    this.answerRepository.save(answerB);
    List<String> hisAnswers = answerRepository.findHisAnswersByOpenid("1");
    System.out.println(JSON.toJSONString(hisAnswers));
    assertThat(hisAnswers.size()).isEqualTo(2);
  }

}