package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.GameTable;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.Game;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class InitialSelectionControllerTest {

    @Test
    /*
     This test runs through the initial selection process for 4 players, using input data correctly.
     This test does not cover improper selections.
     */
    void assignInitialBenefits() {

        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("test1");
        nicknames.add("test2");
        nicknames.add("test3");
        nicknames.add("test4");

        GameTable gameTable = new GameTable(false);

        for (String nickname : nicknames) {
            gameTable.addPlayer(nickname);
        }

        Controller controller = new Controller(new Game(false, new Server(false)), gameTable);
        InitialSelectionController initSelController = controller.getInitialSelectionController();
        TurnController turnController = controller.getTurnController();

        gameTable.startGame();

        ArrayList<PlayerBoard> boards = gameTable.getPlayerBoards();

        // First player selections
        ArrayList<CardLeader> leaderSelection1 = new ArrayList<>();
        leaderSelection1.add(boards.get(0).getCardsLeaderBeforeSelecting().get(0));
        leaderSelection1.add(boards.get(0).getCardsLeaderBeforeSelecting().get(2));

        initSelController.assignInitialBenefits(boards.get(0), leaderSelection1, null, null);

        //Advance turn
        turnController.advanceTurn();

        // Second player selections
        ArrayList<CardLeader> leaderSelection2 = new ArrayList<>();
        leaderSelection2.add(boards.get(1).getCardsLeaderBeforeSelecting().get(1));
        leaderSelection2.add(boards.get(1).getCardsLeaderBeforeSelecting().get(2));

        initSelController.assignInitialBenefits(boards.get(1), leaderSelection2, Resource.Shields, null);

        turnController.advanceTurn();

        // Third player selections
        ArrayList<CardLeader> leaderSelection3 = new ArrayList<>();
        leaderSelection3.add(boards.get(2).getCardsLeaderBeforeSelecting().get(0));
        leaderSelection3.add(boards.get(2).getCardsLeaderBeforeSelecting().get(3));

        initSelController.assignInitialBenefits(boards.get(2), leaderSelection3, Resource.Coins, null);

        turnController.advanceTurn();

        // Fourth player selections
        ArrayList<CardLeader> leaderSelection4 = new ArrayList<>();
        leaderSelection4.add(boards.get(3).getCardsLeaderBeforeSelecting().get(0));
        leaderSelection4.add(boards.get(3).getCardsLeaderBeforeSelecting().get(3));

        initSelController.assignInitialBenefits(boards.get(3), leaderSelection4, Resource.Servants, Resource.Stones);

        turnController.advanceTurn();

        System.out.println("debug");
    }
}