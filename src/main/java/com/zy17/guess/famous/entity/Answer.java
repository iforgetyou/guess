package com.zy17.guess.famous.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 2017/2/27 zy17
 */
@Data
@Entity
public class Answer extends BaseEntity {
  @Id
  @GeneratedValue
  long id;
  // 微信公众号id
  private String openId;
  // 问题id,相当于tagId
  private String questionId;
  // 答案内容
  private String answer;
  // 回答结果
  private boolean result;

}
