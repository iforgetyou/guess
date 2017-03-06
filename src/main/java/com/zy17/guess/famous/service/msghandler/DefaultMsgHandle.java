package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.service.WeixinMsgHandle;

import org.springframework.stereotype.Component;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

/**
 * 微信默认消息处理
 * Created by zy17 on 2016/3/11.
 */
@Component
public class DefaultMsgHandle implements WeixinMsgHandle {
  public static final String DEFAULT = "消息已收到,功能仍在完善,请持续关注^-^\n" + FirstLoginHandle.HELP;

  @Override
  public boolean canHandle(EventMessage msg) {
    return true;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {
    // 创建回复
    XMLMessage resp = new XMLTextMessage(
        msg.getFromUserName(),
        msg.getToUserName(),
        DEFAULT
    );
    //回复
    return resp;
  }
}
