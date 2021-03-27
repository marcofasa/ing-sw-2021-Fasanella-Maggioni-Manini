package it.polimi.ingsw.model;

import java.util.*;

/**
 * Class to handle the marble market
 */
public class Market {

    /**
     * market is a matrix of 3 raws x 4 columns
     */
    private Marble[][] market;

    private Marble spareMarble;

    private Marble marbleGenerator(MarbleType marbleType, GameTable gameTable){
        Marble marble;
        switch (marbleType) {
            case MarbleRed:
                marble = new MarbleRed(gameTable.getFaithTrail());
                break;
            case MarbleWhite:
                marble = new MarbleWhite();
                break;
            case MarbleBlue:
                marble = new MarbleNormal(Resource.Shields);
                break;
            case MarblePurple:
                marble = new MarbleNormal(Resource.Servants);
                break;
            case MarbleGrey:
                marble = new MarbleNormal(Resource.Stone);
                break;
            case MarbleYellow:
                marble = new MarbleNormal(Resource.Coins);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return marble;
    }

    /**
     * Constructor of the class, initializes Market and fills it with Marble s
     * @param gameTable this
     */
    public Market(GameTable gameTable){
        market = new Marble[3][4];
        LinkedList<MarbleType> marbleGenerationList = new LinkedList<>(Arrays.asList(
                MarbleType.MarbleWhite,
                MarbleType.MarbleWhite,
                MarbleType.MarbleWhite,
                MarbleType.MarbleWhite,
                MarbleType.MarbleBlue,
                MarbleType.MarbleBlue,
                MarbleType.MarbleGrey,
                MarbleType.MarbleGrey,
                MarbleType.MarbleYellow,
                MarbleType.MarbleYellow,
                MarbleType.MarblePurple,
                MarbleType.MarblePurple,
                MarbleType.MarbleRed
        ));
        Collections.shuffle(marbleGenerationList);
        int i;
        for (Marble[] marbleList: market
             ) {
            for (Marble marble: marbleList
                 ) {
                    for (i=0; i<12; i++){
                        marble = marbleGenerator(marbleGenerationList.get(i), gameTable);
                    }
                    spareMarble = marbleGenerator(marbleGenerationList.getLast(), gameTable);
            }

        }
    }

    /* TODO review*/
    public List<Marble> getCol(Integer colNumber) {
        Marble[] column = new Marble[3];
        for (int i = 0; i < market.length; i++){
            column[i] = market[colNumber][i];
        }
        return Arrays.asList(column.clone());
    }

    public List<Marble> getRow(Integer rowNumber) {
        return Arrays.asList(market[rowNumber].clone());
    }

}
