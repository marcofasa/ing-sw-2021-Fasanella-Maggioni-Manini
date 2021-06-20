package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.server.*;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import it.polimi.ingsw.communication.server.requests.RequestSignalActivePlayer;
import it.polimi.ingsw.communication.server.responses.ResponseDiscardResourceSelection;
import it.polimi.ingsw.communication.server.responses.*;
import it.polimi.ingsw.controller.exceptions.MainMoveAlreadyMadeException;
import it.polimi.ingsw.controller.exceptions.NotActivePlayerException;
import it.polimi.ingsw.model.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;

/**
 * When a message from a client arrives, the strategy pattern calls one of the methods in this class
 */
public class VirtualClientCommandDispatcher {

    private final VirtualClient virtualClient;

    private Game getGame() {
        switch (virtualClient.getGameState()) {
            case Active -> {
                return virtualClient.getGame();
            }
            case Ended -> {
                send(new KillConnectionMessage());
            }
            case EndedWithError -> {
                send(new KillConnectionMessage(true));
            }
        }
        Thread.currentThread().interrupt();
        return null;
    }

    /**
     * Constructor of the class
     * @param virtualClient VirtualClient associated to this class
     */
    public VirtualClientCommandDispatcher(VirtualClient virtualClient) {
        this.virtualClient = virtualClient;
    }

    /**
     * Method called on connection of the player
     * @param nickname nickname of the player
     */
    public void setupConnection(String nickname) {
        try {
            virtualClient.getServer().registerClient(virtualClient, nickname);
        } catch (NicknameAlreadyInUseException e) {
            System.out.println("nickname unavailable " + nickname);
            virtualClient.send(new ResponseNicknameUnavailable());
        }
    }

    /**
     * Method called on lobby setup message arrival
     * @param size size of the lobby (1 to 4)
     */
    public void setLobbySize(Integer size){
        System.out.println("Server size set to " + size);
        virtualClient.getServer().setCurrentLobbySize(size);
    }

    /**
     * Method called on request of activation of a card leader
     * @param cardLeader cardleader to activate
     * @param timeoutID ID of the timeout request
     */
    public void requestActivateCardLeader(CardLeader cardLeader, int timeoutID) {
        boolean success;
        try {
            success = getGame().activateLeaderCard(virtualClient, cardLeader);
            if (success) {
                sendWithTimeoutID(new ResponseSuccess(), timeoutID);
            } else {
                sendWithTimeoutID(new ResponseLeaderRequirementsNotMet(), timeoutID);
            }
        } catch (NotActivePlayerException ex) {
            sendWithTimeoutID(new ResponseNotActivePlayerError(), timeoutID);
        }
    }


    /**
     * Method called on initial selection message received, resources may or may not be null based on the player's position
     * @param cardLeader selection of cards leader
     * @param resource1 first resource of the selection
     * @param resource2 second resource of the selection
     */
    public void initialSelection(ArrayList<CardLeader> cardLeader, @Nullable Resource resource1, @Nullable Resource resource2) {
        virtualClient.getGame().distributeInitialSelection(virtualClient, cardLeader, resource1, resource2);
    }

    /**
     * Method called on arrival of request for production activation
     * @param productionSelection ProductionSelection instance containing the selection to activate
     * @param _timeoutID ID for the timeout request
     */
    public void requestActivateProduction(ProductionSelection productionSelection, int _timeoutID) {

        try {
            virtualClient.getGame().activateProductionPowers(virtualClient, productionSelection);
            virtualClient.getGame().setMainMoveMade(true);
            sendWithTimeoutID(new ResponseSuccess(GamePhase.Final), _timeoutID);

        } catch (NotActivePlayerException e) {
            sendWithTimeoutID(new ResponseNotActivePlayerError(), _timeoutID);

        } catch (InvalidSlotIndexException e) {
            sendWithTimeoutID(new ResponseUnexpectedMove(), _timeoutID);

        } catch (NotEnoughResourcesException e) {
            sendWithTimeoutID(new ResponseNotEnoughResources(), _timeoutID);
            send(new RequestSignalActivePlayer(
                    virtualClient.getGame().getNicknameByClient(virtualClient),
                    GamePhase.Initial)
            );

        } catch (CardLeaderRequirementsNotMetException e) {
            sendWithTimeoutID(new ResponseLeaderRequirementsNotMet(), _timeoutID);
            send(new RequestSignalActivePlayer(
                    virtualClient.getGame().getNicknameByClient(virtualClient),
                    GamePhase.Initial)
            );

        } catch (MainMoveAlreadyMadeException e) {
            sendWithTimeoutID(new ResponseMainMoveAlreadyMade(), _timeoutID);
        }
    }

