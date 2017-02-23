package com.zy17.guess.famous.service.msghandler;


import com.zy17.guess.famous.other.EventType;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

/**
 * 图片消息处理
 * Created by zy17 on 2016/3/11.
 */
@Slf4j
@Component
public class ImageMsgHandle implements WeixinMsgHandle {

  @Override
  public boolean canHandle(EventMessage msg) {
    if (msg.getMsgType().equals(EventType.IMAGE.getValue())) {
      return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {

    // 创建回复
    XMLMessage xmlTextMessage = new XMLTextMessage(
        msg.getFromUserName(),
        msg.getToUserName(),
        "收到图片,请回复文字添加标签");

    //回复
    return xmlTextMessage;
  }
}
