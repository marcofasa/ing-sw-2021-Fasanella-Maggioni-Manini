package it.polimi.ingsw.model;

import java.util.HashMap;

public class CardDevelopment {

    // Development card state

    private CardDevelopmentType cardType;
    private CardDevelopmentLevel cardLevel;
    private Integer victoryPoints;

    private HashMap<Resource, Integer> cardCosts;
    private HashMap<Resource, Integer> productionInput;
    private HashMap<Resource, Integer> productionOutput;

    private Integer numberOfRedResourceProduced;

    /* Constructor(s) */

    /**
     * Base constructor
     * @param row index of the market row, must be : row >= 0 && row <= 2
     * @param column index of the market column, must be : column >= 0 && column <= 3
     * @param index number of the card that has to be created within the market stack, must be : index >= 0 && index <= 3
     */

    public CardDevelopment(int row, int column, int index) {
        CardDevelopmentInfo information = CardDevelopmentInfo.values()[16 * row + 4 * column + index];

        this.cardType = information.cardType;
        this.cardLevel = information.cardLevel;
        this.victoryPoints = information.victoryPoints;
        this.cardCosts = information.cardCosts;
        this.productionInput = information.productionInput;
        this.productionOutput = information.productionOutput;
        this.numberOfRedResourceProduced = information.numberOfRedResourceProduced;
    }

    /**
     * Constructor to make a deep copy of a CardDevelopment
     * @param cardToBeCloned card to be cloned
     */
    public CardDevelopment(CardDevelopment cardToBeCloned) {

        cardType = cardToBeCloned.cardType;
        cardLevel = cardToBeCloned.cardLevel;
        victoryPoints = cardToBeCloned.victoryPoints;
        cardCosts = new HashMap<>(cardToBeCloned.cardCosts);
        productionInput = new HashMap<>(cardToBeCloned.productionInput);
        productionOutput = new HashMap<>(cardToBeCloned.productionOutput);
        numberOfRedResourceProduced = cardToBeCloned.numberOfRedResourceProduced;
    }


    /* METHODS */

    // Getters

    public CardDevelopmentType getCardType() {
        return cardType;
    }

    public CardDevelopmentLevel getCardLevel() {
        return cardLevel;
    }

    public Integer getVictoryPoints() {
        return victoryPoints;
    }

    public HashMap<Resource, Integer> getCardCosts() {
        return new HashMap<>(cardCosts);
    }

    public HashMap<Resource, Integer> getProductionInput() {
        return new HashMap<>(productionInput);
    }

    public HashMap<Resource, Integer> getProductionOutput() {
        return  new HashMap<>(productionOutput);
    }

    public Integer getNumberOfRedResourceProduced() {
        return numberOfRedResourceProduced;
    }

    // Class methods

    /**
     * Method used to check if player has enough resources in his deposit and strongbox to activate this card
     * @param board The PlayerBoard that owns the card.
     * @return true if PlayerBoard holds enough resources, false otherwise.
     */
    boolean canActivateProduction(PlayerBoard board) {

        Strongbox strongbox = board.getStrongboxInstance();
        Deposit deposit = board.getDepositInstance();

        //temp is initialized with the content of Strongbox, then it is added Deposit's content one resource at a time
        HashMap<Resource, Integer> temp = new HashMap<>(strongbox.getContent());

        for (Resource resource : Resource.values()) {
            temp.put(resource, temp.get(resource) + deposit.getContent().get(resource));
        }

        // Check if card can be activated
        for (Resource resource : Resource.values()) {
            if (getProductionInput().get(resource) > temp.get(resource)) return false;
        }

        return true;
    }

