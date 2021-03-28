package it.polimi.ingsw.model;

import java.util.Map;

public class Deposit extends Stronghold {

    public Deposit() {
        super();
    }

    @Override
    Map<Resource, Integer> addResource(Resource resourceToBeAdded, int amount) {

        /* This method must check that the Resources are allocated at worst with the following distribution:

        YELLOW: 0
        PURPLE: 2
        BLUE: 3
        GREY: 1

        */

        return super.addResource(resourceToBeAdded, amount);
    }

    boolean discard(Resource) {

    }
}
