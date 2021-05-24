package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.*;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import it.polimi.ingsw.communication.server.requests.RequestInitialSelection;
import it.polimi.ingsw.communication.server.requests.RequestSignalActivePlayer;
import it.polimi.ingsw.communication.server.responses.ResponseNotActivePlayerError;
import it.polimi.ingsw.communication.server.responses.ResponseSuccess;
import it.polimi.ingsw.controller.exceptions.MainMoveAlreadyMadeException;
import it.polimi.ingsw.controller.exceptions.NotActivePlayerException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.VirtualClient;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * This class is responsible for instantiating the controller and model classes, receiving and dispatching requests
 * from the clients and sending requests and responses to them.
 * <p>
 * This class maintains the correlations between VirtualClient(s) and nickname(s), in order to be able to expose methods
 * parametric in VirtualClient, find the corresponding nickname and call Controller's methods, which are parametric
 * in String.
 */

public class Game implements Runnable {

    private final Boolean debug;
    private ArrayList<VirtualClient> players;
    private GameTable gameTable;
    private Controller controller;
    private final LinkedHashMap<String, VirtualClient> nicknameClientMap;
    private final LinkedHashMap<VirtualClient, String> clientNicknameMap;
    private final LinkedHashMap<Integer, VirtualClient> idPlayerClientMap;
    private boolean displayStartingEndGame = true;
    private boolean mainMoveMade = false;

    /**
     * Basic constructor which instantiates the private LinkedHashMaps
     */
    public Game(Boolean debug) {
        nicknameClientMap = new LinkedHashMap<>();
        clientNicknameMap = new LinkedHashMap<>();
        idPlayerClientMap = new LinkedHashMap<>();
        this.debug = debug;
    }
    
    public String getNicknameByClient(VirtualClient virtualClient){
        for (String nickname :
                nicknameClientMap.keySet()) {
            if (nicknameClientMap.get(nickname) == virtualClient)
                return nickname;
        }
        throw new IllegalArgumentException("Unknown client");
    }
    
    /**
     * This method is called by ResponseInitialSelection's read() method.
     * It is used to assign a player's selection of card leaders and bonus resources.
     * @param _vClient The VirtualClient corresponding to the player that has sent the response.
     * @param _cardLeader An ArrayList of CardLeader, which contains the cards selected by the player.
     * @param _resource1 The first bonus resource selected, null if the player does not have rights to obtain the resource.
     * @param _resource2 The second bonus resource selected, null if the player does not have rights to obtain the resource.
     */
    public void distributeInitialSelection(VirtualClient _vClient, ArrayList<CardLeader> _cardLeader, Resource _resource1, Resource _resource2) {

        String nickname = clientNicknameMap.get(_vClient);

        try {
            controller.assignInitialBenefits(nickname, _cardLeader, _resource1, _resource2);
            send(_vClient, new ResponseSuccess(GamePhase.Unmodified));
        } catch (NotActivePlayerException ex) {
            send(_vClient, new ResponseNotActivePlayerError());
        }
    }

    @Override
    public void run() {
        System.out.println("Game "+ this + "has begun");
        start();
        solicitInitialSelections();
    }

    /**
     * get player's virtual client
     *
     * @param index index of the player, starting by 1
     * @return player's virtual client
     */
    public VirtualClient getClientByIndex(Integer index) {
        return players.get(index + 1);
    }

    /**
     * This method populates all private LinkedHashMaps.
     * @param virtualClients ArrayList of VirtualClient, contains all virtualClients for the current match
     * @param playersNicknames ArrayList of String, contains all nicknames selected by the players
     */
    public void addAllPlayers(ArrayList<VirtualClient> virtualClients, ArrayList<String> playersNicknames) {
        this.players = new ArrayList<>(virtualClients);
        for (int i = 0; i < virtualClients.size(); i++) {
            VirtualClient virtualClient = virtualClients.get(i);
            virtualClient.setGame(this);
            idPlayerClientMap.put(virtualClient.getID(), virtualClient);
            nicknameClientMap.put(playersNicknames.get(i), virtualClient);
            clientNicknameMap.put(virtualClient, playersNicknames.get(i));
        }
    }

