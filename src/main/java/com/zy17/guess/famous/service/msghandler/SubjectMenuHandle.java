package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.other.EventType;
import com.zy17.guess.famous.other.MenuAnswerEnum;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.other.SubjectButtonEnum;
import com.zy17.guess.famous.service.CacheService;
import com.zy17.guess.famous.service.SubjectService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;

/**
 * Created by zy17 on 2016/3/11.
 */
@Component
public class SubjectMenuHandle implements WeixinMsgHandle {
  @Autowired
  SubjectService subjectService;
  @Autowired
  CacheService cacheService;

  @Override
  public boolean canHandle(EventMessage msg) {
      if (msg.getMsgType().equals(MsgType.EVENT.getValue())&&msg.getEvent().equals(EventType.CLICK.getValue()) ) {
      if (SubjectButtonEnum.convertEnum(msg.getEventKey())!=null){
        return true;
      }
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) throws Exception {
    String key = CacheService.getTopicKey(msg.getFromUserName());
    SubjectButtonEnum subjectButtonEnum = SubjectButtonEnum.convertEnum(msg.getEventKey());
    String topicId = subjectButtonEnum.getSubjectId();
    String topicKeyId = CacheService.getTopicKeyId(msg.getFromUserName());
    cacheService.put(topicKeyId, topicId);
    XMLMessage resp = null;
    ArrayList<XMLNewsMessage.Article> articles = subjectService.getSubject(msg.getFromUserName(),topicId);
    if (articles.size() > 0) {
      resp = new XMLNewsMessage(
          msg.getFromUserName(),
          msg.getToUserName(),
          articles);
    }
    //回复
    return resp;
  }
}
