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
//        assertArrayEquals(playerBoard.getCardsLeader().toArray(), Arrays.asList(playerBoard.getCardsLeaderBeforeSelecting().get(1), playerBoard.getCardsLeaderBeforeSelecting().get(0)).toArray());
    }

    @Test
    public void cardLeaderProduction() throws InvalidCardDevelopmentPlacementException, InvalidSlotIndexException, FullSlotException {
        PlayerBoard playerBoard = newPlayerBoard();
        CardLeader cardLeader = new CardLeaderFactory().produce(CardLeaderType.Production, Resource.Coins);
        cardLeader.draw(playerBoard);
        assertThrows( CardLeaderRequirementsNotMetException.class , () -> cardLeader.activate(playerBoard));
        playerBoard.getStrongboxInstance().addResource(Resource.Coins, 10);
        playerBoard.getStrongboxInstance().addResource(Resource.Servants, 10);
        playerBoard.getStrongboxInstance().addResource(Resource.Shields, 10);
        playerBoard.getStrongboxInstance().addResource(Resource.Stones, 10);
        HashMap<Resource, Integer> resourceIntegerHashMap = new HashMap<>();
        resourceIntegerHashMap.put(Resource.Coins, 10);
        resourceIntegerHashMap.put(Resource.Servants, 10);
        resourceIntegerHashMap.put(Resource.Shields, 10);
        resourceIntegerHashMap.put(Resource.Stones, 10);
        assertTrue(playerBoard.hasResources(resourceIntegerHashMap));
        assertThrows(CardLeaderRequirementsNotMetException.class, () -> cardLeader.activate(playerBoard));
        CardDevelopment cardDevelopment = new CardDevelopment(0,0,1);
        CardDevelopment cardDevelopment2 = new CardDevelopment(0,0,1);
        playerBoard.placeCardDevelopmentCardOnBoard(cardDevelopment, 0);
        playerBoard.placeCardDevelopmentCardOnBoard(cardDevelopment2, 1);
        assertThrows(CardLeaderRequirementsNotMetException.class, () -> cardLeader.activate(playerBoard));
        CardDevelopment cardDevelopment3 = new CardDevelopment(1,0,1);
        assertThrows(InvalidCardDevelopmentPlacementException.class, () -> playerBoard.placeCardDevelopmentCardOnBoard(cardDevelopment3, 2));
        assertThrows(InvalidSlotIndexException.class, () -> playerBoard.placeCardDevelopmentCardOnBoard(cardDevelopment3, 3));
        playerBoard.placeCardDevelopmentCardOnBoard(cardDevelopment3, 1);
        assertThrows(IllegalArgumentException.class, () -> cardLeader.activate(playerBoard));
        playerBoard.activateLeaderProduction(cardLeader, Resource.Coins);
        resourceIntegerHashMap.put(Resource.Coins, 11);
        assertTrue(playerBoard.hasResources(resourceIntegerHashMap));
        resourceIntegerHashMap.put(Resource.Coins, 12);
        assertFalse(playerBoard.hasResources(resourceIntegerHashMap));
    }

    @Test
    public void cardLeaderWhiteMarble() throws InvalidCardDevelopmentPlacementException, InvalidSlotIndexException, FullSlotException {
        PlayerBoard playerBoard = newPlayerBoard();
        CardLeader cardLeader = new CardLeaderFactory().produce(CardLeaderType.WhiteMarble, Resource.Coins);
        cardLeader.draw(playerBoard);
        assertThrows( CardLeaderRequirementsNotMetException.class , () -> cardLeader.activate(playerBoard));
        assertThrows(CardLeaderRequirementsNotMetException.class, () -> cardLeader.activate(playerBoard));
        CardDevelopment cardDevelopment = new CardDevelopment(0,0,1);
        CardDevelopment cardDevelopment2 = new CardDevelopment(0,0,9);
        CardDevelopment cardDevelopment3 = new CardDevelopment(0,0,9);
        playerBoard.placeCardDevelopmentCardOnBoard(cardDevelopment, 1);
        assertThrows(InvalidCardDevelopmentPlacementException.class, () -> playerBoard.placeCardDevelopmentCardOnBoard(cardDevelopment2, 1));
        playerBoard.placeCardDevelopmentCardOnBoard(cardDevelopment2, 0);
        assertThrows(CardLeaderRequirementsNotMetException.class, () -> cardLeader.activate(playerBoard));
        playerBoard.placeCardDevelopmentCardOnBoard(cardDevelopment2, 2);
        cardLeader.activate(playerBoard);
        assertEquals(playerBoard.getWhiteEffect(), Resource.Coins);
        MarbleWhite marbleWhite = new MarbleWhite();
        ArrayList<Marble> marbles = new ArrayList<>();
        marbles.add(marbleWhite);
        HashMap<Resource, Integer> resourceIntegerHashMap = new HashMap<>();
        resourceIntegerHashMap.put(Resource.Coins, 1);
        assertFalse(playerBoard.hasResources(resourceIntegerHashMap));
        var resources = playerBoard.consumeMarbles(marbles);
        playerBoard.tryAddResources(resources);
        assertTrue(playerBoard.hasResources(resourceIntegerHashMap));
    }

    @Test
    public void cardLeaderDeposit() throws InvalidCardDevelopmentPlacementException, InvalidSlotIndexException, FullSlotException {
        PlayerBoard playerBoard = newPlayerBoard();
        CardLeader cardLeader = new CardLeaderFactory().produce(CardLeaderType.Deposit, Resource.Coins);
        cardLeader.draw(playerBoard);
        assertThrows( CardLeaderRequirementsNotMetException.class , () -> cardLeader.activate(playerBoard));
        playerBoard.getStrongboxInstance().addResource(Resource.Shields, 5);
        HashMap<Resource,Integer> marketResult = new HashMap<>();
        marketResult.put(Resource.Coins, 5);
        assertEquals(marketResult, playerBoard.tryAddResources(marketResult));
        cardLeader.activate(playerBoard);
        assertNull(playerBoard.tryAddResources(marketResult));
    }

    @Test
    public void cardLeaderDeposit2() throws InvalidCardDevelopmentPlacementException, InvalidSlotIndexException, FullSlotException {
        PlayerBoard playerBoard = newPlayerBoard();
        CardLeader cardLeader = new CardLeaderFactory().produce(CardLeaderType.Deposit, Resource.Coins);
        cardLeader.draw(playerBoard);
        assertThrows( CardLeaderRequirementsNotMetException.class , () -> cardLeader.activate(playerBoard));
        playerBoard.getStrongboxInstance().addResource(Resource.Shields, 5);
        HashMap<Resource,Integer> marketResult = new HashMap<>();
        marketResult.put(Resource.Coins, 5);
        assertEquals(marketResult, playerBoard.tryAddResources(marketResult));
        cardLeader.activate(playerBoard);
        marketResult.put(Resource.Coins, 6);
        HashMap<Resource,Integer> marketRemains = new HashMap<>();
        marketRemains.put(Resource.Coins, 4);
        assertEquals(marketRemains, playerBoard.tryAddResources(marketResult));
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