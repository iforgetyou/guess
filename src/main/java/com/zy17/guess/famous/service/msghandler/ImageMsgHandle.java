package com.zy17.guess.famous.service.msghandler;


import com.zy17.guess.famous.dao.EventMessageRepository;
import com.zy17.guess.famous.dao.ImageTagRepository;
import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.entity.ImageTag;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.CacheService;
import com.zy17.guess.famous.service.DoubanService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.ResponseCache;
import java.util.ArrayList;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

/**
 * 图片消息处理
 * Created by zy17 on 2016/3/11.
 */
@Slf4j
@Component
public class ImageMsgHandle implements WeixinMsgHandle {
  public static final String DEFAULT = "标签添加成功";
  public static final String DESCRIPTION = "添加成功，试试输入标签搜索下~";

  @Autowired
  CacheService cache;
  @Autowired
  ImageTagRepository imageTagDao;
  @Autowired
  DoubanService doubanService;
  @Autowired
  EventMessageRepository eventRepository;

  @Override
  public boolean canHandle(EventMessage msg) {
    if (msg.getMsgType().equals(MsgType.IMAGE.getValue())) {
      return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {
    XMLMessage resp = null;
    // 收到新图，清理缓存
    String userName = msg.getFromUserName();
    cache.delete(CacheService.getQuestionKey(userName));
    String key = CacheService.getNoImageKey(userName);
    if (cache.get(key) != null) {
      String tagMsgId = (String) cache.get(key);
      EventMessageEntity tagMsg = eventRepository.findOne(tagMsgId);
      // 保存图片标签关系
      ImageTag imageTag = new ImageTag();
      imageTag.setTagMsgId(tagMsg.getMsgId());
      imageTag.setTag(tagMsg.getMsg().getContent());
      imageTag.setImageMsgId(msg.getMsgId());
      imageTag.setImageMediaId(msg.getMediaId());
      imageTagDao.save(imageTag);

      // 创建图文消息,提示用户图片对应的标签?
      ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();
      XMLNewsMessage.Article image = new XMLNewsMessage.Article();
      image.setTitle(DEFAULT + ": " + msg.getContent());
      image.setPicurl(msg.getPicUrl());
      image.setUrl(msg.getUrl());
      image.setDescription(DESCRIPTION);
      articles.add(image);

      articles.addAll(doubanService.searchMovieByName(tagMsg.getMsg().getContent()));

      resp = new XMLNewsMessage(
          msg.getFromUserName(),
          msg.getToUserName(),
          articles
      );
    } else {
      // 创建回复
      resp = new XMLTextMessage(
          userName,
          msg.getToUserName(),
          "收到图片,请在 " + CacheService.cacheTimeInMinutes + " 分钟内添加文字标签\n");
      // 缓存
      cache.put(CacheService.getNewImageKey(userName), msg.getMsgId());
    }

    // 回复
    return resp;
  }
}
