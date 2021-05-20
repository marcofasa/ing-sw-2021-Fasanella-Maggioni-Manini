package it.polimi.ingsw.model;

import java.util.HashMap;

public class Deposit extends Strongbox {

    // Local variables to check if Deposit's invariant is always verified
    private final int singleOccurrenceLimit, doubleOccurrenceLimit, tripleOccurrenceLimit;

    /**
     * Class constructor : initializes content just like Strongbox's
     */
    public Deposit() {

        super();
        singleOccurrenceLimit = 3;
        doubleOccurrenceLimit = 2;
        tripleOccurrenceLimit = 1;

    }

    @Override
    public boolean tryAdd(HashMap<Resource, Integer> resourcesToBeAdded) {

        // Create a temporary copy of deposit content
        Deposit temp = new Deposit();

        // Adding all resources to temp
        for (Resource res : Resource.values()) {

            //Resources already held in content
            temp.addResource(res, content.get(res));

            //Resources the player wishes to add
            if(resourcesToBeAdded.get(res) != null)
                temp.addResource(res, resourcesToBeAdded.get(res));
        }

        if (temp.checkInvariant()) {

            // If invariant is valid for temp, apply changes to real deposit content and return true
            for (Resource res : Resource.values()) {
                if(resourcesToBeAdded.get(res) != null)
                    addResource(res, resourcesToBeAdded.get(res));
            }

            return true;
        }

        else
            // Else do not modify real content and return false.
            return false;

    }

    /**
     * Method to decrement Resource of choice by 1 item
     * @param choice Resource to be decremented
     */
    void discard(Resource choice) {

        //Discard 1 item of chosen resource, if it is available
        if (hasResource(choice, 1)) useResource(choice, 1);
    }

    /**
     * Method to check if Deposit's invariant is valid.
     * @return true if Deposit is in a valid state, false if otherwise.
     */
    private boolean checkInvariant() {

        /*

        This method uses the following 3 variables to check if the class invariant is respected.

        At all times, content's resource distribution must respect the following invariant:

            If 3 items of exactly 1 resource are in content, the other resources must be present as 2 or less items
            If 2 or more items of exactly 2 resources are in content, the other resources must be present as 1 or less items
            If 1 or more item(s) of exactly 3 resources are in content, the other resource must be present as 0 items.

         */

        int numberOfSingleOccurrences;
        int numberOfDoubleOccurrences;
        int numberOfTripleOccurrences;

        numberOfSingleOccurrences = numberOfDoubleOccurrences = numberOfTripleOccurrences = 0;

        for (Resource resource : Resource.values()) {

            switch (getContent().get(resource)) {

                case 0:
                    break;
                case 1:
                    numberOfSingleOccurrences++;
                    break;
                case 2:
                    numberOfSingleOccurrences++;
                    numberOfDoubleOccurrences++;
                    break;
                case 3:
                    numberOfSingleOccurrences++;
                    numberOfDoubleOccurrences++;
                    numberOfTripleOccurrences++;
                    break;

                default:
                    return false;
            }
        }

        return numberOfSingleOccurrences <= singleOccurrenceLimit
                && numberOfDoubleOccurrences <= doubleOccurrenceLimit
                && numberOfTripleOccurrences <= tripleOccurrenceLimit;

    }
}
