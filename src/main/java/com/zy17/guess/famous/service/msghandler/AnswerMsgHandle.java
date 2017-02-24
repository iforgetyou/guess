package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.ImageTagRepository;
import com.zy17.guess.famous.entity.ImageTag;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.CacheService;
import com.zy17.guess.famous.service.ImageService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

/**
 * 答案指令
 * Created by zy17 on 2016/3/11.
 */
@Component
public class AnswerMsgHandle implements WeixinMsgHandle {

  @Autowired
  CacheService cache;
  @Autowired
  private ImageTagRepository imageTagDao;
  @Autowired
  ImageService imageService;

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
  public XMLMessage handleMsg(weixin.popular.bean.message.EventMessage msg) {
    // 缓存中获取上次发送的图片
    XMLMessage resp = null;
    String key = CacheService.getQuestionKey(msg.getFromUserName());
    Object imageMsgId = cache.get(key);
    if (imageMsgId != null) {
      // 找到图片对应的标签
      ImageTag imageTag = imageTagDao.findOne((String) imageMsgId);
      if (msg.getContent().equals("2")) {
        // 想知道答案
        resp = new XMLTextMessage(
            msg.getFromUserName(),
            msg.getToUserName(),
            imageTag.getTag()
        );
        // 删除旧缓存
        cache.delete(key);
      } else {
        // 猜答案
        if (msg.getContent().equals(imageTag.getTag())) {
          // 删除旧缓存
          cache.delete(key);
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
        }
      }

    }
    return resp;
  }
}
