package com.zy17.guess.famous.service;

import com.zy17.guess.famous.service.msghandler.AnswerMsgHandle;
import com.zy17.guess.famous.service.msghandler.DefaultMsgHandle;
import com.zy17.guess.famous.service.msghandler.FirstLoginHandle;
import com.zy17.guess.famous.service.msghandler.ImageMsgHandle;
import com.zy17.guess.famous.service.msghandler.PersistMsgHandle;
import com.zy17.guess.famous.service.msghandler.RandomImageMsgHandle;
import com.zy17.guess.famous.service.msghandler.SearchImageHandle;
import com.zy17.guess.famous.service.msghandler.TagMsgHandle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

/**
 * 2017/2/22 zy17
 */
@Slf4j
@Service
public class BizService {
  List<WeixinMsgHandle> handlers = new ArrayList<>();

  public BizService(
      @Autowired ImageMsgHandle imageMsgHandle,
      @Autowired FirstLoginHandle firstLoginHandle,
      @Autowired RandomImageMsgHandle randomImageMsgHandle,
      @Autowired AnswerMsgHandle answerMsgHandle,
      @Autowired TagMsgHandle tagMsgHandle,
      @Autowired SearchImageHandle searchImageHandle,
      @Autowired PersistMsgHandle persistMsgHandle,
      @Autowired DefaultMsgHandle defaultMsgHandle
  ) {
    handlers.add(persistMsgHandle);
    handlers.add(firstLoginHandle);
    handlers.add(imageMsgHandle);
    // 文字处理开始
    handlers.add(randomImageMsgHandle);
    handlers.add(answerMsgHandle);
    handlers.add(tagMsgHandle);
    handlers.add(searchImageHandle);
    // 文字处理结束
    handlers.add(defaultMsgHandle);
  }

  public XMLMessage handleEvent(EventMessage req) {
    XMLMessage resp = null;
    // 业务处理
    try {
      for (WeixinMsgHandle handler : handlers) {
        if (handler.canHandle(req)) {
          resp = handler.handleMsg(req);
          if (resp != null) {
            return resp;
          }
        }
      }
    } catch (Exception e) {
      log.error("business error", e);
      // 创建回复
      resp = new XMLTextMessage(
          req.getFromUserName(),
          req.getToUserName(),
          "Ծ‸Ծ 有bug？开发小哥快去修复~");
    }
    // TODO 异步保存响应?
    return resp;
  }
}
