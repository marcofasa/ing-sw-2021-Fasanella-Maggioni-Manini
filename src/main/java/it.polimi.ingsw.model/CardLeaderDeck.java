package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;

public class CardLeaderDeck {

    private final ArrayList<CardLeader> cardLeaders;

    public CardLeaderDeck(GameTable gameTable){
        cardLeaders = new ArrayList<>(3);
        CardLeaderFactory cardLeaderFactory = new CardLeaderFactory();
        for (CardLeaderType type: CardLeaderType.values()
             ) {
            for (Resource resource: Resource.values()) {
                cardLeaders.add(cardLeaderFactory.produce(type, resource));
            }
        }
    }

}
