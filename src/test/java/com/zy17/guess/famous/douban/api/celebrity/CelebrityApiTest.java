package com.zy17.guess.famous.douban.api.celebrity;

import static org.junit.Assert.*;

import com.alibaba.fastjson.JSON;
import com.zy17.guess.famous.SpringBootTestBase;
import com.zy17.guess.famous.douban.bean.CelebrityResult;
import com.zy17.guess.famous.douban.bean.SubjectSuggestResult;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;

/**
 * 2017/2/27 yanzhang153
 */
public class CelebrityApiTest extends SpringBootTestBase {
  @Autowired
  CelebrityApi api;

  @Test
  public void findCelebrityById() throws Exception {

  }

  @Test
  public void findCelebrityByName() throws Exception {
    SubjectSuggestResult[] results = api.findCelebrityByName("莱昂纳多");
    System.out.println(JSON.toJSONString(results));
    for (SubjectSuggestResult result : results) {
      if (result.getType().equals("celebrity")) {
        URI uri = new URI(result.getUrl());
        String path = uri.getPath();
        String[] split = path.split("/");
        System.out.println(split[split.length-1]);
//        api.findCelebrityById(uri.getQuery())
      }
    }
  }

}