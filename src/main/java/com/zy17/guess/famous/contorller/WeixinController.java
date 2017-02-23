package com.zy17.guess.famous.contorller;

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

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

/**
 * 服务端事件消息接收
 * 2017/2/21 iforgetyou
 */
@Slf4j
@Controller
@RequestMapping("/weixin")
public class WeixinController {

  //从官方获取
  @Value("${weixin.token}")
  private String token;
  @Autowired
  BizService bizService;

  //重复通知过滤
  private static ExpireKey expireKey = new DefaultExpireKey();

  @RequestMapping(value = "", method = RequestMethod.GET)
  public
  @ResponseBody
  String echo(@RequestParam(value = "signature") String signature,
      @RequestParam(value = "timestamp") String timestamp,
      @RequestParam(value = "nonce") String nonce,
      @RequestParam(value = "echostr") String echostr) {
    log.debug("request:signature={},timestamp={},nonce={},echostr={}", signature, timestamp, nonce, echostr);
    //验证请求签名
    if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
      log.warn("The request signature is invalid");
      return "signature check failed";
    }

    //首次请求申请验证,返回 echostr
    if (echostr != null) {
      return echostr;
    }


    return echostr;
  }

  // 微信业务逻辑处理
  @ResponseStatus(value = HttpStatus.OK)
  @RequestMapping(method = RequestMethod.POST, produces = "application/xml; charset=UTF-8")
  public
  @ResponseBody
  String business(@RequestParam(value = "signature", required = false) String signature,
      @RequestParam(value = "timestamp", required = false) String timestamp,
      @RequestParam(value = "nonce", required = false) String nonce,
      @RequestBody(required = false) EventMessage event) throws UnsupportedEncodingException {
    log.info("request:signature={},timestamp={},nonce={},event={}", signature, timestamp, nonce, XMLConverUtil
        .convertToXML(event));
    //验证请求签名
    if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
      log.warn("The request signature is invalid");
      return "signature check failed";
    }

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
    log.info("resp:{}", resp.toXML());
    return resp.toXML();
  }
}
