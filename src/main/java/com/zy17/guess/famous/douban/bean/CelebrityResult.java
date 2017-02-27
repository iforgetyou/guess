package com.zy17.guess.famous.douban.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 演员
 * Created by zy17 on 2016/3/17.
 */
@Data
public class CelebrityResult extends BaseResult {
    public static final String IMAGE_TYPE_LARGE = "large";
    public static final String IMAGE_TYPE_MEDIUM = "medium";
    public static final String IMAGE_TYPE_SMALL = "small";

    //    ID	条目id	str	Y	Y	N/A	-
    private String id;
    //    name	中文名	str	Y	Y	N/A	-
    private String name;
    //    name_en	英文名	str	Y	Y	N/A	''
    private String name_en;
    //    alt	条目页URL	str	Y	Y	N/A	-
    private String alt;
    //    mobile_url	条目移动版URL	str	Y	Y	N/A	-
    private String mobile_url;
    //    avatars	影人头像，分别提供420px x 600px(大)，140px x 200px(中) 70px x 100px(小)尺寸	dict	Y	Y	N/A	-
    //    "small":"https://img1.doubanio.com/img/celebrity/small/51597.jpg",
    //    "large":"https://img1.doubanio.com/img/celebrity/large/51597.jpg",
    //    "medium":"https://img1.doubanio.com/img/celebrity/medium/51597.jpg"
    private Map<String, String> avatars;
    //    summary	简介	str	N	Y	N/A	''
    private String summary;
    //    aka	更多中文名	array	Y	Y	N/A	[]
    private List<String> aka;
    //    aka_en	更多英文名	array	Y	Y	N/A	[]
    private List<String> aka_en;
    //    website	官方网站	str	N	Y	N/A	''
    private String website;
    //    gender	性别	str	Y	Y	N/A	''
    private String gender;
    //    birthday	出生日期	str	N	Y	N/A	''
    private String birthday;
    //    born_place	出生地	str	Y	Y	N/A	''
    private String born_place;
    //    professions	职业	array	N	Y	N/A	[]
    private List<String> professions;
    //    constellation	星座	str	N	Y	N/A	''
    private String constellation;
    //    photos	影人剧照，最多10张，见附录	array	N	Y	N/A	[]
    private List<Photo> photos;
    //    works	影人作品，最多5部，简版电影条目结构，见附录	array	Y	Y	N/A	[]
    private List<CelebritySimpleSubject> works;
}
