package com.zy17.guess.famous;

import com.fasterxml.jackson.databind.type.TypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;

import weixin.popular.bean.xmlmessage.XMLNewsMessage;

@EnableCaching
@EnableAsync
@SpringBootApplication
public class Application {


  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);
    application.addListeners(new ApplicationPidFileWriter("guess.pid"));
    application.run(args);
  }

  @Bean
  RedisTemplate redisTemplate(
      @Autowired RedisTemplate template) {
    // 自定义序列化
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new Jackson2JsonRedisSerializer(TypeFactory.defaultInstance().constructCollectionType
        (ArrayList.class, XMLNewsMessage.Article.class)));
    return template;
  }

}
