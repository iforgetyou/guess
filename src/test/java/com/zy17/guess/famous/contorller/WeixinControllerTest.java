package com.zy17.guess.famous.contorller;

import static org.assertj.core.api.Assertions.assertThat;

import com.zy17.guess.famous.EventGen;
import com.zy17.guess.famous.service.CacheService;

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

import weixin.popular.api.UserAPI;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.user.FollowResult;
import weixin.popular.support.TokenManager;
import weixin.popular.util.SignatureUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeixinControllerTest {

  @Value("${weixin.token}")
  private String token;

  @Autowired
  CacheService cache;

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
  public void RandomCmdMessageTest() {
    // 发送一张图片
    EventMessage imageEvent = EventGen.getImageEvent();
    restTemplate.postForObject(getUrl(), imageEvent, String.class);
    restTemplate.postForObject(getUrl(), EventGen.getTextEvent("2"), String.class);
    restTemplate.postForObject(getUrl(), EventGen.getTextEvent("3"), String.class);
    restTemplate.postForObject(getUrl(), EventGen.getTextEvent("4"), String.class);

    // 随机获取图片
    String body = restTemplate.postForObject(getUrl(), EventGen.getTextEvent("1"), String.class);
    assertThat(body.contains(imageEvent.getMediaId()));
  }

  @Test
  public void imageAddTagTest() {
    // 发送一张图片
    EventMessage imageEvent = EventGen.getImageEvent();
    String resp = restTemplate.postForObject(getUrl(), imageEvent, String.class);

    assertThat(resp).contains("分钟内添加文字标签");
    assertThat(resp).contains("收到图片");

    String body = restTemplate.postForObject(getUrl(), EventGen.getTextEvent("测试标签"), String.class);
    System.out.println(body);
    assertThat(body.contains("news"));
  }

  @Test
  public void newUserTest() {
    String body = restTemplate.postForObject(getUrl(), EventGen.getSubEvent(), String.class);
    System.out.println(body);
  }

  @Test
  public void AnswerTest() {
    restTemplate.postForObject(getUrl(), EventGen.getImageEvent(), String.class);
    restTemplate.postForObject(getUrl(), EventGen.getTextEvent("这是真标签"), String.class);

    restTemplate.postForObject(getUrl(), EventGen.getTextEvent("1"), String.class);

    String a = restTemplate.postForObject(getUrl(), EventGen.getTextEvent("2"), String.class);
    System.out.println(a);
    assertThat(a).contains("picurl").contains("");
  }

  @Test
  public void cacheTest() throws InterruptedException {
    String key = "test1";
    cache.put(key, "value");
    Object value = cache.get(key);
    assertThat(value).isNotNull();
    Object pop = cache.pop(key);
    assertThat(pop).isNotNull();
    assertThat(cache.get(key)).isNull();
  }

  //  @Test
  public void getAllUser() {
    FollowResult followResult = UserAPI.userGet(TokenManager.getDefaultToken(), null);
    for (String id : followResult.getData().getOpenid()) {
      System.out.println(id);
    }
  }

  public String getUrl() {
    String timestamp = String.valueOf(System.currentTimeMillis());
    String nonce = String.valueOf(new Random().nextInt());
    String signature = SignatureUtil.generateEventMessageSignature(token, timestamp, nonce);
    return "/weixin?signature=" + signature + "&timestamp=" + timestamp + "&nonce=" + nonce + "&echostr=1";
  }


}
