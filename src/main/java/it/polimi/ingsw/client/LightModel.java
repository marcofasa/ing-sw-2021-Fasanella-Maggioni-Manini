package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.client.RequestMarketUse;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LightModel {
    private String nickname;
    private Client client;
    private int numberOfPlayers;
    private boolean first;
    private HashMap<String, Integer> playersPosition;
    private PlayerState playerState;
    private ArrayList<ArrayList<CardDevelopment>> cardDevelopmentMarket;
    private ArrayList<CardDevelopment> cardDevelopment;
    private ArrayList<CardLeader> cardsLeader;
    private ArrayList<FaithTileStatus> tileStatuses; //size()==3 with the tree tiles in TilesPack
    private HashMap<Resource, Integer> deposit;
    private HashMap<Resource, Integer> strongbox;
    private ArrayList<ArrayList<MarbleType>> market;

    public ArrayList<CardLeader> getCardsLeader() {
        return cardsLeader;
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
        if (market==null) market=new ArrayList<ArrayList<MarbleType>>();
        /*
        try {
            market= client.sendAndWait(new RequestMaketInstance(),-1);
        }
        catch (RequestTimeoutException e){
            e.printStackTrace();
        }

         */
        return market;
    }



    public LightModel(Client client){
        this.client=client;
    }

    public ArrayList<ArrayList<CardDevelopment>> getCardDevelopmentMarket() {
        if (cardDevelopmentMarket==null) cardDevelopmentMarket= new ArrayList<ArrayList<CardDevelopment>>();
       /*
       try {
            cardDevelopmentMarket= client.sendAndWait(new RequestCardDevelopmentMarketInstance(),-1);
        }
        catch (RequestTimeoutException e){
            e.printStackTrace();
        }
        */
        return cardDevelopmentMarket;
    }

    public ArrayList<FaithTileStatus> getTileStatuses() {
        if(tileStatuses==null) tileStatuses=new ArrayList<FaithTileStatus>();
        /*
        try {
            tileStatuses= client.sendAndWait(new RequestTileStatusesInstance(),-1);
        }
        catch (RequestTimeoutException e){
            e.printStackTrace();
        }
        */
        return tileStatuses;
    }

    public HashMap<String, Integer> getPlayersPosition() {
        if (playersPosition==null) playersPosition=new HashMap<String,Integer>();
        /*
        try {
            playersPosition= client.sendAndWait(new RequestPlayersPositionInstance(),-1);
        }
        catch (RequestTimeoutException e){
            e.printStackTrace();
        }
        */

        return playersPosition;
    }

}
