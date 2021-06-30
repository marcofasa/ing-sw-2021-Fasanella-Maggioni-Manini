package it.polimi.ingsw.model.faithtrail;

import it.polimi.ingsw.model.enums.FaithSection;
import it.polimi.ingsw.model.enums.FaithTileStatus;

import java.util.HashMap;

public class FaithTilePack {
    private final HashMap<FaithSection, FaithTileStatus> tiles;

    /**
     * @param section of FaithTrail
     * @return Status of corresponding FaithSection
     */
    public FaithTileStatus getStatus(FaithSection section){
        return tiles.get(section);
    }

    /**
     * Constructor creates a map whith the three FaithSection as Not Reached
     */
    public FaithTilePack(){
        this.tiles= new HashMap<>();
        this.tiles.put(FaithSection.One,FaithTileStatus.Not_Reached);
        this.tiles.put(FaithSection.Two,FaithTileStatus.Not_Reached);
        this.tiles.put(FaithSection.Three,FaithTileStatus.Not_Reached);
    }

    /**
     * Sets as Reached the Tile of corresponding Section
     * @param section
     */
    public void setReached(FaithSection section){
        tiles.replace(section,FaithTileStatus.Reached);
    }

    /**
     * Sets as Discarded the Tile of corresponding Section
     * @param section
     */
    public void setDiscarded(FaithSection section){
        tiles.replace(section,FaithTileStatus.Discarded);
    }

    /**
     * Victory points of this TilePack
     * @return
     */
    public int getVictoryPoints() {
        int output=0;
            if(tiles.get(FaithSection.One)==FaithTileStatus.Reached) output=output+2;
            if(tiles.get(FaithSection.Two)==FaithTileStatus.Reached) output=output+3;
            if(tiles.get(FaithSection.Three)==FaithTileStatus.Reached) output=output+4;
        return output;
    }
}


