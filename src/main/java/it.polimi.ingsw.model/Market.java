package it.polimi.ingsw.model;

import java.util.*;

/**
 * Class to handle the marble market
 */
public class Market {

    /**
     * market is a matrix of 3 rows x 4 columns
     */
    private final ArrayList<ArrayList<Marble>> market;

    private Marble spareMarble;

    /**
     * Constructor of the class, initializes Market and fills it with Marble s
     * @param gameTable this
     */
    public Market(GameTable gameTable){
        MarbleFactory marbleFactory = new MarbleFactory();
        market = new ArrayList<>(3);
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
        int k=0;
        for (int e=0; e<3; e++) {
            market.add(new ArrayList<>());
            for (int i = 0; i < 4; i++) {
                market.get(e).add(marbleFactory.produce(marbleGenerationList.get(k), gameTable));
                k++;
            }
        }
        spareMarble = marbleFactory.produce(marbleGenerationList.getLast(), gameTable);
    }

    /**
     * gets a column of the matrix, then shifts the column once adding the spare marble in position 0
     * and removes the last Marble which becomes the new spare marble
     * @param colNumber column to take, must be 1<=colNumber<=4
     * @return column of Marble s
     * @throws IllegalArgumentException
     */
    public ArrayList<Marble> getCol(Integer colNumber) throws IllegalArgumentException{
        if(colNumber<1 || colNumber>4)
            throw new IllegalArgumentException();
        ArrayList<Marble> col = new ArrayList<>();
        for (ArrayList<Marble> marbleList: market
        ) {
            col.add(marbleList.get(colNumber-1));
        }
        Marble tempMarble = spareMarble;
        for (ArrayList<Marble> marbleList: market
        ) {
            marbleList.add(colNumber - 1, tempMarble);
            tempMarble = marbleList.remove(colNumber.intValue());
        }
        spareMarble = tempMarble;
        return col;
    }

    /**
     * gets a row of the matrix, then shifts the row once adding the spare marble in position 0
     * and removes the last Marble which becomes the new spare marble
     * @param rowNumber column to take, must be 1<=colNumber<=3
     * @return row of Marble s
     * @throws IllegalArgumentException
     */
    public ArrayList<Marble> getRow(Integer rowNumber) throws IllegalArgumentException{
        if(rowNumber<1 || rowNumber>3)
            throw new IllegalArgumentException();
        ArrayList<Marble> oldRow = market.get(rowNumber - 1);
        market.get(rowNumber - 1).add(0, spareMarble);
        spareMarble = market.get(rowNumber - 1).remove(4);
        return oldRow;
    }

    /**
     * Clones the market matrix
     * @return a clone of the market matrix
     */
    public ArrayList<ArrayList<Marble>> getMarket(){
        ArrayList<ArrayList<Marble>> marketClone = new ArrayList<>(3);
        for (ArrayList<Marble> marbleList: market
             ) {
            ArrayList<Marble> marbleClonedList = new ArrayList<>();
            marketClone.add(marbleClonedList);
            for (Marble marble: marbleList
                 ) {
                marbleClonedList.add(marble.clone());
            }
        }
        return marketClone;
    }

    /**
     * Clones the spare marble
     * @return a clone of the spare marble
     */
    public Marble getSpareMarble(){
        return spareMarble.clone();
    }
}
