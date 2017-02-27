package com.zy17.guess.famous.douban.api.celebrity;


import com.zy17.guess.famous.douban.api.BaseAPI;
import com.zy17.guess.famous.douban.bean.CelebrityResult;

import org.springframework.stereotype.Service;

/**
 * 影人条目API
 * Created by zy17 on 2016/3/17.
 */
@Service
public class CelebrityApi extends BaseAPI {
  public static final String CELEBRITY_URL = "/v2/movie/celebrity/{celebrityId}";

  /**
   * 影人条目信息
   * @param celebrityId 影人id
   * @return
   */
  public CelebrityResult findCelebrity(String celebrityId) {
    return restTemplate.getForObject(BASE_URI + CELEBRITY_URL, CelebrityResult.class, celebrityId);
  }
}
