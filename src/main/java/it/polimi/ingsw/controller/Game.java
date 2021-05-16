package it.polimi.ingsw.controller;

import com.sun.net.httpserver.Authenticator;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.*;
import it.polimi.ingsw.controller.exceptions.NotActivePlayerException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.VirtualClient;

import java.awt.color.ICC_ColorSpace;
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

    private ArrayList<VirtualClient> players;
    private GameTable gameTable;
    private Controller controller;
    private LinkedHashMap<String, VirtualClient> nicknameClientMap;
    private LinkedHashMap<VirtualClient, String> clientNicknameMap;
    private LinkedHashMap<Integer, VirtualClient> idPlayerClientMap;

    public Game() {
        nicknameClientMap = new LinkedHashMap<>();
        clientNicknameMap = new LinkedHashMap<>();
        idPlayerClientMap = new LinkedHashMap<>();
    }

    public void distributeInitialSelection(VirtualClient _vClient, ArrayList<CardLeader> _cardLeader, Resource _resource1, Resource _resource2) {

        String nickname = clientNicknameMap.get(_vClient);

        try {
            controller.assignInitialBenefits(nickname, _cardLeader, _resource1, _resource2);
            send(_vClient, new ResponseSuccess());
        } catch (NotActivePlayerException ex) {
            send(_vClient, new ResponseNotActivePlayerError());
        }
    }

    @Override
    public void run() {
        System.out.println("Game partito");
        start();
        solicitInitialSelections();
        //System.out.println("debug");
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
        sendAll(new RequestSignalActivePlayer(firstNickname));

    }

    // Public methods to be invoked when a ClientRequest is received

    public void advanceTurn(VirtualClient _vClient) {

        String nickname = clientNicknameMap.get(_vClient);

        try {
            controller.advanceTurn(nickname);
            send(nickname, new ResponseSuccess());
        } catch (NotActivePlayerException ex) {
            send(nickname, new ResponseNotActivePlayerError());
        }

        // Notify new active player that it's his turn to play
        sendAll(
                new RequestSignalActivePlayer(
                        controller.getTurnController().getActivePlayer().getNickname()));
    }

    public void buyAndPlaceDevCard(
            VirtualClient _vClient,
            int _rowIndex,
            int _colIndex,
            int _placementIndex)
            throws NotActivePlayerException,
            InvalidCardDevelopmentPlacementException,
            InvalidSlotIndexException,
            NotEnoughResourcesException,
            FullSlotException {

        String nickname = clientNicknameMap.get(_vClient);

        controller.buyAndPlaceDevCard(nickname, _rowIndex, _colIndex, _placementIndex);

    }

    public HashMap<Resource, Integer> useMarket(VirtualClient _vClient, int _index, String _selection) throws NotActivePlayerException, IllegalArgumentException {

        String nickname = clientNicknameMap.get(_vClient);
        return controller.useMarket(nickname, _index, _selection);
    }

    public HashMap<Resource, Integer> discardResources(VirtualClient _vClient, HashMap<Resource, Integer> _discardSelection) throws NotActivePlayerException {

        String nickname = clientNicknameMap.get(_vClient);
        return controller.discardResources(nickname, _discardSelection);
    }

    public boolean activateLeaderCard(VirtualClient _vClient, CardLeader _cardToBeActivated) throws NotActivePlayerException {

        //TODO : Questo metodo va modificato perche` non funzionera` se riceve come parametro la carta da attivare!

        String nickname = clientNicknameMap.get(_vClient);
        return controller.activateLeaderCard(nickname, _cardToBeActivated);

    }

    public void activateProductionPowers(VirtualClient _vClient, ProductionSelection _selection) throws NotActivePlayerException, InvalidSlotIndexException, NotEnoughResourcesException {

        String nickname = clientNicknameMap.get(_vClient);
        controller.activateProductionPowers(nickname, _selection);
    }

    // Overloaded send method
    public void send(VirtualClient virtualClient, ServerMessage serverMessage) {
        virtualClient.send(serverMessage);
    }

    public void send(String nickname, ServerMessage serverMessage) {
        nicknameClientMap.get(nickname).send(serverMessage);
    }

    public void send(Integer playerID, ServerMessage serverMessage) {
        idPlayerClientMap.get(playerID).send(serverMessage);
    }

    // Overloaded sendAndWait method
    public void sendAndWait(VirtualClient virtualClient, ServerMessage serverMessage, Integer timeoutInSeconds) throws RequestTimeoutException {
        virtualClient.sendAndWait(serverMessage, timeoutInSeconds);
    }

    public void sendAndWait(String nickname, ServerMessage serverMessage, Integer timeoutInSeconds) throws RequestTimeoutException {
        nicknameClientMap.get(nickname).sendAndWait(serverMessage, timeoutInSeconds);
    }

    public void sendAndWait(Integer playerID, ServerMessage serverMessage, Integer timeoutInSeconds) throws RequestTimeoutException {
        idPlayerClientMap.get(playerID).sendAndWait(serverMessage, timeoutInSeconds);
    }

    public void sendAll(ServerMessage serverMessage) {
        for (VirtualClient player :
                players) {
            player.send(serverMessage);
        }
    }
}
