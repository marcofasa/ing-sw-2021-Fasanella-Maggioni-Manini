package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class CardLeaderWhiteMarble extends CardLeader {

    private Resource resource;

    public CardLeaderWhiteMarble(Resource resource, HashMap<CardDevelopmentType, CardDevelopmentLevel> requirements){
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
