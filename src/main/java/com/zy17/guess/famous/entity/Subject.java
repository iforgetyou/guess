package com.zy17.guess.famous.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 题目
 * 2017/2/28 zy17
 */
@Data
@Entity
public class Subject extends BaseEntity {
  @Id
  @GeneratedValue
  private String subjectId;
  @Column(nullable = false)
  private String topicId;
  // 名人id
  private String celebrityId;
  // 名人头像
  private String avatar;
  // 描述
  private String description;
  // 大图详情
  private String celebrityUrl;

  // 正确答案
  private String rightAnswer;

  private String answerA;
  private String answerB;
  private String answerC;
  private String answerD;
}
