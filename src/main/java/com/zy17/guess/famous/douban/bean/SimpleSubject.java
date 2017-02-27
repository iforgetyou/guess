package com.zy17.guess.famous.douban.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by zy17 on 2016/3/17.
 */
@Data
public class SimpleSubject extends BaseResult {
    public static final String IMAGE_TYPE_LARGE="large";
    public static final String IMAGE_TYPE_MEDIUM="medium";
    public static final String IMAGE_TYPE_SMALL="small";

    public static final String RATING_MIN="min";
    public static final String RATING_MAX="max";
    public static final String RATING_AVERAGE="average";
    public static final String RATING_STARS="stars";
    //    ID	条目id	str	Y	Y	Y	-
    private String id;
    //    title	中文名	str	Y	Y	Y	-
    private String title;
    //    original_title	原名	str	Y	Y	Y	''
    private String original_title;
    //    alt	条目URL	float(1)	Y	Y	Y	-
    private String alt;
    //    images	电影海报图，分别提供288px x 465px(大)，96px x 155px(中) 64px x 103px(小)尺寸	dict	Y	Y	Y	-
    private Map<String, String> images;
    //    rating	评分，见附录	dict	Y	Y	Y	-
    //    min	最低评分	int	Y	Y	Y	-
    //    max	最高评分	int	Y	Y	Y	-
    //    average	评分	float(1)	Y	Y	Y	-
    //    stars	评星数	int	Y	Y	Y	-
    Map<String, String> rating;
    //    pubdates	如果条目类型是电影则为上映日期，如果是电视剧则为首播日期	array	N	Y	Y	[]
    //    year	年代	str	Y	Y	Y	''
    private String year;
    //    subtype	条目分类, movie或者tv	str	Y	Y	Y	movie
    private String subtype;
    // 类型
    //    "剧情",
    //    "悬疑"
    private List<String> genres;
    // 演员表
    private List<CelebrityResult> casts;
    // 搜藏数
    private int collect_count;
    // 导演
    private List<CelebrityResult> directors;

}
