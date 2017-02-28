package com.zy17.guess.famous.service;

import com.zy17.guess.famous.dao.SubjectRepository;
import com.zy17.guess.famous.entity.Subject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import weixin.popular.bean.xmlmessage.XMLNewsMessage;

/**
 * 生成新的题目
 * 2017/2/24 zy17
 */
@Slf4j
@Service
public class SubjectService {
  @Autowired
  SubjectRepository subjectRepository;
  @Autowired
  CacheService cacheService;

  public ArrayList<XMLNewsMessage.Article> getNextSubject(String fromUserName) {
    ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();

    Pageable pageable = new PageRequest(0, 1);
    String key = CacheService.getSubjectKey(fromUserName);
    long lastId = 0;
    if (cacheService.get(key) != null) {
      lastId = (long) cacheService.pop(key);
    }
    Page<Subject> subjects = subjectRepository.findAllBySubjectIdGreaterThan(lastId, pageable);
    if (subjects.getTotalElements() > 0) {

      Subject sub = subjects.getContent().get(0);
      XMLNewsMessage.Article subject = new XMLNewsMessage.Article();
      subject.setTitle(sub.getDescription());
      subject.setDescription(sub.getDescription());
      subject.setPicurl(sub.getAvatar());
      subject.setUrl(sub.getCelebrityUrl());
      articles.add(subject);

      XMLNewsMessage.Article A = new XMLNewsMessage.Article();
      A.setTitle("回复1. " + sub.getAnswerA());
      articles.add(A);

      XMLNewsMessage.Article B = new XMLNewsMessage.Article();
      B.setTitle("回复2. " + sub.getAnswerB());
      articles.add(B);

      XMLNewsMessage.Article C = new XMLNewsMessage.Article();
      C.setTitle("回复3. " + sub.getAnswerC());
      articles.add(C);

      XMLNewsMessage.Article D = new XMLNewsMessage.Article();
      D.setTitle("回复4. " + sub.getAnswerD());
      articles.add(D);

      // 放入缓存
      cacheService.put(key, sub.getSubjectId());
      log.info("{} find next subject:{}", fromUserName, sub.getSubjectId());
    }
    return articles;
  }
}
