package it.polimi.ingsw.model;

public class CardDevelopmentMarket {

    /*
    We have two options on how to implement the following market:

    1. The market is an array of 12 positions: 0 -> 3 are level 1, 4 -> 7 are level 2, 8 -> 11 are level 3.
        0, 4, 8 are GREEN
        1, 5, 9 are YELLOW
        2, 6, 10 are PURPLE
        3, 7, 11 are BLUE

    2. The market is a 3-by-4 matrix. Same conventions as above apply.

     */
    private CardDevelopmentStack[][] market = new CardDevelopmentStack[3][4];

    public CardDevelopmentMarket() {

        // Need to populate market

    }

    public CardDevelopmentStack[][] getMarket() {
        return market;
    }

    CardDevelopment popStack(int rowIndex, int colIndex) {

        // Need to check if PlayerBoard has enough resources to pop this item.

        return market[rowIndex][colIndex].pop();
    }


}
