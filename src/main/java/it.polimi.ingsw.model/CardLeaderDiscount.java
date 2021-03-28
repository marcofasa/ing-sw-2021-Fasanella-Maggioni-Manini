package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class CardLeaderDiscount extends CardLeader {

    private Resource resource;

    public CardLeaderDiscount(Resource resource, HashMap<CardDevelopmentType, CardDevelopmentLevel> requirements){
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
