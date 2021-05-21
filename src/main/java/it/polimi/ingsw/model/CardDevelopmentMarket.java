package it.polimi.ingsw.model;

import java.util.HashMap;

public class CardDevelopmentMarket {

    private final int NUMBER_OF_ROWS = 3;
    private final int NUMBER_OF_COLUMNS = 4;
    private final CardDevelopmentStack[][] market;

    /**
     * Constructor.
     * Initializes all objects involved with CardDevelopmentMarket
     */
    public CardDevelopmentMarket() {

        market = new CardDevelopmentStack[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

        initializeMarket(market);
    }

    /**
     * Constructor to create a deep copy of the market
     * @param marketToBeCloned deep copy of marketToBeCloned
     */
    public CardDevelopmentMarket(CardDevelopmentMarket marketToBeCloned) {

        market = new CardDevelopmentStack[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

        for (int i = 0 ; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {

                market[i][j] = new CardDevelopmentStack(marketToBeCloned.market[i][j]);
            }
        }
    }

    public int getNUMBER_OF_COLUMNS() {
        return NUMBER_OF_COLUMNS;
    }

    public int getNUMBER_OF_ROWS() {
        return NUMBER_OF_ROWS;
    }

    public CardDevelopmentStack[][] getMarket() {

        CardDevelopmentMarket clone = new CardDevelopmentMarket(this);
        return clone.market;
    }

    /**
     * Method to buy a card from the market. The method assumes PlayerBoard already holds enough resources to buy
     * the card
     * @param board The PlayerBoard that wishes to buy the card and add it to one of its CardDevelopmentSlot
     * @param rowIndex Market row index, must be : rowIndex >= 0 && rowIndex <= 2
     * @param colIndex Market column index, must be : colIndex >= 0 && colIndex <= 3
     * @return Bought CardDevelopment
     */
    CardDevelopment buyCardFromStack(PlayerBoard board, int rowIndex, int colIndex) {

        /*
        Payment logic : Resources are taken from the player's storages with the following priorities :
        First from the leader deposit, then from the deposit and then from the strongbox.
         */

        // TODO Logic for paying with leaderDeposit is yet to be tested!

        DepositLeaderCard depositLeader = board.getDepositLeaderCardInstance();
        Strongbox strongbox = board.getStrongboxInstance();
        Deposit deposit = board.getDepositInstance();
        CardDevelopment desiredCard = market[rowIndex][colIndex].peek();

        HashMap<Resource, Integer> cardCost = desiredCard.getCardCosts();

        board.consumeResources(cardCost);

        // Card cost has now been paid, pop the card from the market and return it to caller
        return market[rowIndex][colIndex].pop();
    }

    /**
     * Discards a given card type
     * @param typeToBeDiscarded Type of the card specified on the drawn ActionCard that triggered discardCards
     */
    void discardCards(CardDevelopmentType typeToBeDiscarded) {

        int columnIndex = typeToBeDiscarded.ordinal();
        int rowIndex = 0;

        while (market[rowIndex][columnIndex].getCards().size() == 0) {
            rowIndex++;

            //No cards are present in this column!
            if (rowIndex == 3) return;
        }

        if (market[rowIndex][columnIndex].getCards().size() > 1) {
            market[rowIndex][columnIndex].pop();
            market[rowIndex][columnIndex].pop();
        }

        else  {

            if (rowIndex == 2) {
                market[rowIndex][columnIndex].pop();
            }

            else {
                market[rowIndex][columnIndex].pop();
                market[rowIndex + 1][columnIndex].pop();
            }
        }
    }

    /**
     * Return true if the column type is empty
     * @param columnType
     * @return
     */
    boolean isColumnEmpty(CardDevelopmentType columnType) {

        int columnIndex = columnType.ordinal();

        for (int rowIndex = 0; rowIndex < NUMBER_OF_ROWS; rowIndex++) {
            if (market[rowIndex][columnIndex].getCards().size() > 0) return false;
        }

        return true;
    }

    /**
     * Method written for testing purposes
     * @param rowIndex
     * @param colIndex
     * @return
     */
    CardDevelopment popFromStack(int rowIndex, int colIndex) {

        if (rowIndex >= 0 && rowIndex <= 2 && colIndex >= 0 && colIndex <= 3)
        return market[rowIndex][colIndex].pop();

        else return null;
    }

    /**
     * Initialization function, creates 12 CardDevelopmentStack.
     * Each CardDevelopmentStack creates 4 CardDevelopment with same type and level.
     * @param market 3 by 4 matrix of CardDevelopmentStack to be populated, ready for initial game move.
     */
    private void initializeMarket(CardDevelopmentStack[][] market) {
        for (int i = 0; i < getNUMBER_OF_ROWS(); i++) {
            for (int j = 0; j < getNUMBER_OF_COLUMNS(); j++) {
                market[i][j] = new CardDevelopmentStack(i, j);

            }
        }
    }

}
