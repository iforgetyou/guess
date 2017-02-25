package com.zy17.guess.famous.service;

import com.zy17.guess.famous.dao.EventMessageRepository;
import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.other.MsgType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Random;

import weixin.popular.bean.xmlmessage.XMLImageMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;

/**
 * 随机找图片
 * 2017/2/24 zy17
 */
@Service
public class ImageService {
  private Random random = new Random();
  @Autowired
  EventMessageRepository repository;
  @Autowired
  CacheService cache;

  public XMLMessage getRandomImage(String fromUser, String toUser) {
    long count = repository.countAllByMsgType(MsgType.IMAGE.getValue());
    if (count == 0) {
      //  还没有任何图片
      return null;
    }
    // 随机从找一张用户传图
    Pageable pageable = new PageRequest(random.nextInt((int) count), 1);
    Page<EventMessageEntity> all = repository.findAllByMsgType(MsgType.IMAGE.getValue(), pageable);
    EventMessageEntity eventMessageEntity = all.getContent().get(0);
    // 图片放入缓存
    String key = CacheService.getQuestionKey(fromUser);
    cache.put(key, eventMessageEntity.getMsgId());

    // 创建回复
    XMLMessage resp = new XMLImageMessage(
        fromUser,
        toUser,
        eventMessageEntity.getMediaId()
    );
    return resp;
  }
}
