package com.zy17.guess.famous.douban.api.movie;


import com.zy17.guess.famous.douban.api.BaseAPI;
import com.zy17.guess.famous.douban.bean.MovieTop250Result;
import com.zy17.guess.famous.douban.bean.UsBoxResult;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 榜单api
 * Created by zy17 on 2016/3/17.
 */
@Service
public class RankApi extends BaseAPI {
  // 北美票房
  public static final String US_BOX_URL = "/v2/movie/us_box";
  // Top250
  public static final String TOP_250_URL = "/v2/movie/top250?start={start}&count={count}";


  /**
   * 北美票房榜
   * @return
   */
  public UsBoxResult usboxRank() {
    return restTemplate.getForObject(BASE_URI + US_BOX_URL, UsBoxResult.class);
  }

  /**
   * Top250
   * @return
   */
  public MovieTop250Result top250Rank(int start, int count) {
    return restTemplate.getForObject(BASE_URI + TOP_250_URL, MovieTop250Result.class, start, count);
  }
}
