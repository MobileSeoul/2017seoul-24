package com.hbag.seoulhang.model.retrofit;

/**
 * Created by cwh62 on 2017-10-18.
 */

public class DataBaseDTO {
    private int region_code;
    private int question_code;
    private String train_code;
    private String question_name_ko;
    private String question_name_en;
    private double x_coordinate;
    private double y_coordinate;
    private int question_length;

    public void setRegion_code(int region_code) {
        this.region_code = region_code;
    }

    public void setQuestion_name_ko(String question_name_ko) {
        this.question_name_ko = question_name_ko;
    }

    public void setQuestion_code(int question_code) {
        this.question_code = question_code;
    }

    public void setTrain_code(String train_code) {
        this.train_code = train_code;
    }

    public void setQuestion_name_en(String question_name_en) {
        this.question_name_en = question_name_en;
    }

    public void setX_coordinate(double x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public void setY_coordinate(double y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public void setQuestion_length(int question_length) {
        this.question_length = question_length;
    }

    public int getRegion_code() {
        return region_code;
    }

    public String getQuestion_name_ko() {
        return question_name_ko;
    }

    public int getQuestion_code() {
        return question_code;
    }

    public double getX_coordinate() {
        return x_coordinate;
    }

    public double getY_coordinate() {
        return y_coordinate;
    }

    public String getQuestion_name_en() {
        return question_name_en;
    }

    public String getTrain_code() {
        return train_code;
    }

    public int getQuestion_length() {
        return question_length;
    }
}
