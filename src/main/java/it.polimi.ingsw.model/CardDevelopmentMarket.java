package it.polimi.ingsw.model;

public class CardDevelopmentMarket {

    private final int NUMBER_OF_ROWS;
    private final int NUMBER_OF_COLUMNS;
    private final CardDevelopmentStack[][] market;


    public CardDevelopmentMarket() {

        NUMBER_OF_ROWS = 3;
        NUMBER_OF_COLUMNS = 4;
        market = new CardDevelopmentStack[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

        initializeMarket(market);
    }

    public int getNUMBER_OF_COLUMNS() {
        return NUMBER_OF_COLUMNS;
    }

    public int getNUMBER_OF_ROWS() {
        return NUMBER_OF_ROWS;
    }

    public CardDevelopmentStack[][] getMarket() {
        return market;
    }

    CardDevelopment buyCardFromStack(PlayerBoard board, int rowIndex, int colIndex) {

        /*
         Insert payment logic : resources are first taken from deposit, if deposit cannot cover the whole cost
         the rest is taken from strongbox
         */

        return market[rowIndex][colIndex].pop();

    }

    private void initializeMarket(CardDevelopmentStack[][] market) {
        for (int i = 0; i < getNUMBER_OF_ROWS(); i++) {
            for (int j = 0; j < getNUMBER_OF_COLUMNS(); j++) {
                market[i][j] = new CardDevelopmentStack(i, j);
            }
        }
    }

}
