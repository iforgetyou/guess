package com.zy17.guess.famous.service;


import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.zy17.guess.famous.SpringBootTestBase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import weixin.popular.bean.xmlmessage.XMLNewsMessage;

/**
 * 2017/2/25 yanzhang153
 */
public class DoubanServiceTest extends SpringBootTestBase {
  @Autowired
  DoubanService doubanService;

  @Test
  public void searchMovieByName() throws Exception {
    ArrayList<XMLNewsMessage.Article> famous = doubanService.searchMovieByName("胡歌");
    System.out.println(JSON.toJSONString(famous));
    assertThat(famous.size()).isGreaterThan(0);
    doubanService.searchMovieByName("胡歌");
    doubanService.searchMovieByName("胡歌");
  }

  @Test
  public void searchCelebrityTest() {
    ArrayList<XMLNewsMessage.Article> famous = doubanService.searchCelebrity("伊万·麦克格雷格");
    System.out.println(JSON.toJSONString(famous));
    assertThat(famous.size()).isGreaterThan(0);
  }

}