    /**
     * This private method instantiates gameTable and controller, adds the players to gameTable and start the match.
     */
    private void start() {

        // Initialized model
        gameTable = new GameTable(players.size() == 1);

        // Populate match
        for (String nickname : nicknameClientMap.keySet()) {
            gameTable.addPlayer(nickname);
        }

        // Initialize controller
        controller = new Controller(gameTable);

        // Distribute initial cards
        gameTable.startGame();
    }

    /**
     * This method sends a RequestInitialSelection to all players and waits for their response one by one.
     */
    private void solicitInitialSelections() {

        for (VirtualClient vClient : players) {

            try {
                sendAndWait(vClient, new RequestInitialSelection(
                        controller.getPlayerBoardByNickname(clientNicknameMap.get(vClient)).getCardsLeaderBeforeSelecting(),
                        gameTable.getIndexFromPlayer(controller.getPlayerBoardByNickname(clientNicknameMap.get(vClient)))
                ), -1);


            } catch (RequestTimeoutException ex) {

                System.out.println("Still waiting for "
                        + controller.getPlayerBoardByNickname(clientNicknameMap.get(vClient))
                        + " to make his selection..");
            }
        }

        //At this point, Initial Selection phase has ended. Main Loop phase has begun.

        //sendAll signaling its the first player's turn to play
        String firstNickname = clientNicknameMap.get(players.get(0));
        sendAll(new RequestSignalActivePlayer(firstNickname, GamePhase.Initial));

    }

    // Public getter methods to be invoked when a ClientRequest is received

    public HashMap<Resource, Integer> getDepositClone(VirtualClient _vClient) {

        String nickname = clientNicknameMap.get(_vClient);
        PlayerBoard board = gameTable.getPlayerByNickname(nickname);

        return board.getDepositInstance().getContent();
    }

    public HashMap<Resource, Integer> getStrongboxClone(VirtualClient _vClient) {

        String nickname = clientNicknameMap.get(_vClient);
        PlayerBoard board = gameTable.getPlayerByNickname(nickname);

        return board.getStrongboxInstance().getContent();
    }

    public ArrayList<ArrayList<MarbleType>> getMarketClone() {

        ArrayList<ArrayList<MarbleType>> marketClone = new ArrayList<>();
        ArrayList<ArrayList<Marble>> marbleMatrix = gameTable.getMarketInstance().getMarket();

        for (int i = 0; i < marbleMatrix.size(); i++) {

            marketClone.add(new ArrayList<>());

            for (int j = 0; j < marbleMatrix.get(0).size(); j++) {

                MarbleType type = marbleMatrix.get(i).get(j).getType();
                marketClone.get(i).add(type);
            }
        }
        return marketClone;
    }

    public ArrayList<ArrayList<CardDevelopment>> getCardDevMarketClone() {

        ArrayList<ArrayList<CardDevelopment>> output = new ArrayList<>();
        CardDevelopmentStack[][] cardSlotMatrix = gameTable.getCardDevelopmentMarketInstance().getMarket();

        for (int i = 0; i < cardSlotMatrix.length; i++) {

            output.add(new ArrayList<>());
            for (int j = 0; j < cardSlotMatrix[0].length; j++) {

                CardDevelopment topCard = cardSlotMatrix[i][j].getCards().peek();
                output.get(i).add(topCard);
            }
        }

        return output;
    }

    public ArrayList<FaithTileStatus> getTileStatuses(VirtualClient _vClient) {

        ArrayList<FaithTileStatus> output = new ArrayList<>();
        FaithTilePack pack;
        String nickname = clientNicknameMap.get(_vClient);
        PlayerBoard board = gameTable.getPlayerByNickname(nickname);

        pack = gameTable.getFaithTrailInstance().getTilePack(board);

        for (FaithSection section : FaithSection.values()) {
            output.add(pack.getStatus(section));
        }

        return output;
    }

