package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameTable;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TurnControllerTest {

    @Test
    void constructorTest() {

        String nickname = "test";

        GameTable gameTable = new GameTable(true);
        gameTable.addPlayer(nickname);
        gameTable.startGame();

        TurnController turnContr = new TurnController(gameTable, new Controller());

        System.out.println("debug");
    }

    @Test
    void advanceTurn() {
    }

    @Test
    void isActivePlayer() {
    }
}