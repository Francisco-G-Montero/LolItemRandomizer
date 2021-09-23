package com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories;

public class OptionsPOJO {
    private String mapSelected;
    private String itemPool;

    public OptionsPOJO() {
    }

    public OptionsPOJO(String mapSelected, String itemPool) {
        this.mapSelected = mapSelected;
        this.itemPool = itemPool;
    }

    public String getMapSelected() {
        return mapSelected;
    }

    public void setMapSelected(String mapSelected) {
        this.mapSelected = mapSelected;
    }

    public String getItemPool() {
        return itemPool;
    }

    public void setItemPool(String itemPool) {
        this.itemPool = itemPool;
    }
}
