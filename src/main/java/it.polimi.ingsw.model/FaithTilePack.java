package it.polimi.ingsw.model;

import java.util.Map;

public class FaithTilePack {
    private Map<FaithSection,FaithTileStatus> tiles;

    /**
     *
     * @param f
     * @return Value of corresponding FaithSection
     */
    public FaithTileStatus getStatus(FaithSection f){
        return tiles.get(f);
    }

    // Private??
    void setReached(FaithSection section){
        tiles.replace(section,FaithTileStatus.Reached);
    }
    // Private??
    void setDiscarded(FaithSection section){
        tiles.replace(section,FaithTileStatus.Discarded);
    }
}


