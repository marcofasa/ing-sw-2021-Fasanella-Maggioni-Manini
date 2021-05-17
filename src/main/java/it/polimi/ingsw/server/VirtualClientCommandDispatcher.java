package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.server.*;
import it.polimi.ingsw.communication.server.requests.RequestDiscardResourceSelection;
import it.polimi.ingsw.communication.server.responses.*;
import it.polimi.ingsw.controller.exceptions.NotActivePlayerException;
import it.polimi.ingsw.model.*;

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

        boolean success;

        try {
            success = virtualClient.getGame().activateLeaderCard(virtualClient, cardLeader);

            if (success) {
                sendWithTimeoutID(new ResponseSuccess(), timeoutID);
            } else {
                sendWithTimeoutID(new ResponseLeaderRequirementsNotMet(), timeoutID);
            }

        } catch (NotActivePlayerException ex) {

            sendWithTimeoutID(new ResponseNotActivePlayerError(), timeoutID);
        }

    }

    public void initialSelection(ArrayList<CardLeader> cardLeader, Resource resource1, Resource resource2) {
        virtualClient.getGame().distributeInitialSelection(virtualClient, cardLeader, resource1, resource2);
    }

    public void requestActivateProduction(ProductionSelection productionSelection, int _timeoutID) {

        try {
            virtualClient.getGame().activateProductionPowers(virtualClient, productionSelection);
            sendWithTimeoutID(new ResponseSuccess(), _timeoutID);

        } catch (NotActivePlayerException e) {
            sendWithTimeoutID(new ResponseNotActivePlayerError(), _timeoutID);

        } catch (InvalidSlotIndexException e) {
            sendWithTimeoutID(new ResponseUnexpectedMove(), _timeoutID);

        } catch (NotEnoughResourcesException e) {
            sendWithTimeoutID(new ResponseNotEnoughResources(), _timeoutID);
        }

    }

    public void requestBuyAndPlaceDevelopmentCard(int _rowIndex, int _columnIndex, int _placementIndex, int _timeoutID) {

        try {

            virtualClient.getGame().buyAndPlaceDevCard(virtualClient, _rowIndex, _columnIndex, _placementIndex);
            sendWithTimeoutID(new ResponseSuccess(), _timeoutID);

        } catch (NotActivePlayerException ex) {

            sendWithTimeoutID(new ResponseNotActivePlayerError(), _timeoutID);

        } catch (NotEnoughResourcesException ex) {

            sendWithTimeoutID(new ResponseNotEnoughResources(), _timeoutID);

        } catch (InvalidCardDevelopmentPlacementException | InvalidSlotIndexException | FullSlotException ex) {

            sendWithTimeoutID(new ResponseUnexpectedMove(), _timeoutID);
        }
    }

    public void requestEndTurn() {
        virtualClient.getGame().advanceTurn(virtualClient);
    }

    private void sendWithTimeoutID(ServerMessage responseSuccess, int timeoutID) {
        responseSuccess.setTimeoutID(timeoutID);
        virtualClient.send(responseSuccess);
    }

    public void discardResourceSelection(HashMap<Resource, Integer> discardSelection, int _timeoutID) {

        HashMap<Resource, Integer> residualResources;

        try {
            residualResources = virtualClient.getGame().discardResources(virtualClient, discardSelection);

            if (residualResources == null) {

                sendWithTimeoutID(new ResponseSuccess(), _timeoutID);

            } else {
                RequestDiscardResourceSelection request = new RequestDiscardResourceSelection(residualResources);

                sendWithTimeoutID(request, _timeoutID);
            }

        } catch (NotActivePlayerException e) {
            sendWithTimeoutID(new ResponseNotActivePlayerError(), _timeoutID);
        }
    }

    public void requestDiscardCardLeader(CardLeader cardLeader) {
        //TODO
    }

    public void useMarket(int _index, String _selection, int _timeoutID) {

        HashMap<Resource, Integer> residualResources;

        try {
            residualResources = virtualClient.getGame().useMarket(virtualClient, _index, _selection);

            if (residualResources == null) {
                sendWithTimeoutID(new ResponseSuccess(), _timeoutID);
            } else {
                sendWithTimeoutID(new RequestDiscardResourceSelection(residualResources), _timeoutID);
            }

        } catch (NotActivePlayerException ex) {
            sendWithTimeoutID(new ResponseNotActivePlayerError(), _timeoutID);

        } catch (IllegalArgumentException ex) {
            sendWithTimeoutID(new ResponseUnexpectedMove(), _timeoutID);

        }
    }

    public void requestDepositInstance(int _timeoutID) {

        HashMap<Resource, Integer> depositClone;
        depositClone = virtualClient.getGame().getDepositClone(virtualClient);

        sendWithTimeoutID(new ResponseStorageInstance(true, depositClone), _timeoutID);
    }

    public void requestStrongboxInstance(int _timeoutID) {

        HashMap<Resource, Integer> strongboxClone;
        strongboxClone = virtualClient.getGame().getStrongboxClone(virtualClient);

        sendWithTimeoutID(new ResponseStorageInstance(false, strongboxClone), _timeoutID);
    }

    public void requestMarketInstance(int _timeoutID) {

        ArrayList<ArrayList<MarbleType>> marketClone;
        marketClone = virtualClient.getGame().getMarketClone(virtualClient);

        sendWithTimeoutID(new ResponseMarketInstance(marketClone), _timeoutID);

    }
}
