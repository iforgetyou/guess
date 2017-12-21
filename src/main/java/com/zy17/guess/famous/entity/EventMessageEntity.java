package com.zy17.guess.famous.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy17.guess.famous.dao.EventJpaJsonConverter;


import lombok.Data;

import org.springframework.beans.BeanUtils;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import weixin.popular.bean.message.EventMessage;

@Data
@Entity
public class EventMessageEntity {

  public EventMessageEntity() {
  }

  public EventMessageEntity(weixin.popular.bean.message.EventMessage msg) {
      this.msg = msg;
      BeanUtils.copyProperties(msg, this);
  }
  //base
  @Id
  private String msgId;                        //消息ID号

  private String toUserName;                //开发者微信号

  private String fromUserName;        //发送方帐号（一个OpenID）

  private Integer createTime;                //消息创建时间 （整型）

  private String msgType;                        //消息类型，event

  private String event;                        //事件类型，subscribe(订阅)、unsubscribe(取消订阅)

  @Convert(converter = EventJpaJsonConverter.class)
  @Column(columnDefinition = "TEXT")
  private EventMessage msg;
}