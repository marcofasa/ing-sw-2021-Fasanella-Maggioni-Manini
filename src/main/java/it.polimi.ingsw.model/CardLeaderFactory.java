package it.polimi.ingsw.model;

public class CardLeaderFactory {
    public CardLeader produce(CardLeaderType type, Resource resource) {
        switch (type){
            case Production:
                return new CardLeaderProduction(resource);
            case Deposit:
                return new CardLeaderDeposit(resource);
            case WhiteMarble:
                return new CardLeaderWhiteMarble(resource);
            case Discount:
                return new CardLeaderDiscount(resource);
            default:
                throw new IllegalArgumentException();
        }
    }
}
