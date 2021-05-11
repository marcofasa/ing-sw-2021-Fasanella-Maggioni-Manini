package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LightModel {
    private String nickname;
    private Client client;
    private int numberOfPlayers;
    private HashMap<String, Integer> playersPosition;
    private ArrayList<ArrayList<CardDevelopment>> cardDevelopmentMarket;
    private ArrayList<CardDevelopment> cardDevelopment;
    private ArrayList<CardLeader> cardsLeader;
    private ArrayList<FaithTileStatus> tileStatuses; //size()==3 with the tree tiles in TilesPack
    private HashMap<Resource, Integer> deposit;
    private HashMap<Resource, Integer> strongbox;
    private ArrayList<ArrayList<MarbleType>> market;


    //Light Version of the Model, with only the essential parts useful to View

    /**
     * Constructor
     * @param client
     */
    public LightModel(Client client){
        this.client=client;
    }



    public int positionPlayer(){
        return playersPosition.get(nickname);
    }

    //Setters (used as Update methods)

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setCardDevelopmentMarket(ArrayList<ArrayList<CardDevelopment>> cardDevelopmentMarket){
        this.cardDevelopmentMarket= new ArrayList<ArrayList<CardDevelopment>>(cardDevelopmentMarket);
    }
    public void setCardsLeader(ArrayList<CardLeader> cardsLeader) {
        this.cardsLeader = new ArrayList<CardLeader>(cardsLeader);
    }

    public void setCardDevelopment(ArrayList<CardDevelopment> cardDevelopment) {
        this.cardDevelopment = new ArrayList<CardDevelopment>(cardDevelopment);
    }

    public void setMarket(ArrayList<ArrayList<MarbleType>> market) {
        this.market = new ArrayList<ArrayList<MarbleType>>(market);
    }

    public void setStrongbox(HashMap<Resource, Integer> strongbox) {
        this.strongbox = new HashMap<Resource, Integer>(strongbox);
    }

    public void setPlayersPosition(HashMap<String, Integer> playersPosition) {
        this.playersPosition = new HashMap<String, Integer>(playersPosition);
    }

    public void setDeposit(HashMap<Resource, Integer> deposit) {
        this.deposit = new HashMap<Resource, Integer>(deposit);
    }

    public void setTileStatuses(ArrayList<FaithTileStatus> tileStatuses) {
        this.tileStatuses = new ArrayList<FaithTileStatus>(tileStatuses);
    }


    //Getters
    public String getNickname() {
        return nickname;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public ArrayList<ArrayList<MarbleType>> getMarket() {
        if (market==null) market=new ArrayList<ArrayList<MarbleType>>();
        /*
        try {
            client.sendAndWait(new RequestMarketInstance(),-1);
        }
        catch (RequestTimeoutException e){
            e.printStackTrace();
        }

         */
        return market;
    }


    public ArrayList<ArrayList<CardDevelopment>> getCardDevelopmentMarket() {
        if (cardDevelopmentMarket==null) cardDevelopmentMarket= new ArrayList<ArrayList<CardDevelopment>>();
       /*
       try {
            client.sendAndWait(new RequestCardDevelopmentMarketInstance(),-1);

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
            client.sendAndWait(new RequestTileStatusesInstance(),-1);

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
            return playersPosition;
        }
        catch (RequestTimeoutException e){
            e.printStackTrace();
        }
        */

        return playersPosition;
    }

    public HashMap<Resource, Integer> getDeposit(){
        if (deposit==null) deposit=new HashMap<Resource,Integer>();
        /*
        try {
            client.sendAndWait(new RequestDepositIstance(),-1);
        }catch (RequestTimeoutException e){
            e.printStackTrace();
        }

         */
        return deposit;
    }

    public ArrayList<CardLeader> getCardsLeader() {
        if(cardsLeader==null) cardsLeader= new ArrayList<CardLeader>();
        /*
        try {
            client.sendAndWait(new RequestCardLeaderInstance(),-1);
        }catch (RequestTimeoutException e){
            e.printStackTrace();
        }

         */
        return cardsLeader;
    }

    public ArrayList<CardDevelopment> getCardDevelopment() {
        if(cardDevelopment==null) cardDevelopment= new ArrayList<CardDevelopment>();
        /*
        try {
            client.sendAndWait(new RequestCardDevelopmentInstance(),-1);
        }catch (RequestTimeoutException e){
            e.printStackTrace();
        }

         */
        return cardDevelopment;
    }

    public HashMap<Resource, Integer> getStrongbox() {
        if(strongbox==null) strongbox= new HashMap<>();
        /*
        try {
            client.sendAndWait(new RequestStrongboxInstance(),-1);
        }catch (RequestTimeoutException e){
            e.printStackTrace();
        }

         */
        return strongbox;
    }

}
