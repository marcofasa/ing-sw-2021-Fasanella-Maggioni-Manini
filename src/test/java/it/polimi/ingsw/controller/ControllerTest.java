package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.NotActivePlayerException;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.GameTable;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.Game;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void advanceTurnSinglePlayer() {

        Boolean debug = true;
        GameTable gameTable = new GameTable(true);
        String nickname = "test";

        gameTable.addPlayer(nickname);

        Game game = new Game(debug, new Server(debug, true));
        Controller controller = new Controller(game, gameTable);

        gameTable.startGame();

        ArrayList<CardLeader> leaderSelection = new ArrayList<>();
        leaderSelection.add(gameTable.getActivePlayer().getCardsLeaderBeforeSelecting().get(0));
        leaderSelection.add(gameTable.getActivePlayer().getCardsLeaderBeforeSelecting().get(1));

        assertDoesNotThrow(() ->
            controller.assignInitialBenefits(
                    nickname,
                    leaderSelection,
                    null,
                    null)
        );

        assertThrows(NotActivePlayerException.class, () -> {
            controller.advanceTurn("wrong nickname");
        });

        /*This NullPointer is thrown because the method executes a sendAll()!*/
        assertThrows(NullPointerException.class, () -> {
            controller.advanceTurn(nickname);
        });

    }

    @Test
    void assignInitialBenefits() {


        Boolean debug = true;
        GameTable gameTable = new GameTable(false);
        String nickname1 = "test1";
        String nickname2 = "test2";

        gameTable.addPlayer(nickname1);
        gameTable.addPlayer(nickname2);

        Game game = new Game(debug, new Server(debug, true));
        Controller controller = new Controller(game, gameTable);

        gameTable.startGame();

        ArrayList<CardLeader> leaderSelection1 = new ArrayList<>();
        leaderSelection1.add(gameTable.getActivePlayer().getCardsLeaderBeforeSelecting().get(0));
        leaderSelection1.add(gameTable.getActivePlayer().getCardsLeaderBeforeSelecting().get(1));

        assertDoesNotThrow(() ->
                controller.assignInitialBenefits(
                        nickname1,
                        leaderSelection1,
                        null,
                        null)
        );

        ArrayList<CardLeader> leaderSelection2 = new ArrayList<>();
        leaderSelection2.add(gameTable.getActivePlayer().getCardsLeaderBeforeSelecting().get(0));
        leaderSelection2.add(gameTable.getActivePlayer().getCardsLeaderBeforeSelecting().get(1));

        assertThrows(NotActivePlayerException.class, () -> {
            controller.assignInitialBenefits(
                    nickname1,
                    leaderSelection1,
                    null,
                    null);
        });

        assertDoesNotThrow(() ->
                controller.assignInitialBenefits(
                        nickname2,
                        leaderSelection2,
                        Resource.Coins,
                        null)
        );
    }

    @Test
    void calculateScores() {

        Boolean debug = true;
        GameTable gameTable = new GameTable(false);
        String nickname1 = "test1";
        String nickname2 = "test2";
        String nickname3 = "test3";
        String nickname4 = "test4";

        gameTable.addPlayer(nickname1);
        gameTable.addPlayer(nickname2);
        gameTable.addPlayer(nickname3);
        gameTable.addPlayer(nickname4);

        Game game = new Game(debug, new Server(debug, true));
        Controller controller = new Controller(game, gameTable);

        gameTable.startGame();

        HashMap<String, Integer> scores = controller.calculateScores();

        for (PlayerBoard board : gameTable.getPlayerBoards()) {
            assertEquals(0, (int) scores.get(board.getNickname()));
        }

    }
}