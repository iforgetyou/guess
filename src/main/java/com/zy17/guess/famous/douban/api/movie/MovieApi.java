package com.zy17.guess.famous.douban.api.movie;


import com.zy17.guess.famous.douban.api.BaseAPI;
import com.zy17.guess.famous.douban.bean.MovieSearchResult;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 电影API
 * Created by zy17 on 2016/3/17.
 * https://developers.douban.com/wiki/?title=movie_v2
 */
@Service
public class MovieApi extends BaseAPI {
  public static final String SEARCH_URL = "/v2/movie/search?q={q}&tag={tag}&start={start}&count={count}";

  /**
   * 搜索电影api
   * @param query string	str	Y	Y	Y	-
   * @param tag query string	str	Y	Y	Y	-
   * @param start int	Y	Y	Y	0
   * @param count int	Y	Y	Y	20
   * @return
   */
  @Cacheable(value = "movie", key = "#p0+#p1")
  public MovieSearchResult searchMovie(String query, String tag, int start, int count) {
    if (count < 1) {
      count = BaseAPI.DEFAULT_COUNT;
    }
    return restTemplate.getForObject(API_URI + SEARCH_URL, MovieSearchResult.class, query, tag, start, count);
  }
}
