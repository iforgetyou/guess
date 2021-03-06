package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.EventMessageRepository;
import com.zy17.guess.famous.dao.ImageTagRepository;
import com.zy17.guess.famous.dao.AnswerRepository;
import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.entity.ImageTag;
import com.zy17.guess.famous.entity.Answer;
import com.zy17.guess.famous.other.CMDType;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.CacheService;
import com.zy17.guess.famous.service.DoubanService;
import com.zy17.guess.famous.service.ImageService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

/**
 * 答案指令
 * Created by zy17 on 2016/3/11.
 */
@Slf4j
@Component
public class AnswerMsgHandle implements WeixinMsgHandle {

  @Autowired
  CacheService cache;
  @Autowired
  private ImageTagRepository imageTagDao;
  @Autowired
  ImageService imageService;
  @Autowired
  DoubanService doubanService;
  @Autowired
  EventMessageRepository dao;
  @Autowired
  AnswerRepository answerRepository;


  private String[] wrongHit = {"没猜中,再试试吧", "下次一定能猜中", "要不回复2看下答案?"};
  private Random random = new Random();

  @Override

  public boolean canHandle(weixin.popular.bean.message.EventMessage msg) {
    if (msg.getMsgType().equals(MsgType.TEXT.getValue())) {
      String key = CacheService.getQuestionKey(msg.getFromUserName());
      Object imageMsgId = cache.get(key);
      if (imageMsgId != null) {
        return true;
      }
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {
    // 缓存中获取上次发送的图片
    XMLMessage resp = null;
    String key = CacheService.getQuestionKey(msg.getFromUserName());
    Object imageMsgId = cache.get(key);
    if (imageMsgId != null) {

      // 找到图片对应的标签
      EventMessageEntity imageMsg = dao.findOne((String) imageMsgId);
      ImageTag imageTag = imageTagDao.findOne((String) imageMsgId);

      // 需要保存用户答案信息
      Answer userAnswer = new Answer();
      userAnswer.setAnswer(msg.getContent());
      userAnswer.setQuestionId(imageTag.getImageMsgId());
      userAnswer.setOpenId(msg.getFromUserName());

      if (msg.getContent().equals(CMDType.ANSWER.getValue())) {
        // 想知道答案
        ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();
        XMLNewsMessage.Article answer = new XMLNewsMessage.Article();
        answer.setTitle(imageTag.getTag());
        answer.setPicurl(imageMsg.getMsg().getPicUrl());
        articles.add(answer);

        // 加入豆瓣结果
        articles.addAll(doubanService.searchMovieByName(imageTag.getTag()));

        resp = new XMLNewsMessage(
            msg.getFromUserName(),
            msg.getToUserName(),
            articles
        );
        // 删除旧缓存
        cache.delete(key);
        userAnswer.setResult(false);
      } else {
        // 猜答案
        if (msg.getContent().equals(imageTag.getTag())) {
          // 删除旧缓存
          cache.delete(key);
          userAnswer.setResult(true);
          // 正确,来张新图
          resp = imageService.getRandomImage(msg.getFromUserName(), msg.getToUserName());
        } else {
          String content = wrongHit[random.nextInt(wrongHit.length - 1)];
          // 错误
          resp = new XMLTextMessage(
              msg.getFromUserName(),
              msg.getToUserName(),
              content
          );
          userAnswer.setResult(false);
        }
      }

    }
    return resp;
  }
}
