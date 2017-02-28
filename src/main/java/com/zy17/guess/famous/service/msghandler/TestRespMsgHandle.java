package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.EventMessageRepository;
import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;

/**
 * 优先级最高，直接测试微信返回值效果
 * Created by zy17 on 2016/3/11.
 */
@Component
public class TestRespMsgHandle implements WeixinMsgHandle {

  @Override
  public boolean canHandle(weixin.popular.bean.message.EventMessage msg) {
    return true;
  }

  @Override
  public XMLMessage handleMsg(weixin.popular.bean.message.EventMessage msg) {
    // 创建图文消息,提示用户图片对应的标签?
    ArrayList<XMLNewsMessage.Article> articles = new ArrayList<>();
    // 添加问题
    XMLNewsMessage.Article image = new XMLNewsMessage.Article();
    image.setTitle("who?");
    image.setPicurl("http://lh3.googleusercontent.com/FhRB02XgMY1W2EgQOLwggWgFZEI-yuwZD6EKIAkMvqKXJlsCmZuaQXP0GrLES1X7gPQYD48TpERIu3cyiFWHZTnHeC4");
    image.setUrl("http://lh3.googleusercontent.com/FhRB02XgMY1W2EgQOLwggWgFZEI-yuwZD6EKIAkMvqKXJlsCmZuaQXP0GrLES1X7gPQYD48TpERIu3cyiFWHZTnHeC4");
    image.setDescription("tips");
    articles.add(image);

    // 添加选项
    for (int i = 0; i < 4; i++) {
      XMLNewsMessage.Article answer = new XMLNewsMessage.Article();
      answer.setTitle("answer_" + (i + 1));
      articles.add(answer);
    }

    XMLMessage resp = new XMLNewsMessage(
        msg.getFromUserName(),
        msg.getToUserName(),
        articles
    );

    return resp;
  }
}
