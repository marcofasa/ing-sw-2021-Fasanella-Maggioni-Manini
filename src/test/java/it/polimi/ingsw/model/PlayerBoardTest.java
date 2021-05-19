package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void getMarketRow() {
    }

    @Test
    public void getTempDeposit() {
    }

    @Test
    public void getMarketCol() {
    }

    @Test
    public void getCardsLeader() {
    }

    @Test
    public void getCardsLeaderBeforeSelecting() {
    }

    @Test
    public void addCardLeaderBeforeSelecting() {
    }

    @Test
    public void getStrongboxInstance() {
    }

    @Test
    public void getDepositInstance() {
    }

    @Test
    public void getCardDevelopmentSlotByIndex() {
    }

    @Test
    public void discardCardLeaderController() {
    }

    @Test
    public void discardCardLeader() {
    }

    @Test
    public void addToTemporaryDeposit() {
    }

    @Test
    public void activateCardLeader() {
    }

    @Test
    public void drawCardLeaderFromDeck() {
    }

    @Test
    public void selectCardsLeader() {
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