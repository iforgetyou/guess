/*
 * Copyright (C) 2016 , Inc. All Rights Reserved.
 */
package com.zy17.guess.famous.douban.api;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.springframework.web.client.RestTemplate;

public abstract class BaseAPI {
  protected static final String BASE_URI = "https://api.douban.com";
  protected static Header jsonHeader =
      new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
  protected static int DEFAULT_COUNT = 10;

  protected RestTemplate restTemplate = new RestTemplate();

}
