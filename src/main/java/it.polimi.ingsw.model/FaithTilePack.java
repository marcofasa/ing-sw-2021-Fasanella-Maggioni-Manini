package it.polimi.ingsw.model;

import java.util.Map;

public class FaithTilePack {
    private Map<FaithSection,FaithTileStatus> tiles;

    /**
     *
     * @param f
     * @return Status of corresponding FaithSection
     */
    public FaithTileStatus getStatus(FaithSection f){
        return tiles.get(f);
    }

    /**
     * Constructor
     */
    public FaithTilePack(){
        tiles.put(FaithSection.One,FaithTileStatus.Not_Reached);
        tiles.put(FaithSection.Two,FaithTileStatus.Not_Reached);
        tiles.put(FaithSection.Three,FaithTileStatus.Not_Reached);
    }

    void setReached(FaithSection section){
        tiles.replace(section,FaithTileStatus.Reached);
    }

    void setDiscarded(FaithSection section){
        tiles.replace(section,FaithTileStatus.Discarded);
    }
}


