package it.polimi.ingsw.model;

import java.util.HashMap;

public class CardLeaderDeposit extends CardLeader{

    private Resource resource;

    private PlayerBoard playerBoard;

    private HashMap<CardDevelopmentType, CardDevelopmentLevel> requirements;

    public CardLeaderDeposit(Resource resource, CardLeaderRequirements requirements){
        this.resource = resource;
    }

    @Override
    public boolean canActivate() {
        return false;
    }

    @Override
    public void activate() {

    }
}
