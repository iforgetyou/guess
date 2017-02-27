package com.zy17.guess.famous.douban.bean;

import lombok.Data;

import java.util.List;

/**
 * 北美票房榜
 * Created by zy17 on 2016/3/17.
 */
@Data
public class UsBoxResult extends BaseResult {
    //    排行榜名称	str	Y	Y	N/A	-
    private String title;
    //    date	排行榜日期范围	str	Y	Y	N/A	-
    private String date;
    //    subjects	搜索结果列表，见附录	array	Y	Y	Y	-
    private List<BoxSubject> subjects;
}
