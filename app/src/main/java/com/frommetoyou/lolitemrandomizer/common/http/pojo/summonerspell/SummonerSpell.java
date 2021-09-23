
package com.frommetoyou.lolitemrandomizer.common.http.pojo.summonerspell;

import com.frommetoyou.lolitemrandomizer.common.http.pojo.summonerspell.summoner.Summoner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class SummonerSpell {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("data")
    @Expose
    private Map<String, Summoner> summoners;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Summoner> getSummoners() {
        return summoners;
    }

    public void setSummoners(Map<String, Summoner> summoners) {
        this.summoners = summoners;
    }
}
