package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;

public class DepositLeaderCard {

    private final ArrayList<Resource> resourceTypes;

    private final HashMap<Resource, Integer> content;

    /**
     * Class constructor, creates an empty list of resources so initial capacity = 0 and initializes all resources to 0
     */
    public DepositLeaderCard() {
        resourceTypes = new ArrayList<>();
        content = new HashMap<>();
        for (Resource resource :
                Resource.values()) {
            content.put(resource, 0);
        }
    }

    /**
     * Adds a given deposit type
     * @param resource to be added
     */
    public void addLeaderDepositType(Resource resource) {
        resourceTypes.add(resource);
    }

    /**
     * Adds a resource to content
     * @param resourcesToBeAdded
     */
    public void add(HashMap<Resource, Integer> resourcesToBeAdded) {
        for (Resource resource : resourceTypes) {
            while(content.get(resource) <= 2 && resourcesToBeAdded.get(resource) > 0 ){
                content.put(resource, content.get(resource) + 1);
                resourcesToBeAdded.replace(resource, resourcesToBeAdded.get(resource) - 1);
            }
        }
    }

    public HashMap<Resource, Integer> getContent(){
        return new HashMap<>(content);
    }
}
