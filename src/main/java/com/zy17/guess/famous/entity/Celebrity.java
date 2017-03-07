package com.zy17.guess.famous.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 2017/2/27 zy17
 */
@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Celebrity extends BaseEntity {

  //    ID	条目id	str	Y	Y	N/A	-
  @Id
  private String id;
  //    name	中文名	str	Y	Y	N/A	-
  private String name;
  //    name_en	英文名	str	Y	Y	N/A	''
  private String nameEn;
  //    alt	条目页URL	str	Y	Y	N/A	-
  private String alt;
  //    mobile_url	条目移动版URL	str	Y	Y	N/A	-
  private String mobileUrl;
  //    avatars	影人头像，分别提供420px x 600px(大)，140px x 200px(中) 70px x 100px(小)尺寸	dict	Y	Y	N/A	-
  private String avatarL;
  private String avatarM;
  private String avatarS;
  //    summary	简介	str	N	Y	N/A	''
  private String summary;
  //    website	官方网站	str	N	Y	N/A	''
  private String website;
  //    gender	性别	str	Y	Y	N/A	''
  private String gender;
  //    birthday	出生日期	str	N	Y	N/A	''
  private String birthday;
  //    born_place	出生地	str	Y	Y	N/A	''
  private String bornPlace;
  //    constellation	星座	str	N	Y	N/A	''
  private String constellation;

}
