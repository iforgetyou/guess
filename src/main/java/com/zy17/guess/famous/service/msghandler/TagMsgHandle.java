package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.EventMessageRepository;
import com.zy17.guess.famous.dao.ImageTagRepository;
import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.entity.ImageTag;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.CacheService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;

/**
 * 添加标签处理
 * Created by zy17 on 2016/3/11.
 */
@Component
public class TagMsgHandle implements WeixinMsgHandle {
  public static final String DEFAULT = "标签添加成功";

  @Autowired
  CacheService cache;
  @Autowired
  EventMessageRepository dao;
  @Autowired
  ImageTagRepository imageTagDao;

  @Override
  public boolean canHandle(weixin.popular.bean.message.EventMessage msg) {
    if (msg.getMsgType().equals(MsgType.TEXT.getValue())) {
      if (cache.get(CacheService.getNewImageKey(msg.getFromUserName())) != null)
        return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(weixin.popular.bean.message.EventMessage msg) {
    String imageMsgId = (String) cache.pop(CacheService.getNewImageKey(msg.getFromUserName()));
    EventMessageEntity imageEntity = dao.findOne(imageMsgId);

    // 创建图文消息,提示用户图片对应的标签?
    ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();
    XMLNewsMessage.Article image = new XMLNewsMessage.Article();
    image.setTitle(DEFAULT + ":" + msg.getContent());
    image.setPicurl(imageEntity.getPicUrl());
    image.setUrl(imageEntity.getUrl());
    image.setDescription("添加成功，试试输入标签搜索下~");
    articles.add(image);
    // 保存图片标签关系
    ImageTag imageTag = new ImageTag();
    imageTag.setTagMsgId(msg.getMsgId());
    imageTag.setTag(msg.getContent());
    imageTag.setImageMsgId(imageEntity.getMsgId());
    imageTagDao.save(imageTag);

    XMLMessage resp = new XMLNewsMessage(
        msg.getFromUserName(),
        msg.getToUserName(),
        articles
    );
    //回复
    return resp;
  }
}
