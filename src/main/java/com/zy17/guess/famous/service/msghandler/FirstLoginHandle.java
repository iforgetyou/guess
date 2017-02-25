package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.WeixinUserRepository;
import com.zy17.guess.famous.other.EventType;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

/**
 * 首次关注处理
 * Created by zy17
 */
@Component
public class FirstLoginHandle implements WeixinMsgHandle {
  @Autowired
  WeixinUserRepository dao;

  public static final String HELP = ""
      + "  猜图 -> 输入 \"1\"\n"
      + "  答案 -> 输入 \"2\"\n"
//      + "  清除历史成绩 -> 回复\"3\"\"\n"
      + "  搜图 -> 输入标签即可\n"
      + "  传图 -> 传图片后加标签\n\n"
      + "  会定期审核图片，发现违法图片立即删除";

  @Override
  public boolean canHandle(EventMessage msg) {
    if (MsgType.isEvent(msg.getMsgType()) && msg.getEvent().equals(EventType.SUBSCRIBE.getValue())) {
      return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {

//    String newUserId = msg.getFromUserName();
    // 获取用户基本信息,暂时未获取权限
//    User user = UserAPI.userInfo(TokenManager.getDefaultToken(), newUserId);
//    if (user.isSuccess()) {
//       存数据库
//      WeixinUser entity = new WeixinUser(user);
//      dao.save(entity);
//    }
    // 欢迎语和帮助信息
    XMLMessage resp = new XMLTextMessage(
        msg.getFromUserName(),
        msg.getToUserName(),
        "感谢关注\n" + HELP
    );
    return resp;
  }
}
