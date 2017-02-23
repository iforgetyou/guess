package com.zy17.guess.famous.entity;

import lombok.Data;

import org.springframework.beans.BeanUtils;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import weixin.popular.bean.message.AroundBeacon;
import weixin.popular.bean.message.ChosenBeacon;
import weixin.popular.bean.message.EventMessage;

@Data
@Entity
public class EventMessageEntity   {
  public EventMessageEntity() {
  }

  public EventMessageEntity(EventMessage msg) {
    BeanUtils.copyProperties(msg, this);
  }


  //base
  private String toUserName;                //开发者微信号

  private String fromUserName;        //发送方帐号（一个OpenID）

  private Integer createTime;                //消息创建时间 （整型）

  private String msgType;                        //消息类型，event

  private String event;                        //事件类型，subscribe(订阅)、unsubscribe(取消订阅)

  //----扫描带参数二维码事件,自定义菜单key
  private String eventKey;                //事件KEY值，qrscene_为前缀，后面为二维码的参数值


  //接收普通消息------------------------------------ START
  @Id
  private String msgId;                        //消息ID号
  //文本
  private String content;                        //文本消息内容
  //图片
  private String picUrl;                        //图片消息
  //媒体
  private String mediaId;                        //mediaId 可以调用多媒体文件下载接口拉取数据
  //语音格式
  private String format;                        //语音格式
  //语音识别
  private String recognition;                //开通语音识别功能的识别结果
  //视频
  private String thumbMediaId;        //视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。

  //地理位置-地理位置维度
  private String location_X;

  //地理位置-地理位置经度
  private String location_Y;

  //地理位置-地图缩放大小
  private String scale;

  //地理位置-地理位置信息
  private String label;

  //链接
  private String title;
  private String description;
  private String url;
  //接收普通消息------------------------------------ END


  //接收事件推送------------------------------------ START

  //关注/取消关注事件

  //二维码的ticket，可用来换取二维码图片
  private String ticket;

  //----上报地理位置事件
  private String latitude;                //地理位置纬度

  private String longitude;                //地理位置经度

//  private String precision;                //地理位置精度
  //接收事件推送------------------------------------ END


  //群发消息通知------------------------------------ START
  //Event	 事件信息，此处为MASSSENDJOBFINISH
  private String status;
  /**
   * 群发的结构，为“send success”或“send fail”或“err(num)”。
   * 但send success时，也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。err(num)是审核失败的具体原因，可能的情况如下：
   * err(10001), 涉嫌广告
   * err(20001), 涉嫌政治
   * err(20004), 涉嫌社会
   * err(20002), 涉嫌色情
   * err(20006), 涉嫌违法犯罪
   * err(20008), 涉嫌欺诈
   * err(20013), 涉嫌版权
   * err(22000), 涉嫌互推(互相宣传)
   * err(21000), 涉嫌其他
   */

  private Integer totalCount;        //group_id下粉丝数；或者openid_list中的粉丝数

  private Integer filterCount;//过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount = SentCount + ErrorCount

  private Integer sentCount;//发送成功的粉丝数

  private Integer errorCount;//发送失败的粉丝数

  //群发消息通知------------------------------------ END


  //微信认证事件推送-------------------------------- START

  private Integer expiredTime;   //有效期 (整形)，指的是时间戳

  private Integer failTime;   //失败发生时间 (整形)，时间戳

  private String failReason;   //认证失败的原因

  //微信认证事件推送-------------------------------- END


  //微信门店审核事件推送-------------------------------- START

  private String uniqId;   //商户自己内部ID，即字段中的sid

  private String poiId;   //微信的门店ID，微信内门店唯一标示ID

  private String result;   //审核结果，成功succ 或失败fail

  private String msg;   //成功的通知信息，或审核失败的驳回理由

  //微信门店审核事件推送-------------------------------- END


  //摇一摇周边事件通知-------------------------------- START
//  private ChosenBeacon chosenBeacon;   //审核结果，成功succ 或失败fail

//  private List<AroundBeacon> aroundBeacons;   //审核结果，成功succ 或失败fail

  //摇一摇 周边事件通知-------------------------------- END


  //摇一摇 红包绑定用户事件通知-------------------------------- START
  private String lotteryId;

  private Integer money;

  private Integer bindTime;

  //摇一摇 红包绑定用户事件通知-------------------------------- END


  //WIFI连网后下发消息-------------------------------- START
  private Integer connectTime;                //连网时间

  private Integer expireTime;                        //系统保留字段，固定值

  private String vendorId;                        //系统保留字段，固定值

  private String shopId;                                //门店ID，即shop_id

  private String deviceNo;                        //连网的设备无线mac地址，对应bssid

  //WIFI连网后下发消息-------------------------------- END


  //扫一扫事件推送-------------------------------- START
  private String keyStandard;                //商品编码标准

  private String keyStr;                        //商品编码内容

  private String country;                        //用户在微信内设置的国家

  private String province;                //用户在微信内设置的省份

  private String city;                        //用户在微信内设置的城市

  private Integer sex;                        //用户的性别，1为男性，2为女性，0代表未知

  private Integer scene;                        //打开商品主页的场景，1为扫码，2为其他打开场景（如会话、收藏或朋友圈）

  private String regionCode;                //用户的实时地理位置信息（目前只精确到省一级），可在国家统计局网站查到对应明细：http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201504/t20150415_712722.html

  private Integer reasonMsg;                //审核未通过的原因。

  //扫一扫事件推送-------------------------------- END

}