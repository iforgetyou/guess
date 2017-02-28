package com.zy17.guess.famous.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 主题
 * 2017/2/28 zy17
 */
@Data
@Entity
public class Topic extends BaseEntity {
  @Id
  @GeneratedValue
  private String topicId;

  // 主题名称
  private String topicName;
  // 封面url
  private String coverImageUrl;
  // 描述
  private String description;
  // 专题详情url
  private String detailUrl;
}
