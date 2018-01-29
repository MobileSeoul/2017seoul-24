package com.hbag.seoulhang.model.retrofit;

import java.util.Date;

/**
 * Created by cwh62 on 2017-10-10.
 */

public class NoticeDTO {
    private String title_ko;
    private String title_en;
    private String date1;
    private String date2;


    public String getTitle_en() {
        return title_en;
    }

    public String getTitle_ko() {
        return title_ko;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public void setTitle_ko(String title_ko) {
        this.title_ko = title_ko;
    }

    public String getDate1() {
        return this.date1;
    }

    public String getDate2() { return this.date2;}



    public void setDate1(String date) {
        this.date1 = date1;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }
}
