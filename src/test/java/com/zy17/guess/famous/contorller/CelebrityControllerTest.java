package com.zy17.guess.famous.contorller;

import static org.assertj.core.api.Assertions.assertThat;

import com.zy17.guess.famous.SpringBootTestBase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 2017/3/7 zy17
 */
public class CelebrityControllerTest extends SpringBootTestBase {
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void saveTest() throws Exception {
    ResponseEntity<Void> resp = this.restTemplate.postForEntity("/celebrity?id=1031194", null,
        Void.class);
    assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }


}