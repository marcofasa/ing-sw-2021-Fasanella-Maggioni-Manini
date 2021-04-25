package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.ResultIterator;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CardDevelopmentTest {


    @Test
    void testCloneConstructor() {

        //Check that all attributes of clone hold the same values as test, but pointers are different.

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {

                    CardDevelopment test = new CardDevelopment(0, 0, 0);
                    CardDevelopment clone = new CardDevelopment(test);

                    assertSame(test.getCardType(), clone.getCardType());
                    assertSame(test.getCardLevel(), clone.getCardLevel());
                    assertSame(test.getVictoryPoints(), clone.getVictoryPoints());

                    for (Resource res : Resource.values()) {

                        assertSame(test.getCardCosts().get(res), test.getCardCosts().get(res));
                        assertSame(test.getProductionInput().get(res), test.getProductionInput().get(res));
                        assertSame(test.getProductionOutput().get(res), test.getProductionOutput().get(res));

                    }

                    assertSame(test.getNumberOfRedResourceProduced(), clone.getNumberOfRedResourceProduced());

                    assertNotSame(test, clone);
                }
            }
        }
    }

    @Test
    void testCanActivateProduction() {

        CardDevelopment firstCard = new CardDevelopment(0, 0, 0);
        CardDevelopment secondCard = new CardDevelopment(0, 0, 1);
        CardDevelopment thirdCard = new CardDevelopment(0, 0, 2);
        CardDevelopment fourthCard = new CardDevelopment(0, 0, 3);


        ArrayList<String> nicknames = new ArrayList<String>();
        nicknames.add("nickname");

        // Setup gameboard with 1 player
        GameTable board = new GameTable(nicknames);

        PlayerBoard player = board.getPlayerByIndex(board.getNumberOfPlayers() - 1);
        assertNotNull(player);

        // Create resource maps to add to deposit and strongbox
        HashMap<Resource, Integer> depositMap = new HashMap<>();
        depositMap.put(Resource.Coins, 1);
        depositMap.put(Resource.Servants, 1);
        depositMap.put(Resource.Shields, 0);
        depositMap.put(Resource.Stones, 0);

        HashMap<Resource, Integer> strongboxMap = new HashMap<>();
        strongboxMap.put(Resource.Coins, 0);
        strongboxMap.put(Resource.Servants, 1);
        strongboxMap.put(Resource.Shields, 0);
        strongboxMap.put(Resource.Stones, 0);


        // Add resources manually to player's deposit and strongbox
        assertTrue(player.getDepositInstance().tryAdd(depositMap));

        assertTrue(player.getStrongboxInstance().tryAdd(strongboxMap));

        //Check if productions can be activated

        //Has enough resources in deposit
        assertTrue(firstCard.canActivateProduction(player));

        //Does not have resources
        assertFalse(secondCard.canActivateProduction(player));

        //Has enough in deposit + strongbox
        assertTrue(thirdCard.canActivateProduction(player));

        //Does not have resources
        assertFalse(fourthCard.canActivateProduction(player));
    }

    @Test
    void testActivateProduction() {

        CardDevelopment test = new CardDevelopment(0, 0, 0);

        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("test");

        GameTable table = new GameTable(nicknames);
        PlayerBoard player = table.getPlayerByIndex(0);
        assertNotNull(player);

        // Create resource maps to add to deposit and strongbox
        HashMap<Resource, Integer> depositMap = new HashMap<>();
        depositMap.put(Resource.Coins, 1);
        depositMap.put(Resource.Servants, 1);
        depositMap.put(Resource.Shields, 0);
        depositMap.put(Resource.Stones, 0);

        HashMap<Resource, Integer> strongboxMap = new HashMap<>();
        strongboxMap.put(Resource.Coins, 0);
        strongboxMap.put(Resource.Servants, 1);
        strongboxMap.put(Resource.Shields, 0);
        strongboxMap.put(Resource.Stones, 1);

        // Add resources manually
        assertTrue(player.getDepositInstance().tryAdd(depositMap));
        player.getStrongboxInstance().tryAdd(strongboxMap);

        assertTrue(test.canActivateProduction(player));
        test.activateProduction(player);

        //Check deposit consistency
        assertEquals(0, (int) player.getDepositInstance().getContent().get(Resource.Coins));
        assertEquals(1, (int) player.getDepositInstance().getContent().get(Resource.Servants));
        assertEquals(0, (int) player.getDepositInstance().getContent().get(Resource.Shields));
        assertEquals(0, (int) player.getDepositInstance().getContent().get(Resource.Stones));

        //Check strongbox consistency
        assertEquals(0, player.getStrongboxInstance().getContent().get(Resource.Coins));
        assertEquals(1, player.getStrongboxInstance().getContent().get(Resource.Servants));
        assertEquals(0, player.getStrongboxInstance().getContent().get(Resource.Shields));
        assertEquals(1, player.getStrongboxInstance().getContent().get(Resource.Stones));


        assertEquals(1, table.getFaithTrailInstance().getPosition(player));

        //System.out.println("DEUBG");

    }

}