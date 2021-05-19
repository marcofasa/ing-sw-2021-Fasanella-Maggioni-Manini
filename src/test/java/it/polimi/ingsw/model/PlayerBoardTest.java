package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PlayerBoardTest {

    private PlayerBoard newPlayerBoard(){
        var players = new ArrayList<String>();
        players.add("uno");
        GameTable gametable = new GameTable(players);
        return gametable.getPlayerByIndex(0);
    }

    @Test
    public void getNickname() {
    }

    @Test
    public void marketUsage() {
        ArrayList<String> players = new ArrayList<>();
        players.add("uno");
        players.add("due");
        GameTable gametable = new GameTable(players);
        PlayerBoard playerBoard = gametable.getPlayerByIndex(0);
        playerBoard.getAllDevelopmentCards();
        assertSame(playerBoard.getAllDevelopmentCards().size(), 0);
        ArrayList<Marble> marbles = new ArrayList<>(Arrays.asList(new MarbleNormal(Resource.Coins),new MarbleNormal(Resource.Coins), new MarbleNormal(Resource.Coins)));
        HashMap<Resource, Integer> resource = playerBoard.consumeMarbles(marbles);
        for (Resource res :
                Resource.values()) {
            if (res != Resource.Coins){
                assertEquals(resource.get(res), new Integer(0));
            } else {
                assertEquals(resource.get(res), new Integer(3));
            }
        }
        playerBoard.tryAddResources(resource);
        for (Resource res :
                Resource.values()) {
            if (res != Resource.Coins){
                assertEquals(playerBoard.getDepositInstance().content.get(res), new Integer(0));
            } else {
                assertEquals(playerBoard.getDepositInstance().content.get(res), new Integer(3));
            }
        }
        ArrayList<Marble> marbles2 = new ArrayList<>(Arrays.asList(new MarbleNormal(Resource.Coins)));
        HashMap<Resource, Integer> resource2 = playerBoard.consumeMarbles(marbles2);
        assertTrue(playerBoard.hasResources(resource2));
        var resources3 = playerBoard.tryAddResources(resource2);
        for (Resource res :
                Resource.values()) {
            if (res != Resource.Coins){
                assertEquals(resources3.get(res), new Integer(0));
            } else {
                assertEquals(resources3.get(res), new Integer(1));
            }
        }
        assertEquals(gametable.getFaithTrailInstance().getPosition(gametable.getPlayerByIndex(1)), 0);
        assertEquals(gametable.getFaithTrailInstance().getPosition(gametable.getPlayerByIndex(0)), 0);
        CardLeader cardLeader = new CardLeaderFactory().produce(CardLeaderType.Deposit, Resource.Coins);
        var cardsLeaderBeforeSelecting = playerBoard.getCardsLeaderBeforeSelecting();
        playerBoard.selectCardsLeader(playerBoard.getCardsLeaderBeforeSelecting().get(0), playerBoard.getCardsLeaderBeforeSelecting().get(1));
        assertArrayEquals(Arrays.stream(Arrays.stream((cardsLeaderBeforeSelecting.toArray())).toArray()).limit(2).toArray(), playerBoard.getCardsLeader().toArray());
        // cardLeader.draw(playerBoard);
        assertThrows(CardLeaderWrongOwnerException.class, () -> cardLeader.activate(playerBoard));
    }

    @Test
    public void marketUsage2() {
        ArrayList<String> players = new ArrayList<>();
        players.add("uno");
        players.add("due");
        GameTable gametable = new GameTable(players);
        PlayerBoard playerBoard = gametable.getPlayerByIndex(0);
        playerBoard.getAllDevelopmentCards();
        assertSame(playerBoard.getAllDevelopmentCards().size(), 0);
        ArrayList<Marble> marbles = new ArrayList<>(Arrays.asList(new MarbleNormal(Resource.Coins),new MarbleNormal(Resource.Coins), new MarbleNormal(Resource.Coins)));
        HashMap<Resource, Integer> resource = playerBoard.consumeMarbles(marbles);
        for (Resource res :
                Resource.values()) {
            if (res != Resource.Coins){
                assertEquals(resource.get(res), new Integer(0));
            } else {
                assertEquals(resource.get(res), new Integer(3));
            }
        }
        playerBoard.tryAddResources(resource);
        for (Resource res :
                Resource.values()) {
            if (res != Resource.Coins){
                assertEquals(playerBoard.getDepositInstance().content.get(res), new Integer(0));
            } else {
                assertEquals(playerBoard.getDepositInstance().content.get(res), new Integer(3));
            }
        }
        ArrayList<Marble> marbles2 = new ArrayList<>(Arrays.asList(new MarbleNormal(Resource.Coins)));
        HashMap<Resource, Integer> resource2 = playerBoard.consumeMarbles(marbles2);
        assertTrue(playerBoard.hasResources(resource2));
        var resources3 = playerBoard.tryAddResources(resource2);
        for (Resource res :
                Resource.values()) {
            if (res != Resource.Coins){
                assertEquals(resources3.get(res), new Integer(0));
            } else {
                assertEquals(resources3.get(res), new Integer(1));
            }
        }
        assertEquals(gametable.getFaithTrailInstance().getPosition(gametable.getPlayerByIndex(1)), 0);
        assertEquals(gametable.getFaithTrailInstance().getPosition(gametable.getPlayerByIndex(0)), 0);
        CardLeader cardLeader = new CardLeaderFactory().produce(CardLeaderType.Deposit, Resource.Coins);
        var cardsLeaderBeforeSelecting = playerBoard.getCardsLeaderBeforeSelecting();
        playerBoard.selectCardsLeader(playerBoard.getCardsLeaderBeforeSelecting().get(0), playerBoard.getCardsLeaderBeforeSelecting().get(1));
        assertArrayEquals(Arrays.stream(Arrays.stream((cardsLeaderBeforeSelecting.toArray())).toArray()).limit(2).toArray(), playerBoard.getCardsLeader().toArray());
        cardLeader.draw(playerBoard);
        assertThrows(CardLeaderRequirementsNotMetException.class, () -> cardLeader.activate(playerBoard));
    }

    @Test
    public void marketUsage3() {
        ArrayList<String> players = new ArrayList<>();
        players.add("uno");
        players.add("due");
        GameTable gametable = new GameTable(players);
        PlayerBoard playerBoard = gametable.getPlayerByIndex(0);
        playerBoard.getAllDevelopmentCards();
        assertSame(playerBoard.getAllDevelopmentCards().size(), 0);
        ArrayList<Marble> marbles = new ArrayList<>(Arrays.asList(new MarbleNormal(Resource.Coins),new MarbleNormal(Resource.Coins), new MarbleNormal(Resource.Coins)));
        HashMap<Resource, Integer> resource = playerBoard.consumeMarbles(marbles);
        for (Resource res :
                Resource.values()) {
            if (res != Resource.Coins){
                assertEquals(resource.get(res), new Integer(0));
            } else {
                assertEquals(resource.get(res), new Integer(3));
            }
        }
        playerBoard.tryAddResources(resource);
        for (Resource res :
                Resource.values()) {
            if (res != Resource.Coins){
                assertEquals(playerBoard.getDepositInstance().content.get(res), new Integer(0));
            } else {
                assertEquals(playerBoard.getDepositInstance().content.get(res), new Integer(3));
            }
        }
        ArrayList<Marble> marbles2 = new ArrayList<>(Arrays.asList(new MarbleNormal(Resource.Coins), new MarbleNormal(Resource.Coins)));
        HashMap<Resource, Integer> resource2 = playerBoard.consumeMarbles(marbles2);
        assertTrue(playerBoard.hasResources(resource2));
        var resources3 = playerBoard.tryAddResources(resource2);
        for (Resource res :
                Resource.values()) {
            if (res != Resource.Coins){
                assertEquals(resources3.get(res), new Integer(0));
            } else {
                assertEquals(resources3.get(res), new Integer(2));
            }
        }
        assertEquals(gametable.getFaithTrailInstance().getPosition(gametable.getPlayerByIndex(1)), 0);
        assertEquals(gametable.getFaithTrailInstance().getPosition(gametable.getPlayerByIndex(0)), 0);
        CardLeader cardLeader = new CardLeaderFactory().produce(CardLeaderType.Deposit, Resource.Coins);
        var cardsLeaderBeforeSelecting = playerBoard.getCardsLeaderBeforeSelecting();
        playerBoard.selectCardsLeader(playerBoard.getCardsLeaderBeforeSelecting().get(0), playerBoard.getCardsLeaderBeforeSelecting().get(1));
        assertArrayEquals(Arrays.stream(Arrays.stream((cardsLeaderBeforeSelecting.toArray())).toArray()).limit(2).toArray(), playerBoard.getCardsLeader().toArray());
        cardLeader.draw(playerBoard);
        playerBoard.getStrongboxInstance().addResource(Resource.Shields, 5);
        cardLeader.activate(playerBoard);
        resources3 = playerBoard.tryAddResources(resource2);
        assertNull(resources3);
    }

    @Test
    public void selectCardsLeader() {
        PlayerBoard playerBoard = newPlayerBoard();
        var cards = playerBoard.getCardsLeaderBeforeSelecting();
        assertEquals(0, playerBoard.getCardsLeader().size());
        playerBoard.selectCardsLeader(playerBoard.getCardsLeaderBeforeSelecting().get(1), playerBoard.getCardsLeaderBeforeSelecting().get(0));
        assertArrayEquals(playerBoard.getCardsLeader().toArray(), Arrays.asList(playerBoard.getCardsLeaderBeforeSelecting().get(1), playerBoard.getCardsLeaderBeforeSelecting().get(0)).toArray());
    }

    @Test
    public void getWhiteEffect() {
    }

    @Test
    public void setWhiteEffect() {
    }

    @Test
    public void moveFaith() {
    }

    @Test
    public void getCardLeaderProductionOutput() {
    }

    @Test
    public void hasResources() {
    }

    @Test
    public void discountResource() {
    }

    @Test
    public void getDepositLeaderCardInstance() {
    }

    @Test
    public void isFirst() {
    }

    @Test
    public void getPlayerState() {
    }

    @Test
    public void setPlayerState() {
    }

    @Test
    public void activateLeaderProduction() {
    }

    @Test
    public void getVictoryPoints() {
    }

    @Test
    public void buyCardDevelopmentCardFromMarket() {
    }

    @Test
    public void placeCardDevelopmentCardOnBoard() {
    }

    @Test
    public void tryActivateProductions() {
    }
}