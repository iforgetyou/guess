package com.zy17.guess.famous.douban.bean;

import lombok.Data;

/**
 * 2017/2/27 zy17
 */
@Data
public class SubjectSuggestResult {
  public static final String RESULT_TYPE_CELEBRITY = "celebrity";
  public static final String RESULT_TYPE_MOVIE = "movie";

  String episode;
  String img;
  String title;
  String url;
  String sub_title;
  String year;
  String type;

}
