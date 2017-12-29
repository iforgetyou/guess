package com.zy17.guess.famous.service.msghandler;

import com.zy17.guess.famous.dao.WeixinUserRepository;
import com.zy17.guess.famous.entity.WeixinUser;
import com.zy17.guess.famous.other.EventType;
import com.zy17.guess.famous.other.MsgType;
import com.zy17.guess.famous.service.WeixinMsgHandle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import weixin.popular.api.UserAPI;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.user.User;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.TokenManager;

/**
 * 首次关注处理
 * Created by zy17
 */
@Slf4j
@Component
public class FirstLoginHandle implements WeixinMsgHandle {
  @Autowired
  WeixinUserRepository dao;
  @Value("${weixin.command.cache.expire}")
  public static int cacheTimeInMinutes;

  public  static String HELP = ""
//      + "  猜图 -> 输入 \"0\"\n"
//      + "  答案 -> 输入 \"1234代表不同选项，"+"10分钟内有效哦\"\n"
//      + "  清除历史成绩 -> 回复\"3\"\"\n"
//      + "  搜图 -> 输入标签即可\n"
//      + "  传图 -> 传图片后加标签\n\n"
      + "欢迎关注猜名人公众号"
      + "通过菜单  选主题->猜名人 "
      + "发掘你最喜欢的名人~\n"
      + "如果发现违法图片,请随时通知我们\n"
      + "想了解更多名人也可以反馈给我们";

  @Override
  public boolean canHandle(EventMessage msg) {
    if (MsgType.isEvent(msg.getMsgType()) && msg.getEvent().equals(EventType.SUBSCRIBE.getValue())) {
      return true;
    }
    return false;
  }

  @Override
  public XMLMessage handleMsg(EventMessage msg) {

    String newUserId = msg.getFromUserName();
    // 获取用户基本信息,暂时未获取权限
    try {
      User user = UserAPI.userInfo(TokenManager.getDefaultToken(), newUserId);
      if (user.isSuccess()) {
        // 存数据库
        WeixinUser entity = new WeixinUser(user);
        dao.save(entity);
      }
    }catch (Exception e){
      log.error("新用户入库错误",e);
    }


    // 欢迎语和帮助信息
    XMLMessage resp = new XMLTextMessage(
        msg.getFromUserName(),
        msg.getToUserName(),
        "感谢关注\n" + HELP
    );
    return resp;
  }
}
