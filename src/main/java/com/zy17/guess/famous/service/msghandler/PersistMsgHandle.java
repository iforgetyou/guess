package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.EventMessageRepository;
import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;

/**
 * 保存所有用户消息
 * Created by zy17 on 2016/3/11.
 */
@Component
public class PersistMsgHandle implements WeixinMsgHandle {
  @Autowired
  EventMessageRepository repository;

  @Override
  public boolean canHandle(EventMessage msg) {
    return true;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {
    // 只存储,不做任何处理
    repository.save(new EventMessageEntity(msg));
    return null;
  }
}