    public HashMap<String, Integer> getPlayerPositions() {

        HashMap<String, Integer> output = new HashMap<>();
        String nickname;
        PlayerBoard board;

        for (VirtualClient _vClient : clientNicknameMap.keySet()) {

            nickname = clientNicknameMap.get(_vClient);
            board = gameTable.getPlayerByNickname(nickname);

            output.put(nickname, gameTable.getFaithTrailInstance().getPosition(board));
        }

        if (gameTable.isSinglePlayer()) {
            output.put("Lorenzo", gameTable.getFaithTrailInstance().getLorenzoPosition());
        }

        return output;
    }

    public ArrayList<CardLeader> getLeaderCards(VirtualClient _vClient) {

        String nickname = clientNicknameMap.get(_vClient);
        return gameTable.getPlayerByNickname(nickname).getCardsLeader();
    }

    public ArrayList<CardDevelopment> getTopDevelopmentCards(VirtualClient _vClient) {

        String nickname = clientNicknameMap.get(_vClient);
        PlayerBoard board = gameTable.getPlayerByNickname(nickname);

        return board.getTopDevelopmentCards();
    }

    public void setMainMoveMade(boolean b) {
        mainMoveMade = b;
    }

    // Public action methods to be invoked when a ClientRequest is received

    /**
     * This method is called by a RequestEndTurn's read() method.
     * It calls Controller's advanceTurn() method and responds to the ClientRequest accordingly.
     * Finally, it notifies all players who the new active player has become.
     * @param _vClient The VirtualClient associated with the player that requested to end his turn.
     */
    public void advanceTurn(VirtualClient _vClient) {

        String nickname = clientNicknameMap.get(_vClient);
        PlayerBoard previousPlayer = controller.getTurnController().getActivePlayer();

        try {
            controller.advanceTurn(nickname);
            setMainMoveMade(false);
            send(nickname, new ResponseSuccess(GamePhase.Ended));
            sendAll(new NotifyBriefModel(gameTable.getPlayerByNickname(nickname)));
        } catch (NotActivePlayerException ex) {
            send(nickname, new ResponseNotActivePlayerError());
        }

        if (controller.getGamePhase()==2 && displayStartingEndGame){
            sendAll(new StartingEndGameMessage(previousPlayer.getNickname()));
            displayStartingEndGame=false;
        }

        if (controller.getGamePhase()==3){
            sendAll(new ScoreBoardMessage(controller.calculateScores()));
        } else {
        // Notify new active player that it's his turn to play
        sendAll(
                new RequestSignalActivePlayer(
                        controller.getTurnController().getActivePlayer().getNickname(), GamePhase.Initial));
    }}

    /**
     * This method is called by a RequestBuyDevelopmentCard's read() method.
     * @param _vClient The VirtualClient associated with the player that sent the request.
     * @param _rowIndex The row index of the desired CardDevelopment within the market matrix.
     * @param _colIndex The column index of the desired CardDevelopment within the market matrix.
     * @param _placementIndex The placement index of the selected CardDevelopmentSlot, in which the newly bought card is to placed.
     * @throws InvalidCardDevelopmentPlacementException : thrown if the selected placement index would not consent for a legal placement.
     * @throws InvalidSlotIndexException : thrown if an invalid index was selected as the placement index.
     * @throws NotEnoughResourcesException : thrown if the player does not hold enough resources to buy the desired card.
     * @throws FullSlotException : thrown if the slot at _placementIndex already holds 3 cards.
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to make this action.
     */
    public void buyAndPlaceDevCard(
            VirtualClient _vClient,
            int _rowIndex,
            int _colIndex,
            int _placementIndex)
            throws NotActivePlayerException,
            InvalidCardDevelopmentPlacementException,
            InvalidSlotIndexException,
            NotEnoughResourcesException,
            FullSlotException, MainMoveAlreadyMadeException {

        String nickname = clientNicknameMap.get(_vClient);

        if (!mainMoveMade || debug) {
            controller.buyAndPlaceDevCard(nickname, _rowIndex, _colIndex, _placementIndex);
        } else throw new MainMoveAlreadyMadeException();

    }

