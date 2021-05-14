package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.server.ResponseNicknameUnavailable;
import it.polimi.ingsw.communication.server.ResponseSuccess;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.ProductionSelection;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class VirtualClientCommandDispatcher {

    private final VirtualClient virtualClient;

    public VirtualClientCommandDispatcher(VirtualClient virtualClient) {
        this.virtualClient = virtualClient;
    }

    public void setupConnection(String nickname) {
        try {
            virtualClient.getServer().registerClient(virtualClient, nickname);
        } catch (NicknameAlreadyInUseException e) {
            virtualClient.send(new ResponseNicknameUnavailable());
        }
    }

    public void setLobbySize(Integer size){
        System.out.println("Server size set to " + size);
        virtualClient.getServer().setCurrentLobbySize(virtualClient, size);
    }

    public void requestActivateCardLeader(CardLeader cardLeader, int timeoutID) {
        System.out.println("Card Leader activation request arrived");
        sendWithTimeoutID(new ResponseSuccess(), timeoutID);
    }

    public void initialSelection(ArrayList<CardLeader> cardLeader, Resource resource1, Resource resource2) {
        System.out.println("initialSelection");
    }

    public void requestActivateProduction(ProductionSelection productionSelection) {
        //<- tornare indietro una server response chiamata controller
        //SEND al player server response
    }

    public void requestBuyDevelopmentCard(int rowIndex, int columnIndex) {

        //boolean success = Game.buyCardDevelopment(this, rowIndex, columnIndex)

    }

    public void requestEndTurn() {

        //boolean success = Game.advanceTurn()
    }

    private void sendWithTimeoutID(ServerMessage responseSuccess, int timeoutID) {
        responseSuccess.setTimeoutID(timeoutID);
        virtualClient.send(responseSuccess);
    }

    public void discardResourceSelection(HashMap<Resource, Integer> discardSelection) {

        //boolean success = Game.discardResource
    }

    public void requestDiscardCardLeader(CardLeader cardLeader) {
    }
}
