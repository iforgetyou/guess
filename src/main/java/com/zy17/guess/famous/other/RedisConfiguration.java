package com.zy17.guess.famous.other;

import com.fasterxml.jackson.databind.type.TypeFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;
import java.util.ArrayList;

import weixin.popular.bean.xmlmessage.XMLNewsMessage;

@Configuration
public class RedisConfiguration {

  @Bean
  @Primary
  @ConditionalOnMissingBean(name = "redisTemplate")
  public RedisTemplate<Object, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory)
      throws UnknownHostException {
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new Jackson2JsonRedisSerializer(TypeFactory.defaultInstance().constructCollectionType
        (ArrayList.class, XMLNewsMessage.Article.class)));
    return template;
  }

}