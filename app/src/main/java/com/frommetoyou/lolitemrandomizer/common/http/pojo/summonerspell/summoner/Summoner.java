
package com.frommetoyou.lolitemrandomizer.common.http.pojo.summonerspell.summoner;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Summoner {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("tooltip")
    @Expose
    private String tooltip;
    @SerializedName("maxrank")
    @Expose
    private Integer maxrank;
    @SerializedName("cooldown")
    @Expose
    private List<Object> cooldown = null;
    @SerializedName("cooldownBurn")
    @Expose
    private String cooldownBurn;
    @SerializedName("cost")
    @Expose
    private List<Object> cost = null;
    @SerializedName("costBurn")
    @Expose
    private String costBurn;
    @SerializedName("datavalues")
    @Expose
    private Datavalues datavalues;
    @SerializedName("effect")
    @Expose
    private List<Object> effect = null;
    @SerializedName("effectBurn")
    @Expose
    private List<Object> effectBurn = null;
    @SerializedName("vars")
    @Expose
    private List<Object> vars = null;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("summonerLevel")
    @Expose
    private Integer summonerLevel;
    @SerializedName("modes")
    @Expose
    private List<String> modes = null;
    @SerializedName("costType")
    @Expose
    private String costType;
    @SerializedName("maxammo")
    @Expose
    private String maxammo;
    @SerializedName("range")
    @Expose
    private List<Integer> range = null;
    @SerializedName("rangeBurn")
    @Expose
    private String rangeBurn;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("resource")
    @Expose
    private String resource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public Integer getMaxrank() {
        return maxrank;
    }

    public void setMaxrank(Integer maxrank) {
        this.maxrank = maxrank;
    }

    public List<Object> getCooldown() {
        return cooldown;
    }

    public void setCooldown(List<Object> cooldown) {
        this.cooldown = cooldown;
    }

    public String getCooldownBurn() {
        return cooldownBurn;
    }

    public void setCooldownBurn(String cooldownBurn) {
        this.cooldownBurn = cooldownBurn;
    }

    public List<Object> getCost() {
        return cost;
    }

    public void setCost(List<Object> cost) {
        this.cost = cost;
    }

    public String getCostBurn() {
        return costBurn;
    }

    public void setCostBurn(String costBurn) {
        this.costBurn = costBurn;
    }

    public Datavalues getDatavalues() {
        return datavalues;
    }

    public void setDatavalues(Datavalues datavalues) {
        this.datavalues = datavalues;
    }

    public List<Object> getEffect() {
        return effect;
    }

    public void setEffect(List<Object> effect) {
        this.effect = effect;
    }

    public List<Object> getEffectBurn() {
        return effectBurn;
    }

    public void setEffectBurn(List<Object> effectBurn) {
        this.effectBurn = effectBurn;
    }

    public List<Object> getVars() {
        return vars;
    }

    public void setVars(List<Object> vars) {
        this.vars = vars;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(Integer summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public List<String> getModes() {
        return modes;
    }

    public void setModes(List<String> modes) {
        this.modes = modes;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getMaxammo() {
        return maxammo;
    }

    public void setMaxammo(String maxammo) {
        this.maxammo = maxammo;
    }

    public List<Integer> getRange() {
        return range;
    }

    public void setRange(List<Integer> range) {
        this.range = range;
    }

    public String getRangeBurn() {
        return rangeBurn;
    }

    public void setRangeBurn(String rangeBurn) {
        this.rangeBurn = rangeBurn;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

}
