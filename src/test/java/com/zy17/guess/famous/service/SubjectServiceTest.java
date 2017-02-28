package com.zy17.guess.famous.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import com.alibaba.fastjson.JSON;
import com.zy17.guess.famous.SpringBootTestBase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import weixin.popular.bean.xmlmessage.XMLNewsMessage;

/**
 * 2017/2/28 yanzhang153
 */
public class SubjectServiceTest extends SpringBootTestBase {
  @Autowired
  SubjectService subjectService;

  @Test
  public void getNextSubject() throws Exception {
    ArrayList<XMLNewsMessage.Article> result1 = subjectService.getNextSubject("hello");
    System.out.println(JSON.toJSONString(result1));
    ArrayList<XMLNewsMessage.Article> result2 = subjectService.getNextSubject("hello");
    assertThat(result2.size()).isEqualTo(0);
  }

}