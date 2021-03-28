package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;

public class CardLeaderDeck {

    private ArrayList<CardLeader> cardLeaders;

    public void CardLeaderDeck(GameTable gameTable){
        cardLeaders = new ArrayList<>();
        CardLeaderFactory cardLeaderFactory = new CardLeaderFactory();
        ArrayList<Resource> cardLeaderGenerationResources = new ArrayList<Resource>(Arrays.asList(Resource.values()));
        for (CardLeaderType type: CardLeaderType.values()
             ) {
            for (Resource resource: Resource.values()) {
                cardLeaders.add(cardLeaderFactory.produce(type, resource));
            }
        }
    };

}
