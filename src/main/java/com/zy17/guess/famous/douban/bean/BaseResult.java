package com.zy17.guess.famous.douban.bean;

import com.alibaba.fastjson.JSON;

/**
 * Created by zhangyan53 on 2016/3/17.
 */
public class BaseResult {
  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
