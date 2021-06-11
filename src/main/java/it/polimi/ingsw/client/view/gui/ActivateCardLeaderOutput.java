package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class ActivateCardLeaderOutput extends StandardStage{

    private Resource resource;


    public void coin_click(MouseEvent mouseEvent) {
        resource=Resource.Coins;
    }

    public void shield_click(MouseEvent mouseEvent) {
       resource= Resource.Shields;

    }

    public void stone_click(MouseEvent mouseEvent) {
        resource= Resource.Shields;
    }

    public void servant_click(MouseEvent mouseEvent) {
        resource=Resource.Servants;

    }

    public Resource getResource() {
        return resource;
    }

    public void choose(ActionEvent actionEvent) {
        closeStage(actionEvent);
    }
}
