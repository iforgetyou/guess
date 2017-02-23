package com.zy17.guess.famous.service;


import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;

/**
 * Created by zy17 on 2016/3/11.
 */
public interface WeixinMsgHandle {
  /**
   * 是否能处理消息
   * @param msg
   * @return
   */
  boolean canHandle(EventMessage msg);

  /**
   * 消息处理逻辑
   * @param msg
   * @return
   */
  XMLMessage handleMsg(EventMessage msg);
}
