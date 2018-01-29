package com.hbag.seoulhang.model.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cwh62 on 2017-05-10.
 */

public class RateDTO {
    private int rate;
    private String explain;
    @SerializedName("region_name")
    private String regionname;
    private int regioncode;



    public int getRate() { return this.rate;}
    public String getExplain() {return this.explain;}
    public String getRegionname() {return this.regionname;}
    public int getRegioncode() {return this.regioncode;}


    public void setRate(int rate) {
        this.rate = rate;
    }
    public void setExplain(String explain) {this.explain = explain;}
    public void setRegionname(String regionname) {this.regionname = regionname;}
    public void setRegioncode(int regioncode) {this.regioncode = regioncode;}
}
