package com.zy17.guess.famous.service;

import com.zy17.guess.famous.service.msghandler.DefaultMsgHandle;
import com.zy17.guess.famous.service.msghandler.ImageMsgHandle;
import com.zy17.guess.famous.service.msghandler.PersistMsgHandle;
import com.zy17.guess.famous.service.msghandler.RandomImageMsgHandle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

/**
 * 2017/2/22 iforgetyou
 */
@Slf4j
@Service
public class BizService {
  List<WeixinMsgHandle> handlers = new ArrayList<>();

  public BizService(@Autowired DefaultMsgHandle defaultMsgHandle,
      @Autowired ImageMsgHandle imageMsgHandle,
      @Autowired RandomImageMsgHandle randomImageMsgHandle,
      @Autowired PersistMsgHandle persistMsgHandle
  ) {

    handlers.add(persistMsgHandle);
    handlers.add(imageMsgHandle);
    handlers.add(randomImageMsgHandle);
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
          "Ծ‸Ծ 有bug？");
    }
    return resp;
  }
}
