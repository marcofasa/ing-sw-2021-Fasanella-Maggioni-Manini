package it.polimi.ingsw.client.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;


public class PlayerNumberController extends StandardStage {
    @FXML
    TextField text_label;

    public void two(ActionEvent actionEvent) {
        GUI.setPlayerNumber(2);
        GUI.semaphoreRequest.release();
        closeStage(actionEvent);
    }

    public void three(ActionEvent actionEvent) {
        GUI.setPlayerNumber(3);
        GUI.semaphoreRequest.release();
        closeStage(actionEvent);
    }

    public void four(ActionEvent actionEvent) {
        GUI.setPlayerNumber(4);
        GUI.semaphoreRequest.release();
        closeStage(actionEvent);
    }
}
