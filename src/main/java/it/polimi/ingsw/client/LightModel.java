package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LightModel {
    private String nickname;
    private int numberOfPlayers;
    private boolean first;
    private HashMap<String, Integer> playersPosition;
    private PlayerState playerState;
    private CardDevelopmentSlot[] cardSlotArray;
    private ArrayList<CardLeader> cardsLeader;
    private ArrayList<FaithTileStatus> tileStatuses; //size()==3 with the tree tiles in TilesPack
    private HashMap<Resource, Integer> deposit;
    private HashMap<Resource, Integer> strongbox;
    private DepositLeaderCard depositLeaderCard;
    private ArrayList<ArrayList<MarbleType>> market;


    public LightModel(){
    }

    public ArrayList<FaithTileStatus> getTileStatuses() {
        return tileStatuses;
    }

    public HashMap<String, Integer> getPlayersPosition() {
        return playersPosition;
    }

    public int positionPlayer(){
        return playersPosition.get(nickname);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public ArrayList<ArrayList<MarbleType>> getMarket() {
        return market;
    }
}