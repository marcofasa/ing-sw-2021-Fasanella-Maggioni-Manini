package it.polimi.ingsw.client.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LogoController extends StandardScene {



    public void okButton(ActionEvent actionEvent) {
        GUI.fxmlLoader = new FXMLLoader();
        GUI.fxmlLoader.setLocation(getClass().getResource("/fxml/LogIn.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(GUI.fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GUI.scene=secondScene;

        GUI.primaryStage.setScene(GUI.scene);
        GUI.primaryStage.show();


    }

}
