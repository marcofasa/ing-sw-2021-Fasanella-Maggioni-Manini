package it.polimi.ingsw.client.view.gui;

import javafx.event.ActionEvent;
import javafx.scene.Scene;


public class LogoController extends StandardStage {



    //Just the starting game button
    public void okButton(ActionEvent actionEvent) {
        GUI.fxmlLoader = load("/fxml/LogIn.fxml");
        GUI.scene= setScene(GUI.fxmlLoader);

        GUI.primaryStage.setScene(GUI.scene);
        GUI.primaryStage.show();


    }

}
