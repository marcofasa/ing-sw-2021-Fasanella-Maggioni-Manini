package it.polimi.ingsw.client.view.gui;


import it.polimi.ingsw.client.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogInController extends StandardStage {
@FXML
    Label status_label;
@FXML
    TextField user_field;
@FXML
    TextField ip_field;
@FXML
    TextField port_field;

@FXML
Label playerNumber_label;

    private ConnectionInfo connectionInfo;
    private int playerNumber;
    private boolean playerNumberRequest=false;

    //SETTERS

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnected(){
        boolean connected = true;
    }

    public void setNickNameUnavailable() {
        status_label.setText("NickName unavailable");
    }

    public void setServerUnreachable(){
        status_label.setText("Server unreachable");
    }

    public void askPlayerNumber() {
        playerNumberRequest=true;
        status_label.setText("STATUS: Server connected");
        playerNumber_label.setText("Choose lobby size!");

    }


    //BUTTONS

    public void playerNumber_button(ActionEvent actionEvent) {
        if (playerNumberRequest){
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/fxml/PlayerNumber.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // New window (Selection)
            showStage(secondScene);
        playerNumber_label.setText("Player Number set");
            status_label.setText("STATUS: Waiting for others player to start");
        playerNumberRequest=false;
        }
        else printError("This button is not available!");
    }


    public void loginAction(ActionEvent actionEvent) {
        if(!playerNumberRequest){
            connectionInfo = new ConnectionInfo();
            try {
                connectionInfo.setNickname(user_field.getText());
                connectionInfo.setPort(Integer.parseInt(port_field.getText()));
                connectionInfo.setAddress(ip_field.getText());
                GUI.setConnectionInfo(connectionInfo);
                Client.connectionSetupSemaphore.release();
            } catch ( NumberFormatException ex) {
                status_label.setText("Invalid port number");
            } catch (IllegalNicknameException | IllegalAddressException | IllegalPortException e) {
                status_label.setText(e.getMessage());
            }
        }
        else printError("You have to choose the lobby size");
    }
}
