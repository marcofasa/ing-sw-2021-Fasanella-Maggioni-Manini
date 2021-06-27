package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnControllerTest {

    @Test
    void constructorTest() {

        String nickname1 = "test1";
        String nickname2 = "test2";
        String nickname3 = "test3";
        String nickname4 = "test4";

        GameTable gameTable = new GameTable(false);
        gameTable.addPlayer(nickname1);
        gameTable.addPlayer(nickname2);
        gameTable.addPlayer(nickname3);
        gameTable.addPlayer(nickname4);
        gameTable.startGame();

        TurnController turnContr = new TurnController(gameTable);

        System.out.println("debug");
    }

    @Test
    void advanceTurn() {

        String nickname1 = "test1";
        String nickname2 = "test2";
        String nickname3 = "test3";
        String nickname4 = "test4";

        //Single player game : testing transitions between the only PlayerBoard and Lorenzo
        GameTable gameTable1 = new GameTable(true);
        gameTable1.addPlayer(nickname1);
        gameTable1.startGame();

        TurnController turnController = new TurnController(gameTable1);
        for (int i = 0; i < 10000; i++) {

            if (i % 2 == 0) {

                assertTrue(turnController.isActivePlayer(gameTable1.getPlayerByIndex(0)));
                assertFalse(turnController.isLorenzoActive());

            } else {

                assertFalse(turnController.isActivePlayer(gameTable1.getPlayerByIndex(0)));
                assertTrue(turnController.isLorenzoActive());

            }

            turnController.advanceTurn();
        }

        //4 player game: testing transitions between PlayerBoards
        GameTable gameTable2 = new GameTable(false);
        gameTable2.addPlayer(nickname1);
        gameTable2.addPlayer(nickname2);
        gameTable2.addPlayer(nickname3);
        gameTable2.addPlayer(nickname4);
        gameTable2.startGame();

        TurnController turnController2 = new TurnController(gameTable2);
        int playerNum = gameTable2.getNumberOfPlayers();

        for (int i = 0; i < 10000; i++) {

            if (i % playerNum == 0) {

                assertTrue(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(0)));
                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(1)));
                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(2)));
                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(3)));

            } else if (i % playerNum == 1) {

                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(0)));
                assertTrue(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(1)));
                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(2)));
                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(3)));

            } else if (i % playerNum == 2) {

                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(0)));
                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(1)));
                assertTrue(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(2)));
                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(3)));

            } else {

                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(0)));
                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(1)));
                assertFalse(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(2)));
                assertTrue(turnController2.isActivePlayer(gameTable2.getPlayerByIndex(3)));

            }

            turnController2.advanceTurn();
        }
    }
}