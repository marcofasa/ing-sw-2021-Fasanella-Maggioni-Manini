package it.polimi.ingsw.client.view.gui;

import javafx.event.ActionEvent;
import javafx.scene.Scene;


public class LogoController extends StandardStage {



    public void okButton(ActionEvent actionEvent) {
        GUI.fxmlLoader = load("/fxml/LogIn.fxml");
        Scene secondScene = setScene(GUI.fxmlLoader);
        GUI.scene=secondScene;

        GUI.primaryStage.setScene(GUI.scene);
        GUI.primaryStage.show();


    }

}
