package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.RequestDiscardResourceSelection;
import it.polimi.ingsw.communication.server.RequestInitialSelection;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is responsible for instantiating the controller and model classes, receiving and dispatching requests
 * from the clients and sending requests and responses to them.
 *
 * This class maintains the correlations between VirtualClient(s) and nickname(s), in order to be able to expose methods
 * parametric in VirtualClient, find the corresponding nickname and call Controller's methods, which are parametric
 * in String.
 */

public class Game implements Runnable{

    private ArrayList<VirtualClient> players;
    private GameTable gameTable;
    private Controller controller;
    private HashMap<String, VirtualClient> nicknameClientMap;
    private HashMap<Integer, VirtualClient> idPlayerClientMap;

    public Game(){
        //inizializza controller
        gameTable = new GameTable(false);
        controller = new Controller(gameTable);
        nicknameClientMap = new HashMap<>();
        idPlayerClientMap = new HashMap<>();
    }

    @Override
    public void run() {
        System.out.println("Game partito");
        start();
        ArrayList<Marble> marbles = new ArrayList<>();
        marbles.add(new MarbleFactory().produce(MarbleType.MarbleBlue, new GameTable(false)));
        marbles.add(new MarbleFactory().produce(MarbleType.MarbleGrey, new GameTable(false)));
        marbles.add(new MarbleFactory().produce(MarbleType.MarblePurple, new GameTable(false)));
        try {
            sendAndWait(players.get(0), new RequestDiscardResourceSelection(marbles), 20);
        } catch (RequestTimeoutException e) {
            System.out.println("scaduto timeout");
            e.printStackTrace();
        }
        //MSF CONTROLLER
        while (true)
            
            ;
    }

    /**
     * get player's virtual client
     * @param index index of the player, starting by 1
     * @return player's virtual client
     */
    public VirtualClient getClientByIndex(Integer index){
        return players.get(index + 1);
    }

    public void addAllPlayers(ArrayList<VirtualClient> virtualClients, ArrayList<String> playersNicknames){
        this.players = new ArrayList<>(virtualClients);
        for (int i = 0; i < virtualClients.size(); i++) {
            VirtualClient virtualClient = virtualClients.get(i);
            idPlayerClientMap.put(virtualClient.getID(), virtualClient);
            nicknameClientMap.put(playersNicknames.get(i), virtualClient);
        }
    }

    private void start() {
        //inzializza controller del primo turno

    }

    // Overloaded send method
    public void send(VirtualClient virtualClient, ServerMessage serverMessage){
        virtualClient.send(serverMessage);
    }

    public void send(String nickname, ServerMessage serverMessage){
        nicknameClientMap.get(nickname).send(serverMessage);
    }

    public void send(Integer playerID, ServerMessage serverMessage){
        idPlayerClientMap.get(playerID).send(serverMessage);
    }

    // Overloaded sendAndWait method
    public void sendAndWait(VirtualClient virtualClient, ServerMessage serverMessage, Integer timeoutInSeconds) throws RequestTimeoutException {
        virtualClient.sendAndWait(serverMessage, timeoutInSeconds);
    }

    public void sendAndWait(String nickname, ServerMessage serverMessage, Integer timeoutInSeconds) throws RequestTimeoutException{
        nicknameClientMap.get(nickname).sendAndWait(serverMessage, timeoutInSeconds);
    }

    public void sendAndWait(Integer playerID, ServerMessage serverMessage, Integer timeoutInSeconds) throws RequestTimeoutException{
        idPlayerClientMap.get(playerID).sendAndWait(serverMessage, timeoutInSeconds);
    }

    public void sendAll(ServerMessage serverMessage){
        for (VirtualClient player :
                players) {
            player.send(serverMessage);
        }
    }
}
