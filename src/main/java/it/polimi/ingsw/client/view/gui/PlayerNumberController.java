package it.polimi.ingsw.client.view.gui;

import javafx.event.ActionEvent;


public class PlayerNumberController extends StandardStage {

    //BUTTONS

    /**
     * Two players button
     * @param actionEvent
     */
    public void two(ActionEvent actionEvent) {
        GUI.setPlayerNumber(2);
        GUI.semaphoreRequest.release();
        closeStage(actionEvent);
    }

    /**
     * Three players button
     * @param actionEvent
     */
    public void three(ActionEvent actionEvent) {
        GUI.setPlayerNumber(3);
        GUI.semaphoreRequest.release();
        closeStage(actionEvent);
    }

    /**
     * four players button
     * @param actionEvent
     */
    public void four(ActionEvent actionEvent) {
        GUI.setPlayerNumber(4);
        GUI.semaphoreRequest.release();
        closeStage(actionEvent);
    }
}
