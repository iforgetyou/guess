package com.zy17.guess.famous.douban.bean;

import lombok.Data;

/**
 * Created by zy17 on 2016/3/17.
 */
@Data
public class BoxSubject extends BaseResult {
    //    rank	排名	int	Y	Y	Y	-
    private long rank;
    //    box	票房	int	Y	Y	Y	-
    private long box;
    //    new	是否新上映	bool	Y	Y	-
    private boolean whetherNew;
    //    subject	电影条目见附录	dict	Y	Y	Y	-
    private SimpleSubject subject;

    public boolean isNew() {
        return whetherNew;
    }

    public void setNew(boolean whetherNew) {
        this.whetherNew = whetherNew;
    }
}
