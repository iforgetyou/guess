package com.zy17.guess.famous.douban.api.celebrity;


import com.zy17.guess.famous.douban.api.BaseAPI;
import com.zy17.guess.famous.douban.bean.CelebrityResult;
import com.zy17.guess.famous.douban.bean.SubjectSuggestResult;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 影人条目API
 * Created by zy17 on 2016/3/17.
 */
@Service
public class CelebrityApi extends BaseAPI {
  public static final String CELEBRITY_URL = "/v2/movie/celebrity/{celebrityId}";
  public static final String CELEBRITY_QUERY = "/j/subject_suggest?q={celebrityName}";

  /**
   * 影人条目信息
   * @param celebrityId 影人id
   * @return
   */
  public CelebrityResult findCelebrityById(String celebrityId) {
    return restTemplate.getForObject(API_URI + CELEBRITY_URL, CelebrityResult.class, celebrityId);
  }

  @Cacheable(value = "searchCelebrity")
  public SubjectSuggestResult[] findCelebrityByName(String celebrityName) {
    return restTemplate.getForObject(MOVIE_URI + CELEBRITY_QUERY, SubjectSuggestResult[].class, celebrityName);
  }

}
