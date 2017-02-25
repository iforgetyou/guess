package com.zy17.guess.famous.service.msghandler;


import com.zy17.guess.famous.other.CMDType;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.ImageService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;

/**
 * 随机取图片命令消息处理
 * Created by zy17 on 2016/3/11.
 */
@Slf4j
@Component
public class RandomImageMsgHandle implements WeixinMsgHandle {
  @Autowired
  ImageService imageService;

  @Override
  public boolean canHandle(weixin.popular.bean.message.EventMessage msg) {
    String cmd = msg.getContent();
    //
    if (msg.getMsgType().equals(MsgType.TEXT.getValue()) && cmd.equals(CMDType.SEARCH.getValue())) {
      return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {
    //回复
    return imageService.getRandomImage(msg.getFromUserName(), msg.getToUserName());
  }


}
