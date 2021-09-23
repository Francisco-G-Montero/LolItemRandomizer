
package com.frommetoyou.lolitemrandomizer.common.http.pojo.Items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rune {

    @SerializedName("isrune")
    @Expose
    private Boolean isrune;
    @SerializedName("tier")
    @Expose
    private Integer tier;
    @SerializedName("type")
    @Expose
    private String type;

    public Boolean getIsrune() {
        return isrune;
    }

    public void setIsrune(Boolean isrune) {
        this.isrune = isrune;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
