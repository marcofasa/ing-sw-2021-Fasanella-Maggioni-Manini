package it.polimi.ingsw.model;

import java.util.Map;

public class FaithTilePack {
    private Map<FaithSection,FaithTileStatus> tiles;

    /**
     * Return FaithTileStatus of corresponding Section
     * @param f
     * @return
     */
    public FaithTileStatus getStatus(FaithSection f){
        return tiles.get(f);
    }

    public FaithTileStatus get(FaithSection section) {
        return tiles.get(section);
    }
    // Private??
    void setReached(FaithSection section){
        tiles.put(section,FaithTileStatus.Reached);
    }
    // Private??
    void setDiscarded(FaithSection section){
        tiles.put(section,FaithTileStatus.Discarded);
    }
}


