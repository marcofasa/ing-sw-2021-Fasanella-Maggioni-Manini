package it.polimi.ingsw.model;

public class CardLeaderDeposit extends CardLeader{

    public CardLeaderDeposit(Resource resource, CardLeaderRequirements requirements, Integer victoryPoints){
        this.resource = resource;
        this.requirements = requirements;
        this.victoryPoints = victoryPoints;
        active = false;
    }

    @Override
    public void activate() {
        if(!canActivate()) throw new CardLeaderRequirementsNotMetException();
        if(!active) {
            playerBoard.addLeaderDepositType(resource);
            active = true;
        }
    }
}
