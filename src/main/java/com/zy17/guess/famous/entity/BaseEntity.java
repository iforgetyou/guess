package com.zy17.guess.famous.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * 2017/2/27 zy17
 */
@MappedSuperclass
public class BaseEntity {
  // 创建时间
  private Date created;
  private Date updated;

  @PrePersist
  protected void onCreate() {
    created = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    updated = new Date();
  }
}
