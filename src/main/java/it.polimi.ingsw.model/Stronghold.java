package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class Stronghold {

    private HashMap<Resource, Integer> content;

    public Stronghold() {
        content = new HashMap<Resource, Integer>();
    }

    public HashMap<Resource, Integer> getContent() {
        return content;
    }

    void useResource(Resource resourceToBeUsed, int amount) {

    }

    boolean hasResource(Resource resourceToBeChecked, int amount) {

    }

    Map<Resource, Integer> addResource(Resource resourceToBeAdded, int amount) {

    }

}
