package it.polimi.ingsw.model;

import java.util.HashMap;

public class FaithTilePack {
    private HashMap<FaithSection,FaithTileStatus> tiles;

    /**
     * @param s
     * @return Status of corresponding FaithSection
     */
    public FaithTileStatus getStatus(FaithSection s){
        return tiles.get(s);
    }

    /**
     * Constructor
     */
    public FaithTilePack(){
        this.tiles=new HashMap<FaithSection,FaithTileStatus>();
        this.tiles.put(FaithSection.One,FaithTileStatus.Not_Reached);
        this.tiles.put(FaithSection.Two,FaithTileStatus.Not_Reached);
        this.tiles.put(FaithSection.Three,FaithTileStatus.Not_Reached);
    }

    /**
     * Sets as Reached the Tile of corresponding Section
     * @param section
     */
    protected void setReached(FaithSection section){
        tiles.replace(section,FaithTileStatus.Reached);
    }

    /**
     * Sets as Discarded the Tile of corresponding Section
     * @param section
     */
    protected void setDiscarded(FaithSection section){
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


