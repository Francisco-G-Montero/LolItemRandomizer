package com.frommetoyou.lolitemrandomizer.randomizer.pojo;

import java.util.Objects;

public class ItemPOJO {
    private final String BASE_IMAGE_URL="https://ddragon.leagueoflegends.com/cdn/11.1.1/img/item/";
    public static final String TYPE_BOOTS="Boots";
    public static final String TYPE_MYTHIC="Pasiva Mítica:";
    public static final String COMODIN="¡Comodín!";
    public static final String TYPE_ESPATULA="La Espátula Dorada";
    public static final String TYPE_JUNGLE="Jungle";
    public static final String TYPE_LANE="Lane";
    public static final String BASE_SPELL_IMAGE_URL="https://ddragon.leagueoflegends.com/cdn/11.1.1/img/spell/";
    public static final String SPELL_MODE_CLASSIC="CLASSIC";
    public static final String ITEM_POOL_ONLY_COMPLETE ="Complete";
    public static final String ITEM_ONLY_INCOMPLETE ="Incomplete";
    public static final String ITEM_POOL_BOTH ="CompleteAndIncomplete";

    private String name;
    private String imageURL;

    public ItemPOJO() {
    }

    public ItemPOJO(String name, String imageURL) {
        this.name = name;
        this.imageURL=BASE_IMAGE_URL+imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPOJO itemPOJO = (ItemPOJO) o;
        return Objects.equals(name, itemPOJO.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
