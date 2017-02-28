package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.entity.Subject;
import com.zy17.guess.famous.other.CMDType;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.CacheService;
import com.zy17.guess.famous.service.SubjectService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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


  @Override
  public boolean canHandle(EventMessage msg) {
    String cmd = msg.getContent();
    if (msg.getMsgType().equals(MsgType.TEXT.getValue()) && cmd.equals(CMDType.SEARCH.getValue())) {
      return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {
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
