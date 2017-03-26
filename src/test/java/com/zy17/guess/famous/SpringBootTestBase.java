package com.zy17.guess.famous;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import weixin.popular.util.SignatureUtil;

/**
 * 2017/2/25 zy17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootTestBase {
  @Value("${weixin.token}")
  protected String token;

  @Test
  public void init() {

  }

  public String getUrl() {
    String timestamp = String.valueOf(System.currentTimeMillis());
    String nonce = String.valueOf(new Random().nextInt());
    String signature = SignatureUtil.generateEventMessageSignature(token, timestamp, nonce);
    String url = "/weixin?signature=" + signature + "&timestamp=" + timestamp + "&nonce=" + nonce;
    System.out.println(url);
    return url;
  }
}
