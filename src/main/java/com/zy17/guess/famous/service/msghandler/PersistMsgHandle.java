package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.EventMessageRepository;
import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

import weixin.popular.bean.xmlmessage.XMLMessage;

/**
 * 保存所有用户消息
 * Created by zy17 on 2016/3/11.
 */
@Component
public class PersistMsgHandle implements WeixinMsgHandle {
  @Autowired
  EventMessageRepository repository;
  Random random = new Random();

  @Override
  public boolean canHandle(weixin.popular.bean.message.EventMessage msg) {
    return true;
  }

  @Override
  public XMLMessage handleMsg(weixin.popular.bean.message.EventMessage msg) {
    // 只存储,不做任何处理
    EventMessageEntity entity = new EventMessageEntity(msg);
    if (msg.getMsgType().equals(MsgType.EVENT.getValue())) {
      // event 没有msgId, 同一秒有并发问题

      entity.setMsgId("e_" + msg.getCreateTime() + random.nextInt(100));
    }
    repository.save(entity);
    return null;
  }
}
