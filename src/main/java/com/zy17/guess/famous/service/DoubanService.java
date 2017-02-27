package com.zy17.guess.famous.service;

import com.alibaba.fastjson.JSON;
import com.zy17.guess.famous.douban.api.movie.MovieApi;
import com.zy17.guess.famous.douban.bean.MovieSearchResult;
import com.zy17.guess.famous.douban.bean.SimpleSubject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import weixin.popular.bean.xmlmessage.XMLNewsMessage;

/**
 * 豆瓣相关api
 * 2017/2/25 zy17
 */
@Slf4j
@Service
public class DoubanService {
  @Autowired
  MovieApi movieApi;

  /**
   * 豆瓣影人作品搜索
   * @param name
   * @return
   */
  @Async
  private Future<ArrayList<XMLNewsMessage.Article>> searchMovieByName(String name) {
    ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();
    // 默认从豆瓣找3个结果
    MovieSearchResult movieSearchResult = movieApi.searchMovie(name, "", 0, 3);
    log.debug("searchMovie result:{}", JSON.toJSONString(movieSearchResult));

    // 豆瓣结果转化
    for (SimpleSubject simpleSubject : movieSearchResult.getSubjects()) {
      XMLNewsMessage.Article doubanDetail = new XMLNewsMessage.Article();
      doubanDetail.setTitle(simpleSubject.getTitle());
      doubanDetail.setPicurl(simpleSubject.getImages().get(SimpleSubject.IMAGE_TYPE_LARGE));
      doubanDetail.setUrl(simpleSubject.getAlt());
      articles.add(doubanDetail);
    }

    return new AsyncResult<>(articles);
  }

  @Cacheable(value = "searchMovieByNameFromCache")
  public ArrayList<XMLNewsMessage.Article> searchMovieByNameFromCache(String key) {
    ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();
    try {
      articles.addAll(searchMovieByName(key).get(2, TimeUnit.SECONDS));
    } catch (Exception e) {
      log.warn("searchMovieByNameFromCache:" + e.getMessage());
    }
    return articles;
  }


}
