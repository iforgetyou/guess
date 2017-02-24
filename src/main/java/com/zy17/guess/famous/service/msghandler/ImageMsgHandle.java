package com.zy17.guess.famous.service.msghandler;


import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.CacheService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  CacheService cache;

  @Override
  public boolean canHandle(EventMessage msg) {
    if (msg.getMsgType().equals(MsgType.IMAGE.getValue())) {
      return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {
    // 收到新图，清理缓存
    cache.delete(CacheService.getQuestionKey(msg.getFromUserName()));

    // 创建回复
    XMLMessage xmlTextMessage = new XMLTextMessage(
        msg.getFromUserName(),
        msg.getToUserName(),
        "收到图片,请在 " + CacheService.cacheTimeInMinutes + " 分钟内添加文字标签");

    // 缓存
    cache.put(CacheService.getNewImageKey(msg.getFromUserName()), msg.getMsgId());
    // 回复
    return xmlTextMessage;
  }
}
