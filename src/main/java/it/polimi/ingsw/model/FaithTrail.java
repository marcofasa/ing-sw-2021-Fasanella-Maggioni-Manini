package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class FaithTrail implements Serializable {
    final private int length = 25;
    private final ArrayList<FaithCell> cells;
    private final HashMap<PlayerBoard, Integer> playerPosition;
    private final HashMap<PlayerBoard, FaithTilePack> playerTiles;
    private final GameTable gameTable;
    private int lorenzoPosition;

    /**
     * Constructor for Single Player
     * @param gameTable
     * @param players there will be only one player
     * @param lorenzo
     */
    public FaithTrail(GameTable gameTable,ArrayList<PlayerBoard> players,Lorenzo lorenzo){
        this.gameTable = gameTable;
        this.cells= new ArrayList<FaithCell>();
        this.playerTiles= new HashMap<PlayerBoard, FaithTilePack>();
        this.playerPosition= new HashMap<PlayerBoard, Integer>();
        lorenzoPosition=0;
        for (PlayerBoard p : players
        ) {
            this.playerPosition.put(p, 0);
            this.playerTiles.put(p, new FaithTilePack());
        }
        for (int i = 0; i < length; i++) {
            cells.add(new FaithCell(i));
        }
    }

    /**
     * Constructor, sets all players at starting position and creates all cells
     * @param gameTable
     * @param players
     */
    public FaithTrail(GameTable gameTable, ArrayList<PlayerBoard> players) {
        this.gameTable = gameTable;
        this.cells= new ArrayList<FaithCell>();
        this.playerTiles= new HashMap<PlayerBoard, FaithTilePack>();
        this.playerPosition= new HashMap<PlayerBoard, Integer>();
        for (PlayerBoard p : players
        ) {
            this.playerPosition.put(p, 0);
            this.playerTiles.put(p, new FaithTilePack());
        }
        for (int i = 0; i < length; i++) {
            cells.add(new FaithCell(i));
        }
    }


    /**
     *
     * @param p
     * @return position of player must be 0<=value<=24
     */
    public int getPosition(PlayerBoard p) {
        return playerPosition.get(p);
    }

    /**
     *
     * @param p Player
     * @return TilePack of corresponding Player
     */

    public FaithTilePack getTilePack(PlayerBoard p) {
        return playerTiles.get(p);
    }

    /**
     *
     * @param p Player
     * @return total points of player (in Trail)
     */
    int getVictoryPoints(PlayerBoard p){
        return cells.get(playerPosition.get(p)).getVictoryPoints() + playerTiles.get(p).getVictoryPoints();
    }


    /**
     * Moving a player p through n positions
     * @param p player that will be moved
     * @param n number of Cells
     */
    public void movePlayer(PlayerBoard p, int n) {
        for(;n>0;n--){

            if (getPosition(p) < 24) {
                moveP(p);
                if(checkEndGame(getPosition(p))) {
                    gameTable.activateEndGame();
                    break;
                }
            }

        }
    }

    public int getLorenzoPosition() {
        return lorenzoPosition;
    }

    public void moveLorenzo(){
        if (lorenzoPosition < 24) {
            lorenzoPosition++;
            if (checkPopeCellLorenzo(getSection(lorenzoPosition))) popeActive(getSection(lorenzoPosition));
        }
    }


    /**
     * Checks if player is in the last position
     * @param position
     * @return
     */
    public boolean checkEndGame(int position) {
        return position == 24;
    }

    /**
     * Returns enum FaithSection depending on pos
     * @param pos 0<=pos<=24
     * @return
     */
    public FaithSection getSection(int pos) {
        if (0 <= pos && pos <= 8) return FaithSection.One;
        else if (9 <= pos && pos <= 16) return FaithSection.Two;
        else return FaithSection.Three;
    }

    /**
     * Moves player of one position
     * @param p Player
     */
    private void moveP(PlayerBoard p) {
        int pos = getPosition(p);
        FaithSection s;
        playerPosition.put(p, pos + 1);
        s = getSection(getPosition(p));
        checkPopeCell(p, s);
    }

    /**
     * Checks if player is on a Pope Cell and calls popeActive() if it's true
     * @param p
     * @param section
     */
    private void checkPopeCell(PlayerBoard p, FaithSection section) {
        FaithCellType cellType = cells.get(getPosition(p)).getType();
        FaithTileStatus tileStatus= playerTiles.get(p).getStatus(section);
        if (cellType == FaithCellType.Pope && tileStatus == FaithTileStatus.Not_Reached) popeActive(section);
    }

    /**
     * Checks if Lorenzo is on a Pope Cell
     * @param section
     * @return
     */
    private boolean checkPopeCellLorenzo(FaithSection section) {
        FaithCellType cellType = cells.get(lorenzoPosition).getType();
        return cellType == FaithCellType.Pope;
    }

    /**
     * Sets Reached all Tiles in current Section and Discarded Tiles players in previous sections
     * @param section
     */
    private void popeActive(FaithSection section) {
        for (PlayerBoard playerboard : playerPosition.keySet()) {
            if (isReached(playerboard, section)) getTilePack(playerboard).setReached(section);
            else getTilePack(playerboard).setDiscarded(section);
        }

    }

    /**
     * check if Tiles are activated due to popeActive()
     * @param p
     * @param section
     * @return
     */
    private boolean isReached(PlayerBoard p, FaithSection section) {
        if (section == FaithSection.One && playerPosition.get(p) >= 5) return true;
        else if (section == FaithSection.Two && playerPosition.get(p) >= 12) return true;
        else if (section == FaithSection.Three && playerPosition.get(p) >= 19) return true;
        else return false;
    }
}
