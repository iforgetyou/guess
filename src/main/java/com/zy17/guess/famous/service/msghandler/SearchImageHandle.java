package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.ImageTagRepository;
import com.zy17.guess.famous.entity.ImageTag;
import com.zy17.guess.famous.other.CMDType;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.CacheService;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Random;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLImageMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

/**
 * 搜索图片
 * Created by zy17 on 2016/3/11.
 */
@Component
public class SearchImageHandle implements WeixinMsgHandle {
  public static final String content = "居然还找到，来上传张吧";
  @Autowired
  CacheService cache;
  @Autowired
  ImageTagRepository imageTagDao;

  @Override
  public boolean canHandle(weixin.popular.bean.message.EventMessage msg) {
    if (msg.getMsgType().equals(MsgType.TEXT.getValue()) && CMDType.notCmd(msg.getContent())) {
      if (cache.get(CacheService.getNewImageKey(msg.getFromUserName())) == null)
        return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {
    XMLMessage resp = null;
    long count = imageTagDao.countByTag(msg.getContent());
    if (count > 0) {
      int page = new Random().nextInt((int) count);
      Page<ImageTag> imageTags = imageTagDao.findAllByTag(msg.getContent(), new PageRequest(page, 1));
      resp = new XMLImageMessage(
          msg.getFromUserName(),
          msg.getToUserName(),
          imageTags.getContent().get(0).getImageMediaId()
      );
    } else {

      resp = new XMLTextMessage(
          msg.getFromUserName(),
          msg.getToUserName(),
          content
      );
    }

    //回复
    return resp;
  }
}
