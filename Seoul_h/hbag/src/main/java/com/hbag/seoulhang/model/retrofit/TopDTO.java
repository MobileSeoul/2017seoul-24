package com.hbag.seoulhang.model.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cwh62 on 2017-10-10.
 */

public class TopDTO {

    @SerializedName("question_code")
    private int questioncode;
    @SerializedName("question_name")
    private String questionname;
    private int count;
    private String region_name;


    public int getCount() {
        return this.count;
    }

    public int getQuestioncode() {
        return this.questioncode;
    }

    public String getQuestionname() {
        return this.questionname;
    }

    public String getRegion_name() {
        return this.region_name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setQuestioncode(int questioncode) {
        this.questioncode = questioncode;
    }

    public void setQuestionname(String questionname) {
        this.questionname = questionname;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
}
