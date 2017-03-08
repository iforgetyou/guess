package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.TopicRepository;
import com.zy17.guess.famous.entity.Topic;
import com.zy17.guess.famous.other.CMDType;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.CacheService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;

/**
 * 主题选择
 * Created by zy17 on 2016/3/11.
 */
@Component
public class FindTopicMsgHandle implements WeixinMsgHandle {
  public static final String DEFAULT = "消息已收到,功能仍在完善,请持续关注^-^";
  @Autowired
  TopicRepository topicRepository;
  @Autowired
  CacheService cacheService;

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
    Pageable pageable = new PageRequest(0, 10);
    Page<Topic> topics = topicRepository.findAll(pageable);

    if (topics.getSize() > 0) {
      // 创建图文消息,提示用户图片对应的标签?
      ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();
      HashMap<String, String> cacheValue = new HashMap<>();
      for (int i = 0; i < topics.getContent().size(); i++) {
        Topic topic = topics.getContent().get(i);
        XMLNewsMessage.Article a = new XMLNewsMessage.Article();
        a.setTitle(topic.getTopicName());
        a.setDescription("回复" + (i + 1) + ": " + topic.getDescription());
        a.setPicurl(topic.getCoverImageUrl());
        a.setUrl(topic.getDetailUrl());
        articles.add(a);
        cacheValue.put(String.valueOf(i), topic.getTopicId());
      }

      resp = new XMLNewsMessage(
          msg.getFromUserName(),
          msg.getToUserName(),
          articles
      );

      String key = cacheService.getTopicKey(msg.getFromUserName());
      cacheService.put(key, cacheValue);
    }
    //回复
    return resp;
  }
}
