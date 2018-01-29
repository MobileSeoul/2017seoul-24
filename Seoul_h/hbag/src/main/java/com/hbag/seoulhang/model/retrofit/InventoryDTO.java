package com.hbag.seoulhang.model.retrofit;



/**
 * Created by cwh62 on 2017-05-02.
 */

import com.google.gson.annotations.SerializedName;

public class InventoryDTO {
    @SerializedName("region_name")
    private String regionname;
    @SerializedName("question_code")
    private int questioncode;
    private String grade;
    private String question;
    private String answer;
    private String id;
    @SerializedName("player_name")
    private String name;
    private String nickname;
    private String email;
    private int point;
    private int hint;
    @SerializedName("make_quiz")
    private int makequiz;



    public int getQuestioncode() {return this.questioncode;}
    public String getRegionname() {return  this.regionname;}
    public String getQuestion() {return this.question;}
    public String getAnswer() {return this.answer;}
    public String getId() {return this.id;}
    public String getName() {return this.name;}
    public String getNickname() {return this.nickname;}
    public String getEmail() {return this.email;}
    public int getPoint() {return this.point;}
    public int getHint() {return this.hint;}
    public String getGrade() {return this.grade;}
    public int getMakequiz() {return makequiz;}

    public void setRegionname(String regionname) {
        this.regionname = regionname;
    }
    public void setQuestioncode(int questioncode) {
        this.questioncode = questioncode;
    }
    public void setQuestion(String question) {this.question = question;}
    public void setAnswer(String answer) {this.answer = answer;}
    public void setId(String id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setNickname(String nickname) {this.nickname = nickname;}
    public void setEmail(String email) {this.email = email;}
    public void setPoint(int point) {this.point = point;}
    public void setHint(int hint) {this.hint = hint;}
    public void setGrade(String grade) {this.grade = grade;}
    public void setMakequiz(int makequiz) {this.makequiz = makequiz;}
}
