package com.zy17.guess.famous.service;

import com.zy17.guess.famous.dao.SubjectRepository;
import com.zy17.guess.famous.entity.Subject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;

import weixin.popular.bean.xmlmessage.XMLNewsMessage;

/**
 * 生成新的题目
 * 2017/2/24 zy17
 */
@Slf4j
@Service
public class SubjectService {
  public static final String IMAGE_SERVICE_PATH = "/subject?id=";

  @Autowired
  SubjectRepository subjectRepository;
  @Autowired
  CacheService cacheService;

  @Value("${image.base.url}")
  String baseUrl;
  @Value("${image.direct}")
  boolean imageDirect;
  @Value("${image.cloud.url}")
  String imageCloudUrl;

  public ArrayList<XMLNewsMessage.Article> getSubject(String fromUserName,String topicId) throws URISyntaxException {
    String topicKeyId = CacheService.getTopicKeyId(fromUserName);
    String key = CacheService.getSubjectKey(fromUserName);

    Subject sub = subjectRepository.findOne(topicId);
    if (sub != null) {
      ArrayList<XMLNewsMessage.Article> articles = convertSubject(sub);
      // 放入缓存
      cacheService.put(key, sub.getSubjectId());
      log.info("{} find next subject:{}", fromUserName, sub.getSubjectId());
      return articles;
    } else {
      // 没有主题，清空缓存
      cacheService.delete(topicKeyId);
    }
    return null;
  }
  public ArrayList<XMLNewsMessage.Article> getNextSubject(String fromUserName) throws URISyntaxException {
    ArrayList<XMLNewsMessage.Article> articles=new ArrayList<>();
    String topicKeyId = CacheService.getTopicKeyId(fromUserName);
    String topicId = (String) cacheService.get(topicKeyId);

    Pageable pageable = new PageRequest(0, 1);
    String key = CacheService.getSubjectKey(fromUserName);
    String lastId = "";
    if (cacheService.get(key) != null) {
      lastId = (String) cacheService.pop(key);
    }
    Page<Subject> subjects = subjectRepository.findAllByTopicIdAndSubjectIdGreaterThan(topicId, lastId, pageable);
    if (subjects.getTotalElements() > 0) {
      Subject sub = subjects.getContent().get(0);
       articles = convertSubject(sub);
      // 放入缓存
      cacheService.put(key, sub.getSubjectId());
      log.info("{} find next subject:{}", fromUserName, sub.getSubjectId());
      return articles;
    } else {
      // 没有主题，清空缓存
      cacheService.delete(topicKeyId);
    }
    return articles;
  }

  public  ArrayList<XMLNewsMessage.Article> convertSubject(Subject sub){
    ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();
    XMLNewsMessage.Article subject = new XMLNewsMessage.Article();
    subject.setTitle(sub.getDescription());
    subject.setDescription(sub.getDescription());
    String avatarUrl = sub.getAvatar();
    if (avatarUrl == null) {
      avatarUrl = imageCloudUrl + "/" + sub.getSubjectId();
    }
    subject.setPicurl(avatarUrl);
    if (imageDirect) {
      // 直接访问图片地址
      subject.setUrl(sub.getCelebrityUrl());
    } else {
      // 中转图片
      subject.setUrl(baseUrl + IMAGE_SERVICE_PATH + sub.getSubjectId());
    }
    articles.add(subject);

    XMLNewsMessage.Article A = new XMLNewsMessage.Article();
    A.setTitle("A: " + sub.getAnswerA());
    articles.add(A);

    XMLNewsMessage.Article B = new XMLNewsMessage.Article();
    B.setTitle("B: " + sub.getAnswerB());
    articles.add(B);

    XMLNewsMessage.Article C = new XMLNewsMessage.Article();
    C.setTitle("C: " + sub.getAnswerC());
    articles.add(C);

    XMLNewsMessage.Article D = new XMLNewsMessage.Article();
    D.setTitle("D: " + sub.getAnswerD());
    articles.add(D);
    return articles;
  }
}