    /**
     * This method is called by a RequestMarketUse's read() method.
     * @param _vClient The VirtualClient associated with the player that sent the request.
     * @param _index The index of the selected line from which to retrieve the Marbles.
     * @param _selection Field must be either "row" or "column".
     * @return null if no resources must be discarded after obtaining them from the market, an instance of HashMap<Resource,Integer>
     *         containing the newly obtained resources if one or more resources must be discarded.
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to make this action.
     * @throws IllegalArgumentException : thrown if an invalid _index was selected by the player.
     */
    public HashMap<Resource, Integer> useMarket(VirtualClient _vClient, int _index, String _selection) throws NotActivePlayerException, IllegalArgumentException, MainMoveAlreadyMadeException {

        String nickname = clientNicknameMap.get(_vClient);

        if (!mainMoveMade || debug) {
            return controller.useMarket(nickname, _index, _selection);
        } else throw new MainMoveAlreadyMadeException();

    }

    /**
     * This method is called by a ResponseDiscardResourceSelection's read() method.
     * @param _vClient The VirtualClient associated with the player that sent the request.
     * @param _discardSelection An instance of HashMap containing the amounts to be discarded for each Resource
     * @return null if _discardSelection allowed for the remaining resources to be added to the player's deposit, an instance of HashMap<Resource,Integer>
     *         containing the previously obtained resources if the selection did not allow for the remaining resources to be added to the deposit.
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to make this action.
     */
    public HashMap<Resource, Integer> discardResources(VirtualClient _vClient, HashMap<Resource, Integer> _discardSelection) throws NotActivePlayerException {

        String nickname = clientNicknameMap.get(_vClient);
        return controller.discardResources(nickname, _discardSelection);
    }

    /**
     * This method is called by a RequestActivateCardLeader's read() method.
     * @param _vClient The VirtualClient associated with the player that sent the request.
     * @param _cardToBeActivated The CardLeader to be activated.
     * @return true if CardLeader was successfully activated, false if it could not be activated.
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to make this action.
     */
    public boolean activateLeaderCard(VirtualClient _vClient, CardLeader _cardToBeActivated) throws NotActivePlayerException {

        String nickname = clientNicknameMap.get(_vClient);
        return controller.activateLeaderCard(nickname, _cardToBeActivated);

    }

    /**
     * This method is called by a RequestActivateProduction's read() method.
     * @param _vClient The VirtualClient associated with the player that sent the request.
     * @param _selection An instance of ProductionSelection.
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to make this action.
     * @throws InvalidSlotIndexException : thrown if an invalid index for a CardDevelopmentSlot was selected in _selection
     * @throws NotEnoughResourcesException : thrown if the player does not hold enough resources to activate all of the selected production powers.
     */
    public void activateProductionPowers(VirtualClient _vClient, ProductionSelection _selection)
            throws NotActivePlayerException, InvalidSlotIndexException, NotEnoughResourcesException, MainMoveAlreadyMadeException, CardLeaderRequirementsNotMetException {

        String nickname = clientNicknameMap.get(_vClient);
        if (!mainMoveMade || debug) {
            controller.activateProductionPowers(nickname, _selection);
        } else throw new MainMoveAlreadyMadeException();
    }

    /* Overloaded send method */

    /**
     * Overloaded send() method. This method is used to send a ServerMessage to a VirtualClient.
     * @param virtualClient Target VirtualClient.
     * @param serverMessage Instance of ServerMessage to be sent to target VirtualClient.
     */
    public void send(VirtualClient virtualClient, ServerMessage serverMessage) {
        virtualClient.send(serverMessage);
    }

