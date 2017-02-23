package com.zy17.guess.famous.service.msghandler;


import com.zy17.guess.famous.dao.EventMessageRepository;
import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.other.EventType;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Random;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLImageMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;

/**
 * 随机取图片命令消息处理
 * Created by zy17 on 2016/3/11.
 */
@Slf4j
@Component
public class RandomImageMsgHandle implements WeixinMsgHandle {
  @Autowired
  EventMessageRepository repository;
  Random random = new Random();

  @Override
  public boolean canHandle(EventMessage msg) {
    String cmd = msg.getContent();
    //
    if (msg.getMsgType().equals(EventType.TEXT.getValue()) && cmd.equals("1")) {
      return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {
    long count = repository.countAllByMsgType(EventType.IMAGE.getValue());
    if (count == 0) {
      //  还没有任何图片
      return null;
    }
    // 随机从找一张用户传图
    Pageable pageable = new PageRequest(random.nextInt((int) count), 1);
    Page<EventMessageEntity> all = repository.findAllByMsgType(EventType.IMAGE.getValue(), pageable);
    EventMessageEntity eventMessageEntity = all.getContent().get(0);

    // 创建回复
    XMLMessage resp = new XMLImageMessage(
        msg.getFromUserName(),
        msg.getToUserName(),
        eventMessageEntity.getMediaId()
    );
    //回复
    return resp;
  }
}
