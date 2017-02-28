package com.zy17.guess.famous.dao;

import com.zy17.guess.famous.entity.Answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AnswerRepository extends JpaRepository<Answer, String> {
  /**
   * 返回历史回答问题id
   * @param openid
   * @return
   */
  @Query(value = "select questionId from Answer v where open_id=? group by v.questionId")
  List<String> findHisAnswersByOpenid(String openid);

  long countByOpenId(String openid);

  long countByOpenIdResult(String openid, boolean result);
}