package it.polimi.ingsw.client;

import java.io.*;
import java.net.*;

public class ClientDummy {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        out.println(msg);
        String resp = null;
        try {
            resp = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }


    public void stopConnection() {
        try {
            in.close();
        out.close();
        clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}