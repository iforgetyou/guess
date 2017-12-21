package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.EventMessageRepository;
import com.zy17.guess.famous.dao.ImageTagRepository;
import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.entity.ImageTag;
import com.zy17.guess.famous.other.CMDType;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.CacheService;
import com.zy17.guess.famous.service.DoubanService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;

/**
 * 添加标签处理
 * Created by zy17 on 2016/3/11.
 */
@Slf4j
@Component
public class TagMsgHandle implements WeixinMsgHandle {
  public static final String DEFAULT = "标签添加成功";
  public static final String DESCRIPTION = "添加成功，试试输入标签搜索下~";

  @Autowired
  CacheService cache;
  @Autowired
  EventMessageRepository dao;
  @Autowired
  ImageTagRepository imageTagDao;

  @Autowired
  DoubanService doubanService;

  @Override
  public boolean canHandle(weixin.popular.bean.message.EventMessage msg) {
    if (msg.getMsgType().equals(MsgType.TEXT.getValue()) && CMDType.notCmd(msg.getContent())) {
      if (cache.get(CacheService.getNewImageKey(msg.getFromUserName())) != null)
        return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(weixin.popular.bean.message.EventMessage msg) {
    String imageMsgId = (String) cache.pop(CacheService.getNewImageKey(msg.getFromUserName()));
    EventMessageEntity imageEntity = dao.findOne(imageMsgId);


    // 保存图片标签关系
    ImageTag imageTag = new ImageTag();
    imageTag.setTagMsgId(msg.getMsgId());
    imageTag.setTag(msg.getContent());
    imageTag.setImageMsgId(imageEntity.getMsgId());
    imageTag.setImageMediaId(imageEntity.getMsg().getMediaId());
    imageTagDao.save(imageTag);

    // 创建图文消息,提示用户图片对应的标签?
    ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();
    XMLNewsMessage.Article image = new XMLNewsMessage.Article();
    image.setTitle(DEFAULT + ": " + msg.getContent());
    image.setPicurl(imageEntity.getMsg().getPicUrl());
    image.setUrl(imageEntity.getMsg().getUrl());
    image.setDescription(DESCRIPTION);
    articles.add(image);

    articles.addAll(doubanService.searchMovieByName(msg.getContent()));

    XMLMessage resp = new XMLNewsMessage(
        msg.getFromUserName(),
        msg.getToUserName(),
        articles
    );

    //回复
    return resp;
  }
}
