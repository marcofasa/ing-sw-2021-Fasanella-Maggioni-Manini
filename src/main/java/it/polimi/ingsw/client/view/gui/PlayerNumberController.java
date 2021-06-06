package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class PlayerNumberController extends StandardScene{
    @FXML
    TextField text_label;

    public void two(ActionEvent actionEvent) {
        GUI.setPlayerNumber(2);
        GUI.semaphoreRequest.release();
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void three(ActionEvent actionEvent) {
        GUI.setPlayerNumber(3);
        GUI.semaphoreRequest.release();
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void four(ActionEvent actionEvent) {
        GUI.setPlayerNumber(4);
        GUI.semaphoreRequest.release();
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
