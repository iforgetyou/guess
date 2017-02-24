package com.zy17.guess.famous.other;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import weixin.popular.support.TokenManager;

@Component
public class TokenManagerListener implements ServletContextListener {
  @Value("${weixin.appid}")
  private String appid;
  @Value("${weixin.secret}")
  private String secret;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    //WEB容器 初始化时调用
    TokenManager.setDaemon(false);
    TokenManager.init(appid, secret);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    //WEB容器  关闭时调用
    TokenManager.destroyed();
  }
}