package com.zy17.guess.famous.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 缓存服务
 * 2017/2/23 zy17
 */
@Slf4j
@Service
public class CacheService {
  public static int cacheTimeInMinutes = 2;
  LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
      .maximumSize(1000)
      .expireAfterWrite(cacheTimeInMinutes, TimeUnit.MINUTES)
      .build(
          new CacheLoader<String, Object>() {
            public Object load(String key) throws Exception {
              // 没有返回空
              throw new Exception("not found key:" + key);
            }
          });

  public void put(String key, Object value) {
    log.info("put cache key:{},value:{}", key, value);
    this.cache.put(key, value);
  }

  public Object get(String key) {
    Object result = null;
    try {
      result = cache.get(key);
    } catch (ExecutionException e) {
      log.debug("cache_get_error:" + e.getMessage());
    }
    return result;
  }

  public Object pop(String key) {
    Object result = null;
    try {
      result = cache.get(key);
      this.delete(key);
    } catch (ExecutionException e) {
      log.debug("cache_get_error:" + e.getMessage());
    }
    return result;
  }

  public void delete(String key) {
    cache.invalidate(key);
  }

  public static String getNewImageKey(String username) {
    return username + "_tag";
  }

  /**
   * 已经发送随机图片
   * key username_Q
   * value list :imageMsgId
   * @param username
   * @return
   */
  public static String getQuestionKey(String username) {
    return username + "_Q";
  }

  public static String getNoImageKey(String username) {
    return username + "_no_image";
  }

  // value map<选项，topicId>
  public static String getTopicKey(String username) {
    return username + "_tpc";
  }
  public static String getTopicKeyId(String username) {
    return username + "_tpcId";
  }

  public static String getSubjectKey(String username) {
    return username + "_sub";
  }
}
