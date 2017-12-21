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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

import javax.xml.stream.util.EventReaderDelegate;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLImageMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

/**
 * 搜索图片
 * Created by zy17 on 2016/3/11.
 */
@Component
public class SearchImageHandle implements WeixinMsgHandle {
  public static final String content = "居然还找到，来上传张吧";
  @Autowired
  CacheService cache;
  @Autowired
  ImageTagRepository imageTagDao;
  @Autowired
  DoubanService doubanService;
  @Autowired
  EventMessageRepository dao;

  @Override
  public boolean canHandle(weixin.popular.bean.message.EventMessage msg) {
    if (msg.getMsgType().equals(MsgType.TEXT.getValue()) && CMDType.notCmd(msg.getContent())) {
      if (cache.get(CacheService.getNewImageKey(msg.getFromUserName())) == null)
        return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {
    XMLMessage resp = null;
    long count = imageTagDao.countByTag(msg.getContent());
    String userName = msg.getFromUserName();
    if (count > 0) {
      // 随机找到带有标签的图片，todo 处理过期的
      int page = new Random().nextInt((int) count);
      Page<ImageTag> imageTags = imageTagDao.findAllByTag(msg.getContent(), new PageRequest(page, 1));
      ImageTag imageTag = imageTags.getContent().get(0);

      // 找到原始图片信息
      EventMessageEntity imageMsg = dao.findOne(imageTag.getImageMsgId());
      ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();
      XMLNewsMessage.Article detail = new XMLNewsMessage.Article();
      detail.setTitle(imageTag.getTag());
      detail.setPicurl(imageMsg.getMsg().getPicUrl());
      articles.add(detail);

      // 加入豆瓣结果
      articles.addAll(doubanService.searchMovieByName(imageTag.getTag()));

      resp = new XMLNewsMessage(
          userName,
          msg.getToUserName(),
          articles
      );
    } else {
      resp = new XMLTextMessage(
          userName,
          msg.getToUserName(),
          content
      );
      // 提前把标签放入缓存，等下个图片添加后试用
      String key = CacheService.getNoImageKey(userName);
      cache.put(key, msg.getMsgId());
    }

    //回复
    return resp;
  }
}
