
package com.frommetoyou.lolitemrandomizer.common.http.pojo;

import java.util.List;
import java.util.Map;

import com.frommetoyou.lolitemrandomizer.common.http.pojo.Items.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeagueOfLegendsItems {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("basic")
    //@Expose TODO fijarse si funciona el quitar expose
    private transient Basic basic;/*
    @SerializedName("data")
    @Expose
    private List<Item> items;*/
    @SerializedName("data")
    @Expose
    private Map<String,Item> items;
    @SerializedName("groups")
    @Expose
    private List<Object> groups = null;
    @SerializedName("tree")
    @Expose
    private List<Object> tree = null;

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

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void setItems(Map<String, Item> items) {
        this.items = items;
    }
    /*

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }*/

    public List<Object> getGroups() {
        return groups;
    }

    public void setGroups(List<Object> groups) {
        this.groups = groups;
    }

    public List<Object> getTree() {
        return tree;
    }

    public void setTree(List<Object> tree) {
        this.tree = tree;
    }

}
