package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.client.requests.*;
import it.polimi.ingsw.model.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LightModel {
    private String nickname;
    private Client client;
    private int numberOfPlayers;
    private ArrayList<ArrayList<CardDevelopment>> cardDevelopmentMarket;
    private ArrayList<CardDevelopment> cardsDevelopment;
    private ArrayList<CardLeader> cardsLeader;
    private HashMap<Resource, Integer> deposit;
    private HashMap<Resource, Integer> strongbox;
    private ArrayList<ArrayList<MarbleType>> market;
    private Marble spareMarble;
    private ArrayList<Resource> depositLeaderResources;
    private HashMap<Resource, Integer> depositLeaderContent;


    //Light Version of the Model, with only the essential parts useful to View

    /**
     * Constructor
     * @param client
     */
    public LightModel(Client client){
        this.client=client;
    }

    //Setters (used as Update methods)

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSpareMarble(Marble spareMarble) {
        this.spareMarble = spareMarble;
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

    public void setCardsDevelopment(ArrayList<CardDevelopment> cardsDevelopment) {
        this.cardsDevelopment = new ArrayList<CardDevelopment>(cardsDevelopment);
    }

    public void setMarket(ArrayList<ArrayList<MarbleType>> market) {
        this.market = new ArrayList<ArrayList<MarbleType>>(market);
    }

    public void setStrongbox(HashMap<Resource, Integer> strongbox) {
        this.strongbox = new HashMap<>(strongbox);
    }


    public void setDeposit(HashMap<Resource, Integer> deposit) {
        this.deposit = new HashMap<>(deposit);
    }

    public void setDepositLeaderContent(HashMap<Resource, Integer> depositLeaderContent) {
        this.depositLeaderContent = depositLeaderContent;
    }

    public void setDepositLeaderResources(ArrayList<Resource> depositLeaderResources) {
        this.depositLeaderResources = depositLeaderResources;
    }

    //Getters


    public MarbleType getSpareMarble() {
        return spareMarble.getType();
    }

    public String getNickname() {
        return nickname;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public ArrayList<ArrayList<MarbleType>> getMarket() {
        if (market==null) market = new ArrayList<ArrayList<MarbleType>>();

        try {
            client.sendAndWait(new RequestMarketInstance(),-1);
        }
        catch (RequestTimeoutException e){
            e.printStackTrace();
        }

        return market;
    }


    public ArrayList<ArrayList<CardDevelopment>> getCardDevelopmentMarket() {
        if (cardDevelopmentMarket == null) cardDevelopmentMarket= new ArrayList<>();

       try {
            client.sendAndWait(new RequestCardDevelopmentMarketInstance(),-1);

        }
        catch (RequestTimeoutException e){
            e.printStackTrace();
        }

        return cardDevelopmentMarket;
    }

    public HashMap<Resource, Integer> getDeposit(){
        if (deposit==null) deposit=new HashMap<Resource,Integer>();

        try {
            client.sendAndWait(new RequestDepositInstance(),-1);
        } catch (RequestTimeoutException e){
            e.printStackTrace();
        }

        return deposit;
    }

    public ArrayList<CardLeader> getCardsLeader() {
        if(cardsLeader == null) cardsLeader= new ArrayList<CardLeader>();

        try {
            client.sendAndWait(new RequestCardLeaders(),-1);
        } catch (RequestTimeoutException e){
            e.printStackTrace();
        }


        return cardsLeader;
    }

    public ArrayList<CardDevelopment> getCardsDevelopment() {
        if(cardsDevelopment == null) cardsDevelopment = new ArrayList<CardDevelopment>();

        try {
            client.sendAndWait(new RequestTopCardsDevelopment(),-1);
        } catch (RequestTimeoutException e){
            e.printStackTrace();
        }

        return cardsDevelopment;
    }

    public HashMap<Resource, Integer> getStrongbox() {
        if (strongbox==null) strongbox= new HashMap<>();


        try {
            client.sendAndWait(new RequestStrongboxInstance(),-1);
        } catch (RequestTimeoutException e){
            e.printStackTrace();
        }


        return strongbox;
    }

    public ArrayList<Resource> getDepositLeaderResources() {
        return depositLeaderResources;
    }

    public HashMap<Resource, Integer> getDepositLeaderContent() {
        return depositLeaderContent;
    }
}
