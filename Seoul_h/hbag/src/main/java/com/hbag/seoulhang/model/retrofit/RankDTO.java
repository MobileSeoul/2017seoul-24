package com.hbag.seoulhang.model.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cwh62 on 2017-05-09.
 */

public class RankDTO {
    @SerializedName("player_id")
    private String playerid;
    @SerializedName("player_name")
    private String playername;
    private int rank;
    private int point;
    private String grade;


    public String getPlayerid() {return this.playerid;}
    public String getPlayername() {return this.playername;}
    public int getRank() {return  this.rank;}
    public int getPoint() {return this.point;}
    public String getGrade() {return this.grade;}

    public void setPlayerid(String playerid) {this.playerid = playerid;}
    public void setPlayername(String playername) {this.playername = playername;}
    public void setRank(int rank) {this.rank = rank;}
    public void setPoint(int point) {this.point = point;}
    public void setGrade(String grade) {this.grade = grade;}

}
