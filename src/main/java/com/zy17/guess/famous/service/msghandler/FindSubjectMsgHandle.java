package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.other.MsgType;
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
public class FindSubjectMsgHandle implements WeixinMsgHandle {
  @Autowired
  SubjectService subjectService;
  @Autowired
  CacheService cacheService;

  @Override
  public boolean canHandle(EventMessage msg) {
    String key = cacheService.getTopicKey(msg.getFromUserName());
    if (msg.getMsgType().equals(MsgType.TEXT.getValue()) && cacheService.get(key) != null) {
      // 已经选择topic
      return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) throws Exception {
    String key = cacheService.getTopicKey(msg.getFromUserName());
    Map<String, String> topicIds = (Map<String, String>) cacheService.pop(key);
    String topicId = topicIds.get(msg.getContent());
    String topicKeyId = cacheService.getTopicKeyId(msg.getFromUserName());
    cacheService.put(topicKeyId, topicId);
    XMLMessage resp = null;
    ArrayList<XMLNewsMessage.Article> articles = subjectService.getNextSubject(msg.getFromUserName());
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