    /**
     * Method to consume resources from deposit and strongbox, and activate the card's production power.
     * Consumes resources by default starting from the deposit, if not enough resources were found in deposit,
     * the rest is taken from the strongbox.
     * This comes from the logic by which it is always convenient to free space in the deposit rather than strongbox.
     *
     * This method assumes the player holds enough resources to activate the card's production power
     *
     * @param board The PlayerBoard that owns the card.
     */
    void activateProduction(PlayerBoard board) {

        Strongbox strongbox = board.getStrongboxInstance();
        Deposit deposit = board.getDepositInstance();

        // Local variables to make methods less verbose
        HashMap<Resource, Integer> prodInput = getProductionInput();
        HashMap<Resource, Integer> prodOutput = getProductionOutput();
        HashMap<Resource, Integer> depContent = deposit.getContent();

        // Consume inputs from deposit first, if deposit can't cover the whole cost, the rest is taken from strongbox
        for (Resource resource : Resource.values()) {

            if (deposit.hasResource(resource, prodInput.get(resource))) {
                deposit.useResource(resource, prodInput.get(resource));
            } else {
                int delta = prodInput.get(resource) - depContent.get(resource);
                strongbox.useResource(resource, delta);
                deposit.useResource(resource, depContent.get(resource));
            }
        }

        // Insert outputs in strongbox
        strongbox.tryAdd(prodOutput);

        // Move pawn on faith trail for the amount of red resources produced by this card
        board.moveFaith(this.numberOfRedResourceProduced);
    }


