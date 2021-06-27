package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class CardDevelopmentMarketTest {


    @Test
    public void getNUMBER_OF_COLUMNS() {

        assertEquals(4, new CardDevelopmentMarket().getNUMBER_OF_COLUMNS());
    }

    @Test
    public void getNUMBER_OF_ROWS() {

        assertEquals(3, new CardDevelopmentMarket().getNUMBER_OF_ROWS());
    }

    @Test
    public void getMarket() {

        CardDevelopmentMarket market = new CardDevelopmentMarket();

        assertNotNull(market);

        System.out.println("DEBUG : Set breakpoint at this line, check market internal references by hand");

    }

    @Test
    public void testCloneConstructor() {

        CardDevelopmentMarket cardDevMarket = new CardDevelopmentMarket();
        CardDevelopmentMarket clone = new CardDevelopmentMarket(cardDevMarket);

        assertNotSame(cardDevMarket, clone);

        assertNotSame(cardDevMarket.getMarket()[0][0], clone.getMarket()[0][0]);

        System.out.println("debug");

    }

    @Test
    public void buyCardFromStack() {

        /*
        This test creates a GameTable with a single player, deposits 100 of each resource in his deposit and buys
        a card from the market.
        The method tested assumes that the player has enough resources to buy the card!
        Logic for an invalid payment is implemented in PlayerBoard::buyCardFromDevelopmentMarket
        */

        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("test");

        GameTable testTable = new GameTable(nicknames);
        CardDevelopmentMarket market = testTable.getCardDevelopmentMarketInstance();
        PlayerBoard testBoard = testTable.getPlayerByIndex(0);

        for (Resource res : Resource.values()) {
            testBoard.getDepositInstance().addResource(res, 1);
        }


        market.buyCardFromStack(testBoard, 0, 0);

        assertEquals(3, market.getMarket()[0][0].getCards().size());


    }

    @Test
    public void discardCards() {

        CardDevelopmentMarket market = new CardDevelopmentMarket();

        //Discard Blue twice

        int blue = CardDevelopmentType.Blue.ordinal();

        market.discardCards(CardDevelopmentType.Blue);
        assertEquals(2, market.getMarket()[0][blue].getCards().size());

        market.discardCards(CardDevelopmentType.Blue);


        //Pop a purple and discard twice

        int purple = CardDevelopmentType.Purple.ordinal();

        market.popFromStack(0, purple);
        assertEquals(3, market.getMarket()[0][purple].getCards().size());

        market.discardCards(CardDevelopmentType.Purple);
        assertEquals(1, market.getMarket()[0][purple].getCards().size());

        market.discardCards(CardDevelopmentType.Purple);

        //Discard Yellow 6 times

        int yellow = CardDevelopmentType.Yellow.ordinal();

        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);

        assertEquals(0, market.getMarket()[0][yellow].getCards().size());
        assertEquals(0, market.getMarket()[1][yellow].getCards().size());
        assertEquals(0, market.getMarket()[2][yellow].getCards().size());

        //Pop Green once and discard 6 times

        int green = CardDevelopmentType.Green.ordinal();

        market.popFromStack(0, green);
        market.discardCards(CardDevelopmentType.Green);
        market.discardCards(CardDevelopmentType.Green);
        market.discardCards(CardDevelopmentType.Green);
        market.discardCards(CardDevelopmentType.Green);
        market.discardCards(CardDevelopmentType.Green);
        market.discardCards(CardDevelopmentType.Green);

        for (int i = 0; i < market.getNUMBER_OF_ROWS(); i++) {
            for (int j = 0; j < market.getNUMBER_OF_COLUMNS(); j++) {

                if (i == 0 && j == blue) assertEquals(0, market.getMarket()[i][j].getCards().size());

                else if (i == 0 && j == purple) assertEquals(0, market.getMarket()[i][j].getCards().size());

                else if (i == 1 && j == purple) assertEquals(3, market.getMarket()[i][j].getCards().size());

                else if (j == yellow) {
                    assertEquals(0, market.getMarket()[i][yellow].getCards().size());
                }

                else if (j == green) {
                    assertEquals(0, market.getMarket()[i][green].getCards().size());
                }

                else assertEquals(4, market.getMarket()[i][j].getCards().size());
            }
        }
        var stack = market.popFromStack(1, purple);
    }

    @Test
    public void isColumnEmpty() {

        CardDevelopmentMarket market = new CardDevelopmentMarket();

        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);

        assertTrue(market.isColumnEmpty(CardDevelopmentType.Blue));
        assertFalse(market.isColumnEmpty(CardDevelopmentType.Yellow));
        assertFalse(market.isColumnEmpty(CardDevelopmentType.Purple));
        assertFalse(market.isColumnEmpty(CardDevelopmentType.Green));


        market.discardCards(CardDevelopmentType.Blue);
        market.discardCards(CardDevelopmentType.Blue);

        assertTrue(market.isColumnEmpty(CardDevelopmentType.Blue));

        market.discardCards(CardDevelopmentType.Yellow);
        market.discardCards(CardDevelopmentType.Yellow);

        assertTrue(market.isColumnEmpty(CardDevelopmentType.Blue));
        assertFalse(market.isColumnEmpty(CardDevelopmentType.Yellow));
        assertFalse(market.isColumnEmpty(CardDevelopmentType.Purple));
        assertFalse(market.isColumnEmpty(CardDevelopmentType.Green));
    }
}