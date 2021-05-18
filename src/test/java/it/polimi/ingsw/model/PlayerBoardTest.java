package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PlayerBoardTest {

    private PlayerBoard newPlayerBoard(){
        GameTable gametable = new GameTable(new ArrayList<>().add("uno"));
        return new PlayerBoard("uno", true, PlayerState.PLAYING, gametable);
    }

    @Test
    public void getNickname() {
    }

    @Test
    public void getAllDevelopmentCards() {
        PlayerBoard playerBoard = newPlayerBoard();
        playerBoard.getAllDevelopmentCards();
        assertSame(playerBoard.getAllDevelopmentCards().size(), 0);
        ArrayList<Marble> marbles = new ArrayList<>(Arrays.asList(new MarbleNormal(Resource.Coins),new MarbleNormal(Resource.Coins), new MarbleNormal(Resource.Coins)));
        HashMap<Resource, Integer> resource = playerBoard.consumeMarbles(marbles);
        for (Resource res :
                Resource.values()) {
            if (res != Resource.Coins){
                assertEquals(resource.get(res), new Integer(0));
            } else {
                assertEquals(resource.get(res), new Integer(4));
            }
        }
    }

    @Test
    void getMarketRow() {
    }

    @Test
    void getTempDeposit() {
    }

    @Test
    void getMarketCol() {
    }

    @Test
    void getCardsLeader() {
    }

    @Test
    void getCardsLeaderBeforeSelecting() {
    }

    @Test
    void addCardLeaderBeforeSelecting() {
    }

    @Test
    void getStrongboxInstance() {
    }

    @Test
    void getDepositInstance() {
    }

    @Test
    void getCardDevelopmentSlotByIndex() {
    }

    @Test
    void discardCardLeaderController() {
    }

    @Test
    void discardCardLeader() {
    }

    @Test
    void addToTemporaryDeposit() {
    }

    @Test
    void discardFromTemporaryDeposit() {
    }

    @Test
    void consumeMarbles() {
    }

    @Test
    void tryAddResources() {
    }

    @Test
    void tryAddMarbles() {
    }

    @Test
    void activateCardLeader() {
    }

    @Test
    void drawCardLeaderFromDeck() {
    }

    @Test
    void selectCardsLeader() {
    }

    @Test
    void getWhiteEffect() {
    }

    @Test
    void setWhiteEffect() {
    }

    @Test
    void moveFaith() {
    }

    @Test
    void getCardLeaderProductionOutput() {
    }

    @Test
    void hasResources() {
    }

    @Test
    void discountResource() {
    }

    @Test
    void getDepositLeaderCardInstance() {
    }

    @Test
    void isFirst() {
    }

    @Test
    void getPlayerState() {
    }

    @Test
    void setPlayerState() {
    }

    @Test
    void activateLeaderProduction() {
    }

    @Test
    void getVictoryPoints() {
    }

    @Test
    void buyCardDevelopmentCardFromMarket() {
    }

    @Test
    void placeCardDevelopmentCardOnBoard() {
    }

    @Test
    void tryActivateProductions() {
    }
}