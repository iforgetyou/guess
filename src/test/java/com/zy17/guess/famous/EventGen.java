package com.zy17.guess.famous;

import com.zy17.guess.famous.other.EventType;
import com.zy17.guess.famous.other.MsgType;

import java.util.Random;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.util.XMLConverUtil;

/**
 * 2017/2/24 zy17
 */
public class EventGen {
  public static EventMessage getTextEvent(String content) {
    //    构建文本消息
    EventMessage event = getbasic();
    event.setMsgType(MsgType.TEXT.getValue());
    event.setContent(content);
    System.out.println(XMLConverUtil.convertToXML(event));
    return event;
  }

  public static EventMessage getImageEvent() {
    EventMessage event = getbasic();
    event.setMsgType(MsgType.IMAGE.getValue());
    event.setUrl("url");
    event.setPicUrl("picurl");
    event.setMediaId("mediaid" + new Random().nextInt(100000));
    return event;
  }

  public static EventMessage getSubEvent() {
    EventMessage event = getbasic();
    event.setMsgType(MsgType.EVENT.getValue());
    event.setEvent(EventType.SUBSCRIBE.getValue());
    return event;
  }

  public static EventMessage getbasic() {
    EventMessage event = new EventMessage();
    event.setFromUserName("oAfFstyJxeBlRT-KdM4iWNB8V8Bg");
    event.setToUserName("1348831860");
    event.setCreateTime((int) System.currentTimeMillis());
    event.setMsgId("msgid" + new Random().nextInt(100000));
    return event;
  }
}
