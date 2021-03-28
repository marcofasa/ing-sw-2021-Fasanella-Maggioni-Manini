package it.polimi.ingsw.model;

public class CardLeaderDiscount extends CardLeader {

    private Resource resource;

    public CardLeaderDiscount(Resource resource){
        this.resource = resource;
    }

    @Override
    public boolean canActivate() {
        return false;
    }

    @Override
    public void activate() {

    }

    @Override
    public void discard() {

    }

    @Override
    public void boughtBy(PlayerBoard playerBoard) {

    }

    @Override
    public PlayerBoard ownedBy() throws CardLeaderNotSoldException {
        return null;
    }
}
