package it.polimi.ingsw.model;

import java.util.HashMap;

public class Strongbox {

    private HashMap<Resource, Integer> content;

    /* METHODS */

    //Constructor

    public Strongbox() {

        content = new HashMap<>();

        content.put(Resource.Coins, 0);
        content.put(Resource.Stone, 0);
        content.put(Resource.Servants, 0);
        content.put(Resource.Shields, 0);
    }

    //Getter

    public HashMap<Resource, Integer> getContent() {
        return content;
    }

    /* Class methods */

    //Check if Strongbox contains enough resources to be used
    boolean hasResource(Resource resourceToBeChecked, int amount) {
        return content.get(resourceToBeChecked) >= amount;
    }

    //Consume amount resourceToBeUsed from the Strongbox
    void useResource(Resource resourceToBeUsed, int amount) {
        content.put(resourceToBeUsed, content.get(resourceToBeUsed) - amount);
    }

    //Exposed method to add resources to the strongbox, always returns true
    boolean tryAdd(HashMap<Resource, Integer> resourcesToBeAdded) {

        for (Resource res : Resource.values()) {
            addResource(res, resourcesToBeAdded.get(res));
        }

        return true;

    }

    //Add amount resourceToBeAdded to the Strongbox, does not throw the exception! It is only thrown in subclass.
    protected void addResource(Resource resourceToBeAdded, int amount) {
        content.put(resourceToBeAdded, content.get(resourceToBeAdded) + amount);
    }

}
