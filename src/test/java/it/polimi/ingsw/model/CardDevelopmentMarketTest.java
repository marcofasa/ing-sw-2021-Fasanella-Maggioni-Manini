package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CardDevelopmentMarketTest {


    @BeforeEach
    void before() {
        CardDevelopmentMarket market = new CardDevelopmentMarket();
    }

    @Test
    void getNUMBER_OF_COLUMNS() {

        Assertions.assertEquals(4, new CardDevelopmentMarket().getNUMBER_OF_COLUMNS());
    }

    @Test
    void getNUMBER_OF_ROWS() {

        Assertions.assertEquals(3, new CardDevelopmentMarket().getNUMBER_OF_ROWS());
    }

    @Test
    void getMarket() {

        CardDevelopmentMarket market = new CardDevelopmentMarket();

        Assertions.assertNotNull(market);

        System.out.println("DEBUG : Set breakpoint at this line, check market internal references by hand");

    }

    @Test
    void buyCardFromStack() {

        /*
        This test creates a GameTable with a single player, deposits 100 of each resource in his deposit and buys
        a card from the market.
        The development card placement logic is still missing.
        */

        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("test");

        GameTable testTable = new GameTable(nicknames);
        PlayerBoard testBoard = testTable.getPlayerByIndex(0);

        for (Resource res : Resource.values()) {
            testBoard.getDepositInstance().addResource(res, 100);
        }

        CardDevelopmentMarket market = testTable.getCardDevelopmentMarketInstance();

        market.buyCardFromStack(testBoard, 0, 0);

        Assertions.assertEquals(3, market.getMarket()[0][0].getCards().size());


    }

    @Test
    void discardCards() {

        CardDevelopmentMarket market = new CardDevelopmentMarket();

        //Discard Blue twice

        int blue = CardDevelopmentType.Blue.ordinal();

        market.discardCards(CardDevelopmentType.Blue);
        Assertions.assertEquals(2, market.getMarket()[0][blue].getCards().size());

        market.discardCards(CardDevelopmentType.Blue);


        //Pop a purple and discard twice

        int purple = CardDevelopmentType.Purple.ordinal();

        market.getMarket()[0][purple].pop();
        Assertions.assertEquals(3, market.getMarket()[0][purple].getCards().size());

        market.discardCards(CardDevelopmentType.Purple);
        Assertions.assertEquals(1, market.getMarket()[0][purple].getCards().size());

        market.discardCards(CardDevelopmentType.Purple);

        //Discard Yellow 6 times

        int yellow = CardDevelopmentType.Yellow.ordinal();

        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);

        Assertions.assertEquals(0, market.getMarket()[0][yellow].getCards().size());
        Assertions.assertEquals(0, market.getMarket()[1][yellow].getCards().size());
        Assertions.assertEquals(0, market.getMarket()[2][yellow].getCards().size());

        //Pop Green once and discard 6 times

        int green = CardDevelopmentType.Green.ordinal();

        market.getMarket()[0][green].pop();
        market.discardCards(CardDevelopmentType.Green);
        market.discardCards(CardDevelopmentType.Green);
        market.discardCards(CardDevelopmentType.Green);
        market.discardCards(CardDevelopmentType.Green);
        market.discardCards(CardDevelopmentType.Green);
        market.discardCards(CardDevelopmentType.Green);

        for (int i = 0; i < market.getNUMBER_OF_ROWS(); i++) {
            for (int j = 0; j < market.getNUMBER_OF_COLUMNS(); j++) {

                if (i == 0 && j == blue) Assertions.assertEquals(0, market.getMarket()[i][j].getCards().size());

                else if (i == 0 && j == purple) Assertions.assertEquals(0, market.getMarket()[i][j].getCards().size());

                else if (i == 1 && j == purple) Assertions.assertEquals(3, market.getMarket()[i][j].getCards().size());

                else if (j == yellow) {
                    Assertions.assertEquals(0, market.getMarket()[i][yellow].getCards().size());
                }

                else if (j == green) {
                    Assertions.assertEquals(0, market.getMarket()[i][green].getCards().size());
                }

                else Assertions.assertEquals(4, market.getMarket()[i][j].getCards().size());
            }
        }
    }

    @Test
    void isColumnEmpty() {

        CardDevelopmentMarket market = new CardDevelopmentMarket();

        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);

        Assertions.assertTrue(market.isColumnEmpty(CardDevelopmentType.Blue));
        Assertions.assertFalse(market.isColumnEmpty(CardDevelopmentType.Yellow));
        Assertions.assertFalse(market.isColumnEmpty(CardDevelopmentType.Purple));
        Assertions.assertFalse(market.isColumnEmpty(CardDevelopmentType.Green));


        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);

        Assertions.assertTrue(market.isColumnEmpty(CardDevelopmentType.Blue));

        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);

        Assertions.assertTrue(market.isColumnEmpty(CardDevelopmentType.Blue));
        Assertions.assertFalse(market.isColumnEmpty(CardDevelopmentType.Yellow));
        Assertions.assertFalse(market.isColumnEmpty(CardDevelopmentType.Purple));
        Assertions.assertFalse(market.isColumnEmpty(CardDevelopmentType.Green));
    }
}