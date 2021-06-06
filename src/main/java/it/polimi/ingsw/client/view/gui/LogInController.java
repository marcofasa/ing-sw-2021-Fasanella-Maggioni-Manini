package it.polimi.ingsw.client.view.gui;


import it.polimi.ingsw.client.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController extends StandardScene{
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
    private boolean connected=false;
    private int playerNumber;
    private boolean playerNumberRequest=false;


    public void loginAction(ActionEvent actionEvent) {
        if(!playerNumberRequest){
        connectionInfo = new ConnectionInfo();
        boolean invalid = true;
        while (invalid) {
            try {
                connectionInfo.setNickname(user_field.getText());
                connectionInfo.setPort(Integer.parseInt(port_field.getText()));
                connectionInfo.setIP(ip_field.getText());
                invalid = false;
            } catch (IllegalNicknameException | IllegalAddressException | IllegalPortException ignore) {
            }
        }

        GUI.setConnectionInfo(connectionInfo);
        Client.semaphore.release();}
        else printError("You have to choose the lobby size");


    }


    @Override
    public void init() {
        super.init();
    }

    public void singlePlayerAction(ActionEvent actionEvent) {

    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnected(){
        connected=true;
    }

    public void setNickNameUnavailable() {
        status_label.setText("NickName unavailable");
    }

    public void setServerUnreachable(){
        status_label.setText("Server unreachable");
    }

    public void askPlayerNumber() {
    playerNumberRequest=true;
        playerNumber_label.setText("Choose lobby size!");
    }


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
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        //newWindow.setX(primaryStage.getX() + 200);
        //newWindow.setY(primaryStage.getY() + 100);
        PlayerNumberController playerNumberController =loader.getController();
        newWindow.showAndWait();
        playerNumberRequest=false;
        }
        else printError("This button is not available!");
    }
}
