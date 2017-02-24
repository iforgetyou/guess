package com.zy17.guess.famous.entity;

import lombok.Data;

import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

import weixin.popular.bean.user.User;

/**
 * 2017/2/24 zy17
 */
@Data
@Entity
public class WeixinUser {

  public WeixinUser() {
  }

  public WeixinUser(User user) {
    BeanUtils.copyProperties(user, this);
  }

  private Integer subscribe;                //用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。

  @Id
  private String openid;                        //用户的标识，对当前公众号唯一

  private String nickname;

  private String nickname_emoji;        //昵称 表情转义

  private Integer sex;                        //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知

  private String language;

  private String city;

  private String province;

  private String country;

  private String headimgurl;

  private Integer subscribe_time;

  private String[] privilege;                //sns 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）

  private String unionid;                        //多个公众号之间用户帐号互通UnionID机制

  private Integer groupid;

  private String remark;                        //公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注

  private Integer[] tagid_list;        //用户被打上的标签ID列表
}
