package com.zy17.guess.famous.contorller;

import static org.assertj.core.api.Assertions.assertThat;

import com.zy17.guess.famous.other.EventType;
import com.zy17.guess.famous.service.msghandler.DefaultMsgHandle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeixinControllerTest {

  @Value("${weixin.token}")
  private String token;

  @Autowired
  private TestRestTemplate restTemplate;

  //  @Before
  public void init() {
    List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
//    Jaxb2RootElementHttpMessageConverter jaxbMessageConverter = new Jaxb2RootElementHttpMessageConverter();
    List<MediaType> mediaTypes = new ArrayList<MediaType>();
    MediaType e = new MediaType(MediaType.APPLICATION_XML, Charset.forName("utf-8"));
    System.out.println(e.toString());
    mediaTypes.add(e);

    Jaxb2RootElementHttpMessageConverter converter = new Jaxb2RootElementHttpMessageConverter();
    converter.setSupportedMediaTypes(mediaTypes);
    converters.add(converter);
    restTemplate.getRestTemplate().setMessageConverters(converters);
  }

  @Test
  public void echo() {
    System.out.println(token);
    String body = this.restTemplate.getForObject(getUrl(), String.class);
    assertThat(body).isEqualTo("1");
  }

  @Test
  public void textMessage() {
//    构建文本消息
    EventMessage event = getTextEvent("test");
// 测试返回结果
    String body = restTemplate.postForObject(getUrl(), event, String.class);
    assertThat(body).contains(DefaultMsgHandle.DEFAULT);
  }


  @Test
  public void RandomCmdMessage() {
    // 发送一张图片
    EventMessage imageEvent = getImageEvent();
    restTemplate.postForObject(getUrl(), imageEvent, String.class);
    restTemplate.postForObject(getUrl(), getTextEvent("2"), String.class);
    restTemplate.postForObject(getUrl(), getTextEvent("3"), String.class);
    restTemplate.postForObject(getUrl(), getTextEvent("4"), String.class);

    // 随机获取图片
    String body = restTemplate.postForObject(getUrl(), getTextEvent("1"), String.class);
    assertThat(body.contains(imageEvent.getMediaId()));
  }

  public String getUrl() {
    String timestamp = String.valueOf(System.currentTimeMillis());
    String nonce = String.valueOf(new Random().nextInt());
    String signature = SignatureUtil.generateEventMessageSignature(token, timestamp, nonce);
    return "/weixin?signature=" + signature + "&timestamp=" + timestamp + "&nonce=" + nonce + "&echostr=1";
  }

  public EventMessage getTextEvent(String content) {
    //    构建文本消息
    EventMessage event = new EventMessage();
    event.setMsgType(EventType.TEXT.getValue());
    event.setFromUserName("1348831860");
    event.setToUserName("1348831860");
    event.setContent(content);
    event.setCreateTime((int) System.currentTimeMillis());
    event.setMsgId("msgid" + new Random().nextInt(100000));
    System.out.println(XMLConverUtil.convertToXML(event));
    return event;
  }

  public EventMessage getImageEvent() {
    EventMessage event = new EventMessage();
    event.setMsgType(EventType.IMAGE.getValue());
    event.setFromUserName("1348831860");
    event.setToUserName("1348831860");
    event.setMediaId("mediaid" + new Random().nextInt(100000));
    event.setCreateTime((int) System.currentTimeMillis());
    event.setMsgId("msgid" + new Random().nextInt(100000));
    return event;
  }


}