    /**
     * Method to activate the production power of this card
     * @param board PlayerBoard that owns the CardDevelopment and wishes to activate the production power
     * @return true if activation went well, false otherwise.
     */
    boolean tryActivateProduction(PlayerBoard board) {

        if (canActivateProduction(board)) {
            activateProduction(board);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Private enum that holds the information on all CardDevelopment present in the game.
     */
    private enum CardDevelopmentInfo {

        // TYPE, LEVEL, VICTORY POINTS, COST, INPUT, OUTPUT, REDS PRODUCED

        /* LEVEL 1 */

        CARD1(CardDevelopmentType.Green,
                CardDevelopmentLevel.One,
                1,
                CardDevelopmentInfo.toHashMap("SHIELD:2"),
                CardDevelopmentInfo.toHashMap("COIN:1"),
                CardDevelopmentInfo.toHashMap(""),
                1),
        CARD2(CardDevelopmentType.Green,
                CardDevelopmentLevel.One,
                2,
                CardDevelopmentInfo.toHashMap("SHIELD:1,SERVANT:1,STONE:1"),
                CardDevelopmentInfo.toHashMap("STONE:1"),
                CardDevelopmentInfo.toHashMap("SERVANT:1"),
                0),
        CARD3(CardDevelopmentType.Green,
                CardDevelopmentLevel.One,
                3, CardDevelopmentInfo.toHashMap("SHIELD:3"),
                CardDevelopmentInfo.toHashMap("SERVANT:2"),
                CardDevelopmentInfo.toHashMap("SHIELD:1,STONE:1,COIN:1"),
                0),
        CARD4(CardDevelopmentType.Green,
                CardDevelopmentLevel.One,
                4,
                CardDevelopmentInfo.toHashMap("SHIELD:2,COIN:2"),
                CardDevelopmentInfo.toHashMap("SERVANT:1,STONE:1"),
                CardDevelopmentInfo.toHashMap("COIN:2"),
                1),

        CARD5(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.One,
                1,
                CardDevelopmentInfo.toHashMap("STONE:2"),
                CardDevelopmentInfo.toHashMap("SERVANT:1"),
                CardDevelopmentInfo.toHashMap(""),
                1),
        CARD6(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.One,
                2,
                CardDevelopmentInfo.toHashMap("SHIELD:1,STONE:1,COIN:1"),
                CardDevelopmentInfo.toHashMap("SHIELD:1"),
                CardDevelopmentInfo.toHashMap("COIN:1"),
                0),
        CARD7(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.One,
                3,
                CardDevelopmentInfo.toHashMap("STONE:3"),
                CardDevelopmentInfo.toHashMap("SHIELD:2"),
                CardDevelopmentInfo.toHashMap("SERVANT:1,STONE:1,COIN:1"),
                0),
        CARD8(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.One,
                4,
                CardDevelopmentInfo.toHashMap("SHIELD:2,STONE:2"),
                CardDevelopmentInfo.toHashMap("SERVANT:1,COIN:1"),
                CardDevelopmentInfo.toHashMap("SHIELD:2"),
                1),

        CARD9(CardDevelopmentType.Purple,
                CardDevelopmentLevel.One,
                1,
                CardDevelopmentInfo.toHashMap("SERVANT:2"),
                CardDevelopmentInfo.toHashMap("STONE:1"),
                CardDevelopmentInfo.toHashMap(""),
                1),
        CARD10(CardDevelopmentType.Purple,
                CardDevelopmentLevel.One,
                2,
                CardDevelopmentInfo.toHashMap("SHIELD:1,SERVANT:1,COIN:1"),
                CardDevelopmentInfo.toHashMap("COIN:1"),
                CardDevelopmentInfo.toHashMap("SHIELD:1"),
                0),
        CARD11(CardDevelopmentType.Purple,
                CardDevelopmentLevel.One,
                3,
                CardDevelopmentInfo.toHashMap("SERVANT:3"),
                CardDevelopmentInfo.toHashMap("COIN:2"),
                CardDevelopmentInfo.toHashMap("SHIELD:1,SERVANT:1,STONE:1"),
                0),
        CARD12(CardDevelopmentType.Purple,
                CardDevelopmentLevel.One,
                4,
                CardDevelopmentInfo.toHashMap("SERVANT:2,STONE:2"),
                CardDevelopmentInfo.toHashMap("SHIELD:1,COIN:1"),
                CardDevelopmentInfo.toHashMap("STONE:2"),
                1),

        CARD13(CardDevelopmentType.Blue,
                CardDevelopmentLevel.One,
                1,
                CardDevelopmentInfo.toHashMap("COIN:2"),
                CardDevelopmentInfo.toHashMap("SHIELD:1"),
                CardDevelopmentInfo.toHashMap(""),
                1),
        CARD14(CardDevelopmentType.Blue,
                CardDevelopmentLevel.One,
                2,
                CardDevelopmentInfo.toHashMap("SERVANT:1,STONE:1,COIN:1"),
                CardDevelopmentInfo.toHashMap("SERVANT:1"),
                CardDevelopmentInfo.toHashMap("STONE:1"),
                0),
        CARD15(CardDevelopmentType.Blue,
                CardDevelopmentLevel.One,
                3,
                CardDevelopmentInfo.toHashMap("COIN:3"),
                CardDevelopmentInfo.toHashMap("STONE:2"),
                CardDevelopmentInfo.toHashMap("SHIELD:1,SERVANT:1,COIN:1"),
                0),
        CARD16(CardDevelopmentType.Blue,
                CardDevelopmentLevel.One,
                4,
                CardDevelopmentInfo.toHashMap("SERVANT:2,COIN:2"),
                CardDevelopmentInfo.toHashMap("SHIELD:1,STONE:1"),
                CardDevelopmentInfo.toHashMap("SERVANT:2"),
                1),

        /* LEVEL 2 */

        CARD17(CardDevelopmentType.Green,
                CardDevelopmentLevel.Two,
                5,
                CardDevelopmentInfo.toHashMap("SHIELD:4"),
                CardDevelopmentInfo.toHashMap("STONE:1"),
                CardDevelopmentInfo.toHashMap(""),
                2),
        CARD18(CardDevelopmentType.Green,
                CardDevelopmentLevel.Two,
                6,
                CardDevelopmentInfo.toHashMap("SHIELD:3,SERVANT:2"),
                CardDevelopmentInfo.toHashMap("SHIELD:1,SERVANT:1"),
                CardDevelopmentInfo.toHashMap("STONE:3"),
                0),
        CARD19(CardDevelopmentType.Green,
                CardDevelopmentLevel.Two,
                7,
                CardDevelopmentInfo.toHashMap("SHIELD:5"),
                CardDevelopmentInfo.toHashMap("COIN:2"),
                CardDevelopmentInfo.toHashMap("STONE:2"),
                2),
        CARD20(CardDevelopmentType.Green,
                CardDevelopmentLevel.Two,
                8,
                CardDevelopmentInfo.toHashMap("SHIELD:3,COIN:3"),
                CardDevelopmentInfo.toHashMap("COIN:1"),
                CardDevelopmentInfo.toHashMap("SHIELD:2"),
                1),

        CARD21(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.Two,
                5,
                CardDevelopmentInfo.toHashMap("STONE:4"),
                CardDevelopmentInfo.toHashMap("SHIELD:1"),
                CardDevelopmentInfo.toHashMap(""),
                2),
        CARD22(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.Two,
                6,
                CardDevelopmentInfo.toHashMap("SHIELD:2,STONE:3"),
                CardDevelopmentInfo.toHashMap("SHIELD:1,STONE:1"),
                CardDevelopmentInfo.toHashMap("COIN:3"),
                0),
        CARD23(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.Two,
                7,
                CardDevelopmentInfo.toHashMap("STONE:5"),
                CardDevelopmentInfo.toHashMap("SHIELD:2"),
                CardDevelopmentInfo.toHashMap("SERVANT:2"),
                2),
        CARD24(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.Two,
                8,
                CardDevelopmentInfo.toHashMap("SERVANT:3,STONE:3"),
                CardDevelopmentInfo.toHashMap("SHIELD:1"),
                CardDevelopmentInfo.toHashMap("COIN:2"),
                1),

        CARD25(CardDevelopmentType.Purple,
                CardDevelopmentLevel.Two,
                5,
                CardDevelopmentInfo.toHashMap("SERVANT:4"),
                CardDevelopmentInfo.toHashMap("COIN:1"),
                CardDevelopmentInfo.toHashMap(""),
                2),
        CARD26(CardDevelopmentType.Purple,
                CardDevelopmentLevel.Two,
                6,
                CardDevelopmentInfo.toHashMap("SERVANT:3,COIN:2"),
                CardDevelopmentInfo.toHashMap("SERVANT:1,COIN:1"),
                CardDevelopmentInfo.toHashMap("SHIELD:3"),
                0),
        CARD27(CardDevelopmentType.Purple,
                CardDevelopmentLevel.Two,
                7,
                CardDevelopmentInfo.toHashMap("SERVANT:5"),
                CardDevelopmentInfo.toHashMap("STONE:2"),
                CardDevelopmentInfo.toHashMap("COIN:2"),
                2),
        CARD28(CardDevelopmentType.Purple,
                CardDevelopmentLevel.Two,
                8,
                CardDevelopmentInfo.toHashMap("SHIELD:3,SERVANT:3"),
                CardDevelopmentInfo.toHashMap("STONE:1"),
                CardDevelopmentInfo.toHashMap("SERVANT:2"),
                1),

        CARD29(CardDevelopmentType.Blue,
                CardDevelopmentLevel.Two,
                5,
                CardDevelopmentInfo.toHashMap("COIN:4"),
                CardDevelopmentInfo.toHashMap("SERVANT:1"),
                CardDevelopmentInfo.toHashMap(""),
                2),
        CARD30(CardDevelopmentType.Blue,
                CardDevelopmentLevel.Two,
                6,
                CardDevelopmentInfo.toHashMap("STONE:2,COIN:3"),
                CardDevelopmentInfo.toHashMap("STONE:1,COIN:1"),
                CardDevelopmentInfo.toHashMap("SERVANT:3"),
                0),
        CARD31(CardDevelopmentType.Blue,
                CardDevelopmentLevel.Two,
                7,
                CardDevelopmentInfo.toHashMap("COIN:5"),
                CardDevelopmentInfo.toHashMap("SERVANT:2"),
                CardDevelopmentInfo.toHashMap("SHIELD:2"),
                2),
        CARD32(CardDevelopmentType.Blue,
                CardDevelopmentLevel.Two,
                8,
                CardDevelopmentInfo.toHashMap("STONE:3,COIN:3"),
                CardDevelopmentInfo.toHashMap("SERVANT:1"),
                CardDevelopmentInfo.toHashMap("STONE:2"),
                1),

        /* LEVEL 3 */

        CARD33(CardDevelopmentType.Green,
                CardDevelopmentLevel.Three,
                9,
                CardDevelopmentInfo.toHashMap("SHIELD:6"),
                CardDevelopmentInfo.toHashMap("COIN:2"),
                CardDevelopmentInfo.toHashMap("STONE:3"),
                2),
        CARD34(CardDevelopmentType.Green,
                CardDevelopmentLevel.Three,
                10,
                CardDevelopmentInfo.toHashMap("SHIELD:5,SERVANT:2"),
                CardDevelopmentInfo.toHashMap("SERVANT:1,COIN:1"),
                CardDevelopmentInfo.toHashMap("SHIELD:2,STONE:2"),
                1),
        CARD35(CardDevelopmentType.Green,
                CardDevelopmentLevel.Three,
                11,
                CardDevelopmentInfo.toHashMap("SHIELD:7"),
                CardDevelopmentInfo.toHashMap("SERVANT:1"),
                CardDevelopmentInfo.toHashMap("COIN:1"),
                3),
        CARD36(CardDevelopmentType.Green,
                CardDevelopmentLevel.Three,
                12,
                CardDevelopmentInfo.toHashMap("SHIELD:4,COIN:4"),
                CardDevelopmentInfo.toHashMap("STONE:1"),
                CardDevelopmentInfo.toHashMap("SHIELD:1,COIN:3"),
                0),

        CARD37(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.Three,
                9,
                CardDevelopmentInfo.toHashMap("STONE:6"),
                CardDevelopmentInfo.toHashMap("SHIELD:2"),
                CardDevelopmentInfo.toHashMap("SERVANT:3"),
                2),
        CARD38(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.Three,
                10,
                CardDevelopmentInfo.toHashMap("SERVANT:2,STONE:5"),
                CardDevelopmentInfo.toHashMap("SERVANT:1,STONE:1"),
                CardDevelopmentInfo.toHashMap("SHIELD:2,COIN:2"),
                1),
        CARD39(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.Three,
                11,
                CardDevelopmentInfo.toHashMap("STONE:7"),
                CardDevelopmentInfo.toHashMap("SHIELD:1"),
                CardDevelopmentInfo.toHashMap("SERVANT:1"),
                3),
        CARD40(CardDevelopmentType.Yellow,
                CardDevelopmentLevel.Three,
                12,
                CardDevelopmentInfo.toHashMap("SERVANT:4,STONE:4"),
                CardDevelopmentInfo.toHashMap("SHIELD:1"),
                CardDevelopmentInfo.toHashMap("SERVANT:3,STONE:1"),
                0),

        CARD41(CardDevelopmentType.Purple,
                CardDevelopmentLevel.Three,
                9,
                CardDevelopmentInfo.toHashMap("SERVANT:6"),
                CardDevelopmentInfo.toHashMap("STONE:2"),
                CardDevelopmentInfo.toHashMap("COIN:3"),
                2),
        CARD42(CardDevelopmentType.Purple,
                CardDevelopmentLevel.Three,
                10,
                CardDevelopmentInfo.toHashMap("SERVANT:5,COIN:2"),
                CardDevelopmentInfo.toHashMap("SHIELD:1,STONE:1"),
                CardDevelopmentInfo.toHashMap("SERVANT:2,COIN:2"),
                1),
        CARD43(CardDevelopmentType.Purple,
                CardDevelopmentLevel.Three,
                11,
                CardDevelopmentInfo.toHashMap("SERVANT:7"),
                CardDevelopmentInfo.toHashMap("COIN:1"),
                CardDevelopmentInfo.toHashMap("STONE:1"),
                3),
        CARD44(CardDevelopmentType.Purple,
                CardDevelopmentLevel.Three,
                12,
                CardDevelopmentInfo.toHashMap("SHIELD:4,SERVANT:4,"),
                CardDevelopmentInfo.toHashMap("COIN:1"),
                CardDevelopmentInfo.toHashMap("SERVANT:1,STONE:3"),
                0),

        CARD45(CardDevelopmentType.Blue,
                CardDevelopmentLevel.Three,
                9,
                CardDevelopmentInfo.toHashMap("COIN:6"),
                CardDevelopmentInfo.toHashMap("SERVANT:2"),
                CardDevelopmentInfo.toHashMap("SHIELD:3"),
                2),
        CARD46(CardDevelopmentType.Blue,
                CardDevelopmentLevel.Three,
                10,
                CardDevelopmentInfo.toHashMap("STONE:2,COIN:5"),
                CardDevelopmentInfo.toHashMap("SHIELD:1,COIN:1"),
                CardDevelopmentInfo.toHashMap("SERVANT:2,STONE:2"),
                1),
        CARD47(CardDevelopmentType.Blue,
                CardDevelopmentLevel.Three,
                11,
                CardDevelopmentInfo.toHashMap("COIN:7"),
                CardDevelopmentInfo.toHashMap("STONE:1"),
                CardDevelopmentInfo.toHashMap("SHIELD:1"),
                3),
        CARD48(CardDevelopmentType.Blue,
                CardDevelopmentLevel.Three,
                12,
                CardDevelopmentInfo.toHashMap("STONE:4,COIN:4"),
                CardDevelopmentInfo.toHashMap("SERVANT:1"),
                CardDevelopmentInfo.toHashMap("SHIELD:3,COIN:1"),
                0);

        CardDevelopmentType cardType;
        CardDevelopmentLevel cardLevel;
        Integer victoryPoints;
        HashMap<Resource, Integer> cardCosts;
        HashMap<Resource, Integer> productionInput;
        HashMap<Resource, Integer> productionOutput;
        Integer numberOfRedResourceProduced;

        CardDevelopmentInfo(CardDevelopmentType _cardType,
                CardDevelopmentLevel _cardLevel,
                Integer _victoryPoints,
                HashMap<Resource, Integer> _cardCosts,
                HashMap<Resource, Integer> _productionInput,
                HashMap<Resource, Integer> _productionOutput,
                Integer _numberOfRedResourceProduced
        ) {
            this.cardType = _cardType;
            this.cardLevel = _cardLevel;
            this.victoryPoints = _victoryPoints;
            this.cardCosts = _cardCosts;
            this.productionInput = _productionInput;
            this.productionOutput = _productionOutput;
            this.numberOfRedResourceProduced = _numberOfRedResourceProduced;

        }




        /**
         * Converts a string of Resources and their amount to a HashMap<Resource, Integer>
         * @param str
         * @return
         */
        private static HashMap<Resource, Integer> toHashMap(String str) {

            HashMap<Resource, Integer> outputMap = new HashMap<>();
            for (Resource res : Resource.values()) {
                outputMap.put(res, 0);
            }

            if (str.length() == 0) { return outputMap; }

            String[] elems = str.split(",");

            for (String elem : elems) {
                String[] keyValue = elem.split(":");
                outputMap.put(toResource(keyValue[0]), Integer.valueOf(keyValue[1]));
            }

            return outputMap;
        }


        /**
         * Converts a string in all caps to its corresponding Resource
         * @param str
         * @return
         */
        private static Resource toResource(String str) {

            switch (str) {
                case "COIN":
                    return Resource.Coins;
                case "STONE":
                    return Resource.Stones;
                case "SERVANT":
                    return Resource.Servants;
                case "SHIELD":
                    return Resource.Shields;
            }

            //This return should never be reached!
            return null;

        }
    }
}