package com.zy17.guess.famous.contorller;

import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.zy17.guess.famous.EventGen;
import com.zy17.guess.famous.SpringBootTestBase;
import com.zy17.guess.famous.dao.TopicRepository;
import com.zy17.guess.famous.entity.Topic;
import com.zy17.guess.famous.service.CacheService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class TopicTest extends SpringBootTestBase {

  @Value("${weixin.token}")
  private String token;

  @Autowired
  CacheService cache;

  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private TopicRepository topicRepository;

  @Test
  public void addTopicTest() {
    Topic topic = new Topic();
    topic.setTopicName("topic");
    topic.setCoverImageUrl("http://url");
    topic.setDescription("测试主题");
    topic.setDetailUrl("http://detail.url");
    System.out.println("testtopic:" + JSON.toJSONString(topic));

    Topic save = topicRepository.save(topic);
    assertThat(save.getTopicId()).isNotNull();

    String s = restTemplate.postForObject(getUrl(), EventGen.getTextEvent("0"), String.class);
    System.out.println("result:" + s);
    String s1 = restTemplate.postForObject(getUrl(), EventGen.getTextEvent("1"), String.class);
    System.out.println("result:" + s1);
  }

  @Test
  public void findTopicTest() {

  }


}
