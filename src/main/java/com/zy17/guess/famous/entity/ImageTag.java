package com.zy17.guess.famous.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 2017/2/24 zy17
 */
@Data
@Entity
public class ImageTag {
  @Id
  String imageMsgId;
  String tagMsgId;
  String tag;
}
