package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.CardDevelopment;
import it.polimi.ingsw.model.enums.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BriefModel implements Serializable {

    private final HashMap<Resource,Integer> deposit;

    private final HashMap<Resource,Integer> strongBox;

    private final ArrayList<CardLeader> visibleCardsLeaders;

    private final ArrayList<CardDevelopment> cardsDevelopment;

    private final Integer faithTrailPosition;

    private final boolean empty;

    public BriefModel(PlayerBoard playerBoard){
        deposit = playerBoard.getDepositInstance().getContent();
        strongBox = playerBoard.getStrongboxInstance().getContent();
        visibleCardsLeaders = new ArrayList<>();
        for (CardLeader cardLeader:
                playerBoard.getCardsLeader()
             ) {
            if (cardLeader.active){
                visibleCardsLeaders.add(cardLeader);
            }
        }
        cardsDevelopment = playerBoard.getAllDevelopmentCards();
        faithTrailPosition = playerBoard.getFaithTrailPosition();
        empty = false;
    }

    public BriefModel() {
        deposit = new HashMap<>();
        strongBox = new HashMap<>();
        visibleCardsLeaders = new ArrayList<>();
        cardsDevelopment = new ArrayList<>();
        faithTrailPosition = 0;
        empty = true;
    }

    public HashMap<Resource, Integer> getDeposit() {
        return deposit;
    }

    public HashMap<Resource, Integer> getStrongBox() {
        return strongBox;
    }

    public ArrayList<CardDevelopment> getCardsDevelopment() {
        return cardsDevelopment;
    }

    public Integer getFaithTrailPosition() {
        return faithTrailPosition;
    }

    public ArrayList<CardLeader> getVisibleCardsLeaders() {
        return visibleCardsLeaders;
    }

    public boolean isEmpty(){
        return empty;
    }
}

