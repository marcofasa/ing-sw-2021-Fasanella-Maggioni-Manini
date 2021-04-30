package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.client.PlayersNumber;
import it.polimi.ingsw.communication.client.SetupConnection;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.server.CommandDispatcher;

import java.io.*;
import java.net.Socket;

public class Client {

    private volatile static boolean connected;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket clientSocket;

    public void startConnectionAndListen(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            connected = true;
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            send(new SetupConnection("prova", null));
            ServerMessage inputClass;
            while (connected) {
                try {
                    inputClass = (ServerMessage) inputStream.readObject();
                    CommandDispatcherClient.parseInput(inputClass, this);
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyConnected(){
        System.out.println("Il client è connesso al server");
    }

    public void send(ClientMessage clientMessage){
        try {
            outputStream.reset();
            outputStream.writeObject(clientMessage);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int askPlayersNumber() { /* TODO */
        return 4;
    }

    public static void main(String[] args) {
        Client client = new Client();
        System.out.println("Client has started");
        int port = 25556;
        String ip = "127.0.0.1";
        new Thread(() -> client.startConnectionAndListen(ip,port)).start();
    }

    public void sendPlayersNumber() {
        send(new PlayersNumber(Integer.toString(askPlayersNumber()), null));
    }
}
