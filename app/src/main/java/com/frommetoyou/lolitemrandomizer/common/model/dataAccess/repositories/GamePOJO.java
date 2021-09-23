package com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories;

public class GamePOJO {
    public static String GAME_TABLE="Game";
    public static String GAME_MODE="1vs1";
    public static String GAME_OWNER="owner";
    public static String GAME_OPPONENT="opponent";

    private String key;
    private PlayerPOJO owner;
    private PlayerPOJO opponent;
    private OptionsPOJO setupOptions;
    private int connectedPlayers;

    public GamePOJO() {
    }

    public GamePOJO(PlayerPOJO owner, PlayerPOJO opponent, OptionsPOJO setupOptions) {
        this.owner = owner;
        this.opponent = opponent;
        this.setupOptions = setupOptions;
        this.connectedPlayers = 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PlayerPOJO getOwner() {
        return owner;
    }

    public void setOwner(PlayerPOJO owner) {
        this.owner = owner;
    }

    public PlayerPOJO getOpponent() {
        return opponent;
    }

    public void setOpponent(PlayerPOJO opponent) {
        this.opponent = opponent;
    }

    public OptionsPOJO getSetupOptions() {
        return setupOptions;
    }

    public void setSetupOptions(OptionsPOJO setupOptions) {
        this.setupOptions = setupOptions;
    }

    public int getConnectedPlayers() {
        return connectedPlayers;
    }

    public void setConnectedPlayers(int connectedPlayers) {
        this.connectedPlayers = connectedPlayers;
    }
}
