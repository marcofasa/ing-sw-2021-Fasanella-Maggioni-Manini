package it.polimi.ingsw.model;

import java.util.HashMap;

public class Strongbox {

    private HashMap<Resource, Integer> content;

    /* METHODS */

    /**
     * Initialize content to the following HashMap:
     *
     * Coins : 0
     * Stones : 0
     * Servants : 0
     * Shields : 0
     */

    public Strongbox() {

        content = new HashMap<>();

        content.put(Resource.Coins, 0);
        content.put(Resource.Stones, 0);
        content.put(Resource.Servants, 0);
        content.put(Resource.Shields, 0);
    }

    /**
     * Getter method for content
     * @return this.content
     */

    public HashMap<Resource, Integer> getContent() {
        return new HashMap<>(content);
    }

    /* Class methods */

    /**
     *
     * @param resourceToBeChecked resource whose amount we want to check
     * @param amount amount of resourceToBeChecked needed
     * @return content.get(resourceToBeChecked) >= amount
     */
    boolean hasResource(Resource resourceToBeChecked, int amount) {
        return content.get(resourceToBeChecked) >= amount;
    }

    /**
     * Must be called after hasResource(resourceToBeUsed, amount) returns true
     * @param resourceToBeUsed resource the player wants to consume from the storage
     * @param amount amount of resourceToBeUsed the player wants to consume
     */
    void useResource(Resource resourceToBeUsed, int amount) {
        content.put(resourceToBeUsed, content.get(resourceToBeUsed) - amount);
    }

    /**
     * Method that adds resources to strongbox
     * @param resourcesToBeAdded Map of <Resource,Integer> to be added to content
     * @return true, because Strongbox's content has no boundaries
     */
    boolean tryAdd(HashMap<Resource, Integer> resourcesToBeAdded) {

        for (Resource res : Resource.values()) {
            addResource(res, resourcesToBeAdded.get(res));
        }
        return true;
    }

    /**
     * Add an arbitrary amount of a single resource to content
     * @param resourceToBeAdded resource to be added to content
     * @param amount amount of resource to be added to content
     */
    protected void addResource(Resource resourceToBeAdded, int amount) {
        content.put(resourceToBeAdded, content.get(resourceToBeAdded) + amount);
    }

}
