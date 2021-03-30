package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class FaithTrail {
    final private int lenght=24;
    private ArrayList<FaithCell> cells;
    private Map<PlayerBoard,Integer> playerPosition;
    private Map<PlayerBoard,FaithTilePack> playerTiles;
    FaithCell getCell(int i){
        return cells.get(i);
    }
    int getVictoryPoints(){
        return 1;
    }

    /**
     * Moving a player p through n positions
     * @param  p player that will be moved
     * @param n number of Cells
     */
    public void movePlayer(PlayerBoard p, int n){
         for(;n>0;n--){
             this.moveP(p);
         }
    }

    /**
     * Return enum FaithSection depending on pos
     * @param pos
     * @return
     */
    public FaithSection getSection(int pos){
        if (5<=pos && pos<=8) return FaithSection.One;
            else if (12<=pos && pos<=16) return FaithSection.Two;
               else  if (19<=pos && pos<=24) return FaithSection.Three;
               else return FaithSection.Outside;
    }

    /**
     * Moves player of one position
     * @param p Player
     */
    private void moveP(PlayerBoard p){
        int pos;
        FaithSection s;
          pos =  playerPosition.get(p);
          pos++;
          playerPosition.put(p,pos);
          if(pos==25) {
              endGame();
              return;
          }
          s = getSection(pos);
          this.checkCell(cells.get(pos).getType(),playerTiles.get(p).getStatus(s),s);
    }

    /**
     * Checks possible activations of current cell
     * @param celltype
     * @param tilestatus
     * @param section
     */
    private void checkCell(FaithCellType celltype,FaithTileStatus tilestatus, FaithSection section){
           if (celltype==FaithCellType.Pope && tilestatus==FaithTileStatus.Not_Reached) popeActive(section);

    }

    /**
     * All players from current to the first have one last round
     */
    private void endGame(){

    }

    /**
     * Sets Reached all Tiles in current Section and Discarded Tiles players in previous sections
     * @param section
     */
    private void popeActive(FaithSection section){
        Iterator<Map.Entry<PlayerBoard, FaithTilePack>> iterator = playerTiles.entrySet().iterator();
        while (iterator.hasNext()) {
            if(isReached(iterator.next().getKey(),section))  iterator.next().getValue().setReached(section);
            else iterator.next().getValue().setDiscarded(section);
        }

    }

    /**
     * check if Tiles are activated due to popeActive()
     * @param p
     * @param section
     * @return
     */
    private boolean isReached(PlayerBoard p, FaithSection section){
        if (section==FaithSection.One && playerPosition.get(p)>=5) return true;
        else if (section==FaithSection.Two && playerPosition.get(p)>=12) return true;
            else if (section==FaithSection.Three && playerPosition.get(p)>=19) return true;
                else return false;
    }
}
