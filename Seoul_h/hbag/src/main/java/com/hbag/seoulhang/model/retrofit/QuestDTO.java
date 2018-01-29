package com.hbag.seoulhang.model.retrofit;

/**
 * Created by cwh62 on 2017-05-02.
 */

import com.google.gson.annotations.SerializedName;

public class QuestDTO {
    private String question;
    private String answer;
    @SerializedName("question_code")
    private int questioncode;
    @SerializedName("region_code")
    private int regioncode;
    @SerializedName("content_type")
    private String contenttype;
    private int qresult;


    public int getQresult() { return this.qresult;}

    public String getContenttype() {
        return this.contenttype;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public int getQuestioncode() {
        return this.questioncode;
    }

    public int getRegioncode() {return this.regioncode;}


    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestioncode(int questioncode) {
        this.questioncode = questioncode;
    }

    public void setRegioncode(int regioncode) {
        this.regioncode = regioncode;
    }

    public void setQresult(int result) {this.qresult = qresult;}
}


