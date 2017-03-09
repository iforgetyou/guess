package com.zy17.guess.famous.douban.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by zy17 on 2016/3/17.
 */
public class BaseResult implements Serializable {
  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