    /**
     * Overloaded send() method. This method is used to send a ServerMessage to a VirtualClient.
     * @param nickname The nickname bound to the target VirtualClient.
     * @param serverMessage Instance of ServerMessage to be sent to target VirtualClient.
     */
    public void send(String nickname, ServerMessage serverMessage) {
        nicknameClientMap.get(nickname).send(serverMessage);
    }

    /**
     * Overloaded send() method. This method is used to send a ServerMessage to a VirtualClient.
     * @param playerID The ID associated with the target VirtualClient.
     * @param serverMessage Instance of ServerMessage to be sent to target VirtualClient.
     */
    public void send(Integer playerID, ServerMessage serverMessage) {
        idPlayerClientMap.get(playerID).send(serverMessage);
    }

    /* Overloaded sendAndWait method */

    /**
     * Overloaded sendAndWait() method. This method is used to send a ServerMessage to a VirtualClient and block
     * the thread until the Client has sent his response.
     * @param virtualClient Target VirtualClient.
     * @param serverMessage Instance of ServerMessage to be sent to target VirtualClient.
     * @param timeoutInSeconds Amount of seconds the thread will wait for the Client's response.
     *                         If timeoutInSeconds == -1, the thread will wait forever.
     * @throws RequestTimeoutException : thrown if the timeout expires and no response has been received.
     */
    public void sendAndWait(VirtualClient virtualClient, ServerMessage serverMessage, Integer timeoutInSeconds) throws RequestTimeoutException {
        virtualClient.sendAndWait(serverMessage, timeoutInSeconds);
    }

    /**
     * Overloaded sendAndWait() method. This method is used to send a ServerMessage to a VirtualClient and block
     * the thread until the Client has sent his response.
     * @param nickname The nickname bound to the target VirtualClient.
     * @param serverMessage Instance of ServerMessage to be sent to target VirtualClient.
     * @param timeoutInSeconds Amount of seconds the thread will wait for the Client's response.
     *                         If timeoutInSeconds == -1, the thread will wait forever.
     * @throws RequestTimeoutException : thrown if the timeout expires and no response has been received.
     */
    public void sendAndWait(String nickname, ServerMessage serverMessage, Integer timeoutInSeconds) throws RequestTimeoutException {
        nicknameClientMap.get(nickname).sendAndWait(serverMessage, timeoutInSeconds);
    }

    /**
     * Overloaded sendAndWait() method. This method is used to send a ServerMessage to a VirtualClient and block
     * the thread until the Client has sent his response.
     * @param playerID The ID associated with the target VirtualClient.
     * @param serverMessage Instance of ServerMessage to be sent to target VirtualClient.
     * @param timeoutInSeconds Amount of seconds the thread will wait for the Client's response.
     *                         If timeoutInSeconds == -1, the thread will wait forever.
     * @throws RequestTimeoutException : thrown if the timeout expires and no response has been received.
     */
    public void sendAndWait(Integer playerID, ServerMessage serverMessage, Integer timeoutInSeconds) throws RequestTimeoutException {
        idPlayerClientMap.get(playerID).sendAndWait(serverMessage, timeoutInSeconds);
    }

    /**
     * Method used to send an instance of ServerMessage in broadcast to all players.
     * @param serverMessage Instance of ServerMessage to be sent to all players.
     */
    public void sendAll(ServerMessage serverMessage) {
        for (VirtualClient player :
                players) {
            player.send(serverMessage);
        }
    }

    public void discardCardLeader(VirtualClient virtualClient, Integer cardLeaderIndex) {
        controller.discardCardLeader(clientNicknameMap.get(virtualClient), cardLeaderIndex);
    }

    public Marble getSpareMarble() {
        return gameTable.getMarketInstance().getSpareMarble();
    }
}
