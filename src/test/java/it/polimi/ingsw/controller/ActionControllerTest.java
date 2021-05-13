package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ActionControllerTest {

    @Test
    void buyAndPlaceDevCard() {


        GameTable gameTable = new GameTable(true);
        String nickname = "test";

        ActionController actionController = new ActionController(gameTable);

        gameTable.addPlayer(nickname);
        gameTable.startGame();

        PlayerBoard player = gameTable.getActivePlayer();

        HashMap<Resource, Integer> temp = new HashMap<>();

        temp.put(Resource.Coins, 0);
        temp.put(Resource.Stones, 0);
        temp.put(Resource.Shields, 10);
        temp.put(Resource.Servants, 2);

        player.getStrongboxInstance().tryAdd(temp);

        assertThrows(InvalidCardDevelopmentPlacementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                actionController.buyAndPlaceDevCard(player, 1, 0, 1);
            }
        });

        assertThrows(InvalidSlotIndexException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                actionController.buyAndPlaceDevCard(player, 0, 0, 5);
            }
        });

        assertThrows(InvalidSlotIndexException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                actionController.buyAndPlaceDevCard(player, 0, 0, -1);
            }
        });

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                actionController.buyAndPlaceDevCard(player, 0, 0, 0);
            }
        });

        assertThrows(NotEnoughResourcesException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                actionController.buyAndPlaceDevCard(player, 1, 1, 0);
            }
        });
    }

    @Test
    void useMarket() {

        Scanner scanner = new Scanner(System.in);

        GameTable gameTable = new GameTable(true);
        String nickname = "test1";

        ActionController actionController = new ActionController(gameTable);

        gameTable.addPlayer(nickname);
        gameTable.startGame();

        PlayerBoard player = gameTable.getActivePlayer();

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                actionController.useMarket(player, 5, "row");
            }
        });

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                actionController.useMarket(player, 5, "column");
            }
        });

        assertThrows(Exception.class, () -> {
           actionController.useMarket(player, 1, "invalidArg");
        });

        //assertTrue(actionController.useMarket(player, 2, "row"));

        HashMap<Resource, Integer> temp = new HashMap<>();
        HashMap<Resource, Integer> discardSelection = new HashMap<>();

        for (int i = 0; i < 5; i++) {

            temp = actionController.useMarket(player, 1, "row");
            temp = actionController.useMarket(player, 1, "column");

            System.out.println(temp);

            if (temp != null) {

                do {
                    for (Resource res : Resource.values()) {

                        discardSelection.put(res, new Random().nextInt(4));
                    }
                    System.out.println("Trying to discard : " + discardSelection);

                } while (actionController.discardResources(player, discardSelection) != null);
            }
        }

        System.out.println("debug");
    }

    @Test
    void activateProductionPowers() {

        /*
        This test is based on internal randomness : to test this method, it's sufficient
        to test the tryActivateProductions method in PlayerBoard class
         */
    }

    @Test
    void activateLeaderCard() {

        /*
        This test is based on internal randomness : to test this method, it's sufficient
        to test the leader card activation process!
         */

    }
}