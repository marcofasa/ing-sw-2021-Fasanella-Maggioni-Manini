package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Resource;

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

    public ArrayList<Resource> getResourceTypes() {
        return new ArrayList<>(resourceTypes);
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
     * @param resourcesToBeAdded map of resources -> amount to be added
     */
    public void add(HashMap<Resource, Integer> resourcesToBeAdded) {
        for (Resource resource : resourceTypes) {
            while(content.get(resource) < 2 && resourcesToBeAdded.get(resource) > 0 ){
                content.put(resource, content.get(resource) + 1);
                resourcesToBeAdded.replace(resource, resourcesToBeAdded.get(resource) - 1);
            }
        }
    }

    public HashMap<Resource, Integer> getContent(){
        return new HashMap<>(content);
    }

    /**
     * This method is used to consume the resources held by DepositCardLeader.
     * @param cost an instance of HashMap representing the cost that is to be paid.
     * @return an instance of HashMap, representing the cost map minus the amount the DepositLeaderCard was able
     * to pay, if applicable.
     */
    public HashMap<Resource, Integer> tryConsume(HashMap<Resource, Integer> cost) {

        for (Resource res : resourceTypes) {

            if (content.get(res) > 0 && cost.get(res) > 0) {

                if (content.get(res) >= cost.get(res)) {
                    content.put(res, content.get(res) - cost.get(res));
                    cost.put(res, 0);

                } else {
                    cost.put(res, cost.get(res) - content.get(res));
                    content.put(res, 0);

                }
            }
        }

        return cost;
    }

}
