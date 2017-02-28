package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.AnswerRepository;
import com.zy17.guess.famous.dao.EventMessageRepository;
import com.zy17.guess.famous.dao.ImageTagRepository;
import com.zy17.guess.famous.dao.SubjectRepository;
import com.zy17.guess.famous.entity.Answer;
import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.entity.ImageTag;
import com.zy17.guess.famous.entity.Subject;
import com.zy17.guess.famous.other.CMDType;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.CacheService;
import com.zy17.guess.famous.service.DoubanService;
import com.zy17.guess.famous.service.ImageService;
import com.zy17.guess.famous.service.SubjectService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

/**
 * 选择题答案指令
 * Created by zy17 on 2016/3/11.
 */
@Slf4j
@Component
public class SubjectAnswerMsgHandle implements WeixinMsgHandle {

  @Autowired
  CacheService cache;
  @Autowired
  SubjectRepository subjectRepository;
  @Autowired
  SubjectService subjectService;
  @Autowired
  AnswerRepository answerRepository;

  private String[] wrongHit = {"没猜中,再试试吧", "下次一定能猜中", "要不回复2看下答案?"};
  private Random random = new Random();

  @Override
  public boolean canHandle(EventMessage msg) {
    if (msg.getMsgType().equals(MsgType.TEXT.getValue())) {

      String key = CacheService.getSubjectKey(msg.getFromUserName());
      Object subjectId = cache.get(key);
      if (subjectId != null) {
        // 有未完成题目
        return true;
      }

    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {
    // 缓存中获取上次发送的图片
    XMLMessage resp = null;
    if (msg.getContent().length() == 1 && "1234".contains(msg.getContent())) {
      // 必须是1234其中之一 todo
    }

    String openid = msg.getFromUserName();
    String key = CacheService.getSubjectKey(openid);
    String subjectId = (String) cache.get(key);

    Answer answer = new Answer();
    answer.setOpenId(openid);
    answer.setQuestionId(subjectId);
    answer.setAnswer(msg.getContent());

    Subject subject = subjectRepository.findOne(subjectId);

    if (msg.getContent().equals(subject.getRightAnswer())) {
      // 回答正确
      answer.setResult(true);
      // 下一道题
      ArrayList<XMLNewsMessage.Article> articles = subjectService.getNextSubject(openid);
      if (articles.size() > 0) {
        resp = new XMLNewsMessage(
            openid,
            msg.getToUserName(),
            articles);
      } else {
        // 回复此次主题的结果 todo
        long total = answerRepository.countByOpenId(openid);
        long right = answerRepository.countByOpenIdResult(openid, true);

        resp = new XMLTextMessage(
            openid,
            msg.getToUserName(),
            "恭喜您，题目全部答完~\n"
                + "答对 " + right + "/" + total + " 题"
        );
      }
    } else {
      // 回答错误
      String content = wrongHit[random.nextInt(wrongHit.length - 1)];
      resp = new XMLTextMessage(
          openid,
          msg.getToUserName(),
          content
      );
    }
    // 记录答题结果
    answerRepository.save(answer);
    return resp;
  }
}
