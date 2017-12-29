package com.zy17.guess.famous.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
  @Value("${weixin.command.cache.expire}")
  public int cacheTimeInMinutes;
  /**
   * Redis
   */
  @Autowired
  private RedisTemplate<Object, Object>  redisTemplate;

//  LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
//      .maximumSize(1000)
//      .expireAfterWrite(cacheTimeInMinutes, TimeUnit.MINUTES)
//      .build(
//          new CacheLoader<String, Object>() {
//            public Object load(String key) throws Exception {
//              // 没有返回空
//              throw new Exception("not found key:" + key);
//            }
//          });

  public void put(String key, Object value) {
    log.debug("put cache key:{},value:{}", key, value);
    redisTemplate.opsForValue().set(key,value,cacheTimeInMinutes, TimeUnit.MINUTES);
  }

  public Object get(String key) {
    Object result = null;
      result= redisTemplate.opsForValue().get(key);
      log.debug("key:{},cache_get_result:{}",key,result);
    return result;
  }

  public Object pop(String key) {
    Object result = get(key);
    redisTemplate.delete(key);
    return result;
  }

  public void delete(String key) {
    redisTemplate.delete(key);
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
