package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class CardLeaderDeck implements Serializable {

    private final ArrayList<CardLeader> cardsLeader;

    /**
     * Constructor of the class, generates the deck
     * @param gameTable gameTable of the game
     */
    public CardLeaderDeck(GameTable gameTable){
        cardsLeader = new ArrayList<>(16);
        CardLeaderFactory cardLeaderFactory = new CardLeaderFactory();
        for (CardLeaderType type: CardLeaderType.values()
             ) {
            for (Resource resource: Resource.values()) {
                cardsLeader.add(cardLeaderFactory.produce(type, resource));
            }
        }
        Collections.shuffle(cardsLeader);
    }

    /**
     * get card leader from the deck. This should only be done in the creation of the game.
     * @param playerBoard player who's buying the card
     * @return CardLeader bought
     */
    public CardLeader getCardLeader(PlayerBoard playerBoard){
        return cardsLeader.remove(0).draw(playerBoard);
    }
}
