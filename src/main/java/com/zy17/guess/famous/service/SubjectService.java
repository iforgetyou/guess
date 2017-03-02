package com.zy17.guess.famous.service;

import com.zy17.guess.famous.dao.SubjectRepository;
import com.zy17.guess.famous.entity.Subject;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
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
  public static final String IMAGE_SERVICE_PATH = "/image";

  @Autowired
  SubjectRepository subjectRepository;
  @Autowired
  CacheService cacheService;

  @Value("${image.base.url}")
  String baseUrl;


  public ArrayList<XMLNewsMessage.Article> getNextSubject(String fromUserName) throws URISyntaxException {
    ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();

    Pageable pageable = new PageRequest(0, 1);
    String key = CacheService.getSubjectKey(fromUserName);
    String lastId = "";
    if (cacheService.get(key) != null) {
      lastId = (String) cacheService.pop(key);
    }
    Page<Subject> subjects = subjectRepository.findAllBySubjectIdGreaterThan(lastId, pageable);
    if (subjects.getTotalElements() > 0) {

      Subject sub = subjects.getContent().get(0);
      XMLNewsMessage.Article subject = new XMLNewsMessage.Article();
      subject.setTitle(sub.getDescription());
      subject.setDescription(sub.getDescription());
      subject.setPicurl(sub.getAvatar());
      URIBuilder uri = new URIBuilder(baseUrl + IMAGE_SERVICE_PATH);
      uri.addParameter("id", sub.getSubjectId());
      subject.setUrl(uri.build().toString());
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
