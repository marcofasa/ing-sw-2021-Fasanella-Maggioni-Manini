package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameTable;
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
    private HashMap<VirtualClient, String> vClientToNicknames;
    private GameTable gameTable;
    private Controller controller;

    public Game(){
        //inizializza controller
        gameTable = new GameTable(false);
        controller = new Controller(gameTable);
    }

    @Override
    public void run() {
        System.out.println("Game partito");
        start();
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

    public void addAllPlayers(ArrayList<VirtualClient> virtualClients){
        this.players = virtualClients;
    }

    private void start() {
        //inzializza GameTable e Controller
    }
}