    /**
     * Method called on arrival of a buy and place development card request
     * @param _rowIndex index of the card to buy
     * @param _columnIndex column of the card to buy
     * @param _placementIndex slot where to place the card
     * @param _timeoutID ID for the timeout request
     */
    public void requestBuyAndPlaceDevelopmentCard(int _rowIndex, int _columnIndex, int _placementIndex, int _timeoutID) {

        try {

            virtualClient.getGame().buyAndPlaceDevCard(virtualClient, _rowIndex, _columnIndex, _placementIndex);
            virtualClient.getGame().setMainMoveMade(true);
            sendWithTimeoutID(new ResponseSuccess(GamePhase.Final), _timeoutID);

        } catch (NotActivePlayerException ex) {

            sendWithTimeoutID(new ResponseNotActivePlayerError(), _timeoutID);

        } catch (NotEnoughResourcesException ex) {

            sendWithTimeoutID(new ResponseNotEnoughResources(), _timeoutID);
            send(
                    new RequestSignalActivePlayer(
                            virtualClient.getGame().getNicknameByClient(virtualClient),
                            GamePhase.Initial));

        } catch (MainMoveAlreadyMadeException ex) {

            sendWithTimeoutID(new ResponseMainMoveAlreadyMade(), _timeoutID);

        } catch (InvalidCardDevelopmentPlacementException | InvalidSlotIndexException | FullSlotException ex) {

            sendWithTimeoutID(new ResponseInvalidPlacementSelection(), _timeoutID);
            send(
                    new RequestSignalActivePlayer(
                            virtualClient.getGame().getNicknameByClient(virtualClient),
                            GamePhase.Initial));
        } catch (EmptyStackException ex) {

            sendWithTimeoutID(new ResponseUnexpectedMove(), _timeoutID);
            send(
                    new RequestSignalActivePlayer(
                            virtualClient.getGame().getNicknameByClient(virtualClient),
                            GamePhase.Initial));
        }
    }

    /**
     *  Method called on arrival of an end turn request
     */
    public void requestEndTurn() {
        virtualClient.getGame().advanceTurn(virtualClient);
    }

    /**
     * Method called on arrival of a resource selection response
     * @param discardSelection resources to discard
     * @param _timeoutID timeout ID for TimeoutHandler
     */
    public void discardResourceSelection(HashMap<Resource, Integer> discardSelection, int _timeoutID) { /* TODO timeoutID is probably useless since this is not a request */

        HashMap<Resource, Integer> residualResources;

        try {
            residualResources = virtualClient.getGame().discardResources(virtualClient, discardSelection);

            if (residualResources == null) {

                sendWithTimeoutID(new ResponseSuccess(GamePhase.Final), _timeoutID);
                //send(new RequestSignalActivePlayer(virtualClient.getGame().getNicknameByClient(virtualClient), GamePhase.Final));

            } else {

                sendWithTimeoutID(new ResponseDiscardResourceSelection(residualResources), _timeoutID);
            }

        } catch (NotActivePlayerException e) {
            sendWithTimeoutID(new ResponseNotActivePlayerError(), _timeoutID);
        }
    }

    /**
     * Method called on arrival of a discard card leader request
     * @param cardLeaderIndex index of the card leader to discard
     * @param timeoutID ID for the timeout request
     */
    public void requestDiscardCardLeader(Integer cardLeaderIndex, int timeoutID) {
        try{
            virtualClient.getGame().discardCardLeader(virtualClient, cardLeaderIndex);
            sendWithTimeoutID(new ResponseSuccess(), timeoutID);
        } catch (Exception e) {
            sendWithTimeoutID(new ResponseUnexpectedMove(), timeoutID);
        }
    }

    /**
     * Method called on arrival of a use market request
     * @param _index index of the column or row
     * @param _selection "column" or "row"
     * @param _timeoutID ID for the timeout request
     */
    public void requestUseMarket(int _index, String _selection, int _timeoutID) {

        HashMap<Resource, Integer> residualResources;

        try {
            residualResources = virtualClient.getGame().useMarket(virtualClient, _index, _selection);
            virtualClient.getGame().setMainMoveMade(true);
            if (residualResources == null) {
                sendWithTimeoutID(new ResponseSuccess(GamePhase.Final), _timeoutID);
//                send(new RequestSignalActivePlayer(virtualClient.getGame().getNicknameByClient(virtualClient), GamePhase.Final));
            } else {
                sendWithTimeoutID(new ResponseDiscardResourceSelection(residualResources), _timeoutID);
            }

        } catch (NotActivePlayerException ex) {
            sendWithTimeoutID(new ResponseNotActivePlayerError(), _timeoutID);

        } catch (IllegalArgumentException ex) {
            sendWithTimeoutID(new ResponseUnexpectedMove(), _timeoutID);

        } catch (MainMoveAlreadyMadeException e) {
            sendWithTimeoutID(new ResponseMainMoveAlreadyMade(), _timeoutID);
            //send(new RequestSignalActivePlayer(virtualClient.getGame().getNicknameByClient(virtualClient), GamePhase.Initial));
        }
    }

