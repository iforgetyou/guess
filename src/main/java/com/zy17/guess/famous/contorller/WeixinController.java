package com.zy17.guess.famous.contorller;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.zy17.guess.famous.service.BizService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

/**
 * 服务端事件消息接收
 * 2017/2/21 zy17
 */
@Slf4j
@Controller
@RequestMapping("/weixin")
public class WeixinController {

  //从官方获取
  @Value("${weixin.token}")
  private String token;
  @Value("${weixin.appid}")
  private String appId;
  @Value("${weixin.EncodingAESKey}")
  private String encodingAESKey;
@Value("${weixin.checktoken}")
boolean checkToken;
  @Autowired
  BizService bizService;

  //重复通知过滤
  private static ExpireKey expireKey = new DefaultExpireKey();


  // 微信业务逻辑处理
  @ResponseStatus(value = HttpStatus.OK)
  @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/xml; charset=UTF-8")
  public
  @ResponseBody
  String business(@RequestParam String signature,
      @RequestParam String timestamp,
      @RequestParam String nonce,
      @RequestParam(required = false) String echostr,
      @RequestParam(required = false) String encrypt_type,
      @RequestParam(required = false) String msg_signature,
      @RequestBody(required = false) EventMessage event) throws UnsupportedEncodingException {
    //验证请求签名
    if (checkToken&&!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
      log.warn("The request signature is invalid");
      return "signature check failed";
    }

    WXBizMsgCrypt wxBizMsgCrypt = null;
    //加密方式
    boolean isAes = "aes".equals(encrypt_type);
    if (isAes) {
      try {
        wxBizMsgCrypt = new WXBizMsgCrypt(token, encodingAESKey, appId);
      } catch (AesException e) {
        log.warn("", e);
      }
    }

    //首次请求申请验证,返回echostr
    if (isAes && echostr != null) {
      try {
        // 加密模式
        echostr = URLDecoder.decode(echostr, "utf-8");
//        wxBizMsgCrypt.decryptMsg();
        return wxBizMsgCrypt.verifyUrl(msg_signature, timestamp, nonce, echostr);
      } catch (AesException e) {
        log.warn("", e);
      }
    } else if (echostr != null) {
      // 非加密模式
      return echostr;
    }

    log.info("request:?signature={}&timestamp={}&nonce={},event={}", signature, timestamp, nonce, XMLConverUtil
        .convertToXML(event));

    String key = event.getFromUserName() + "__"
        + event.getToUserName() + "__"
        + event.getMsgId() + "__"
        + event.getCreateTime();
    if (expireKey.exists(key)) {
      //重复通知不作处理
      log.debug("重复通知不作处理");
      return "重复通知不作处理";
    } else {
      expireKey.add(key);
    }

    XMLMessage resp = bizService.handleEvent(event);
    if( resp!=null){
      log.info("{} resp:{}", event.getMsgId(), resp.toXML());
      return resp.toXML();
    }else{
      return "";
    }
  }


}
