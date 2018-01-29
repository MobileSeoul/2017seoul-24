package com.hbag.seoulhang.model.retrofit;

/**
 * Created by cwh62 on 2017-05-06.
 */

public class ItemDTO {
    private int regioncode;
    private String regionname;
    private int questioncode;
    private String question;
    private String answer;
    private int hintflag;
    private String hint;
    private int hintcount;
    private String questiontype;


    public int getRegioncode() {return this.regioncode;}
    public String getQuestion() {return this.question;}
    public int getQuestioncode() {return this.questioncode;}
    public String getRegionname() {return this.regionname;}
    public String getAnswer() {return  this.answer;}
    public int getHintflag() {return this.hintflag;}
    public String getHint() {return this.hint;}
    public int getHintcount() {return this.hintcount;}
    public String getquestiontype() {return this.questiontype;}

    public void setRegioncode(int regioncode) {this.regioncode = regioncode;}
    public void setQuestion(String question) {this.question = question;}
    public void setQuestioncode(int questioncode) {
        this.questioncode = questioncode;
    }
    public void setRegionname(String regionname) {this.regionname = regionname;}
    public void setAnswer(String answer) {this.answer = answer;}
    public void setHintflag(int hintflag) {this. hintflag = hintflag;}
    public void setHint(String hint) {this.hint = hint;}
    public void setHintcount(int hintcount) {this.hintcount = hintcount;}
    public void setQuestiontype(String questiontype) {this.questiontype = questiontype;}
}