    /**
     * Method called on arrival of a deposit instance request
     * @param _timeoutID ID for the timeout request
     */
    public void requestDepositInstance(int _timeoutID) {

        HashMap<Resource, Integer> depositClone;
        HashMap<Resource, Integer> leaderContentClone = new HashMap<>();
        depositClone = virtualClient.getGame().getDepositClone(virtualClient);

        ArrayList<Resource> leaderResourcesClone = virtualClient.getGame().getLeaderResourcesClone(virtualClient);

        if (leaderResourcesClone.size() > 0)
            leaderContentClone = virtualClient.getGame().getLeaderContentClone(virtualClient);

        sendWithTimeoutID(
                new ResponseStorageInstance(
                        true,
                        depositClone,
                        leaderResourcesClone,
                        leaderContentClone
                ),
                _timeoutID);
    }

    /**
     * Method called on arrival of strongbox instance request
     * @param _timeoutID ID for the timeout request
     */
    public void requestStrongboxInstance(int _timeoutID) {

        HashMap<Resource, Integer> strongboxClone;
        strongboxClone = virtualClient.getGame().getStrongboxClone(virtualClient);

        sendWithTimeoutID(new ResponseStorageInstance(false, strongboxClone, null, null), _timeoutID);
    }

    /**
     * Method called on arrival of market instance request
     * @param _timeoutID ID for the timeout request
     */
    public void requestMarketInstance(int _timeoutID) {

        ArrayList<ArrayList<MarbleType>> marketClone;
        marketClone = virtualClient.getGame().getMarketClone();
        Marble spareMarble = virtualClient.getGame().getSpareMarble();
        sendWithTimeoutID(new ResponseMarketInstance(marketClone, spareMarble), _timeoutID);

    }

    /**
     * Method called on arrival of development card market instance request
     * @param _timeoutID ID for the timeout request
     */
    public void getCardDevelopmentMarketInstance(int _timeoutID) {

        ArrayList<ArrayList<CardDevelopment>> cardMarketClone;
        cardMarketClone = virtualClient.getGame().getCardDevMarketClone();

        sendWithTimeoutID(new ResponseCardDevelopmentMarketInstance(cardMarketClone), _timeoutID);

    }

    /**
     * Method called on arrival of a faithTrail instance request
     * @param _vClient virtualClient who's asking
     * @param _timeoutID ID for the timeout request
     */
    public void requestFaithTrail(VirtualClient _vClient, int _timeoutID) { /* TODO _vClient is probably useless */

        ArrayList<FaithTileStatus> tileStatuses;
        tileStatuses = virtualClient.getGame().getTileStatuses(_vClient);

        HashMap<String, Integer> playerPositions;
        playerPositions = virtualClient.getGame().getPlayerPositions();

        sendWithTimeoutID(new ResponseLightFaithTrail(tileStatuses, playerPositions), _timeoutID);
    }

    /**
     * Method called on arrival of card leader deck request
     * @param _timeoutID ID for the timeout request
     */
    public void requestLeaderCards(int _timeoutID) {

        ArrayList<CardLeader> leaderCards;
        leaderCards = virtualClient.getGame().getLeaderCards(virtualClient);

        sendWithTimeoutID(new ResponseCardLeaders(leaderCards), _timeoutID);
    }

    /**
     * Method called on arrival of a top cards development request, used to checkout a player's PlayerBoard
     * @param _vClient whose cards to checkout
     * @param _timeoutID ID for the timeout request
     */
    public void requestTopCardsDevelopment(VirtualClient _vClient, int _timeoutID) {

        ArrayList<CardDevelopment> developmentCards;
        developmentCards = virtualClient.getGame().getTopDevelopmentCards(_vClient);

        sendWithTimeoutID(new ResponseTopCardsDevelopment(developmentCards), _timeoutID);

    }


    /**
     * sends a message to the client with the ID necessary to stop the player's timeout
     * @param serverMessage message to be sent
     * @param timeoutID timeout received from the client request
     */
    private void sendWithTimeoutID(ServerMessage serverMessage, int timeoutID) {
        serverMessage.setTimeoutID(timeoutID);
        virtualClient.send(serverMessage);
    }

    /**
     * Proxy method for sending messages
     * @param message ServerMessage to be sent from the virtual client associated
     */
    private void send(ServerMessage message) {
        virtualClient.send(message);
    }
}
