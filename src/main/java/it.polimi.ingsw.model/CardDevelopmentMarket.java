package it.polimi.ingsw.model;

public class CardDevelopmentMarket {

    private final int NUMBER_OF_ROWS;
    private final int NUMBER_OF_COLUMNS;
    private final CardDevelopmentStack[][] market;


    /**
     * Constructor.
     * Initializes all objects involved with CardDevelopmentMarket
     */
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
         Payment logic : resources are first taken from deposit, if deposit cannot cover the whole cost
         the rest is taken from strongbox
         */

        Strongbox strongbox = board.getStrongbox();
        Deposit deposit = board.getDeposit();
        CardDevelopment desiredCard = market[rowIndex][colIndex].peek();

        // Consume the card cost for each resource
        for (Resource res : desiredCard.getCardCosts().keySet()) {

            if (deposit.hasResource(res, desiredCard.getCardCosts().get(res))) {

                // If true, consume resources only from deposit
                deposit.useResource(res, desiredCard.getCardCosts().get(res));
            } else {

                // Else, consume all of deposit and take delta from strongbox
                int delta = desiredCard.getCardCosts().get(res) - deposit.getContent().get(res);
                strongbox.useResource(res, delta);
                deposit.useResource(res, deposit.getContent().get(res));
            }
        }

        // Card cost has now been paid, pop the card from the market and return it to caller
        return market[rowIndex][colIndex].pop();

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