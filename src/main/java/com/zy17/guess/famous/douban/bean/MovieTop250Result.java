package com.zy17.guess.famous.douban.bean;

import lombok.Data;

import java.util.List;

/**
 * Top250
 * Created by zy17 on 2016/3/17.
 */
@Data
public class MovieTop250Result extends BaseResult {
    // 搜索条件
    //    start	start	int	Y	Y	Y	0
    //    count	count	int	Y	Y	Y	20
    private long start;
    //    count	count	int	Y	Y	Y	20
    private long count;
    //    total	总数, Basic最多只返回20条记录	int	Y	Y	Y	0
    private long total;
    //    query	搜索字符串	str	Y	Y	Y	-
    private String title;
    //    subjects	搜索结果列表，见附录	array	Y	Y	Y	-
    private List<SimpleSubject> subjects;
}
