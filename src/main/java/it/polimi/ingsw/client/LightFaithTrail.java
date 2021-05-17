package it.polimi.ingsw.client;

import it.polimi.ingsw.model.FaithTileStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class LightFaithTrail {
    private final Client client;
    private ArrayList<FaithTileStatus> tileStatuses; //size()==3 with the tree tiles in TilesPack
    private HashMap<String, Integer> playersPosition;

    public LightFaithTrail(Client client) {
        this.client=client;
    }

    public void setFaithTrail(HashMap<String, Integer> playersPosition, ArrayList<FaithTileStatus> tileStatuses) {
        this.playersPosition = new HashMap<String, Integer>(playersPosition);
        this.tileStatuses = new ArrayList<>(tileStatuses);
    }

    public ArrayList<FaithTileStatus> getTileStatuses() {
        return tileStatuses;
    }

    public HashMap<String, Integer> getPlayersPosition() {
        return playersPosition;
    }


    public LightFaithTrail getFaithTrail(){
        /*
        try {
            client.sendAndWait(new RequestFaithTrail(),-1);

        }
        catch (RequestTimeoutException e){
            e.printStackTrace();
        }

         */
        return this;
    }
}
