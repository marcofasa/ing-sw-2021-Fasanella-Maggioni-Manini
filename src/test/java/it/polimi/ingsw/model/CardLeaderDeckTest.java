package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CardLeaderDeckTest {

    @Test
    public void cardLeaderTest1() {
        GameTable gameTable = new GameTable(new ArrayList<String>(Arrays.asList("1", "2", "3")));
        PlayerBoard playerBoard = new PlayerBoard("1", false, PlayerState.IDLE, gameTable);
        playerBoard.drawCardLeaderFromDeck();
        playerBoard.drawCardLeaderFromDeck();
        playerBoard.drawCardLeaderFromDeck();
        playerBoard.drawCardLeaderFromDeck();
        var cardsLeader = playerBoard.getCardsLeaderBeforeSelecting();
        playerBoard.selectCardsLeader(cardsLeader.get(0), cardsLeader.get(1));
        int i=0;
        for (var card :
                playerBoard.getCardsLeader()) {
            assertEquals(card, cardsLeader.get(i));
            i++;
        }
    }

    @Test (expected = CardLeaderRequirementsNotMetException.class)
    public void cardLeaderActivationTest_shouldThrowExceptionCannotActivate(){
        GameTable gameTable = new GameTable(new ArrayList<String>(Arrays.asList("1", "2", "3")));
        PlayerBoard playerBoard = new PlayerBoard("1", false, PlayerState.IDLE, gameTable);
        CardLeader cardleaderWhite = new CardLeaderWhiteMarble(Resource.Coins, CardLeaderRequirementsFinder.getRequirements(CardLeaderType.WhiteMarble, Resource.Coins), CardLeaderRequirementsFinder.getVictoryPoints(CardLeaderType.WhiteMarble));
        cardleaderWhite.playerName = playerBoard.getNickname();
        cardleaderWhite.activate(playerBoard);
    }

    public void cardLeaderActivationTest(){
        GameTable gameTable = new GameTable(new ArrayList<String>(Arrays.asList("1", "2", "3")));
        PlayerBoard playerBoard = new PlayerBoard("1", false, PlayerState.IDLE, gameTable);
        CardLeader cardleaderWhite = new CardLeaderWhiteMarble(Resource.Coins, CardLeaderRequirementsFinder.getRequirements(CardLeaderType.WhiteMarble, Resource.Coins), CardLeaderRequirementsFinder.getVictoryPoints(CardLeaderType.WhiteMarble));
        cardleaderWhite.playerName = playerBoard.getNickname();
        MarbleWhite marbleWhite = new MarbleWhite();
        var marblelist = new ArrayList<Marble>();
        //test
        marblelist.add(marbleWhite);
        var deposit = new HashMap<>(playerBoard.getDepositInstance().getContent());
        playerBoard.tryAddMarbles(marblelist);
        for (var resource :
                deposit.keySet()) {
            if(resource != Resource.Coins)
                assertEquals(deposit.get(resource),playerBoard.getDepositInstance().getContent().get(resource));
            else {
                assertEquals(Integer.valueOf(deposit.get(resource) + 1),playerBoard.getDepositInstance().getContent().get(resource));
            }
        }
    }
}