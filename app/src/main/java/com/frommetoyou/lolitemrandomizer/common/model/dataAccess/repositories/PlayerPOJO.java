package com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories;

import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;

import java.util.List;

public class PlayerPOJO {
    private String name;
    private List<ItemPOJO> items;
    private List<ItemPOJO> summoners;
    private String winDescription;

    public PlayerPOJO() {
    }

    public PlayerPOJO(String name, List<ItemPOJO> items, List<ItemPOJO> summoners, String winDescription) {
        this.name = name;
        this.items = items;
        this.summoners = summoners;
        this.winDescription = winDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemPOJO> getItems() {
        return items;
    }

    public void setItems(List<ItemPOJO> items) {
        this.items = items;
    }

    public List<ItemPOJO> getSummoners() {
        return summoners;
    }

    public void setSummoners(List<ItemPOJO> summoners) {
        this.summoners = summoners;
    }

    public String getWinDescription() {
        return winDescription;
    }

    public void setWinDescription(String winDescription) {
        this.winDescription = winDescription;
    }
}
