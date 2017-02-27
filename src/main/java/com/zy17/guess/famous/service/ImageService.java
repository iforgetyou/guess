package com.zy17.guess.famous.service;

import com.zy17.guess.famous.dao.EventMessageRepository;
import com.zy17.guess.famous.dao.ImageTagRepository;
import com.zy17.guess.famous.entity.ImageTag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
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
  ImageTagRepository imageTagRepository;
  @Autowired
  CacheService cache;

  public XMLMessage getRandomImage(String fromUser, String toUser) {
    long count = imageTagRepository.count();
//    long count = repository.countAllByMsgType(MsgType.IMAGE.getValue());
    if (count == 0) {
      //  还没有任何图片
      return null;
    }
    // 查看缓存，找到已经发送过的图片
    String key = CacheService.getQuestionKey(fromUser);
    Map<String, String> o = (Map<String, String>) cache.get(key);
    Collection<String> values = o.values();
    // 随机从找一张用户传图
    Pageable pageable = new PageRequest(random.nextInt((int) count), 1);
    Page<ImageTag> all = imageTagRepository.findAll(pageable);
    ImageTag imageTag = all.getContent().get(0);

    // 图片放入缓存

    cache.put(key, imageTag.getImageMsgId());

    // 创建回复
    XMLMessage resp = new XMLImageMessage(
        fromUser,
        toUser,
        imageTag.getImageMediaId()
    );
    return resp;
  }
}
