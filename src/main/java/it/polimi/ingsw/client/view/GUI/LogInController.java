package it.polimi.ingsw.client.view.GUI;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

public class LogInController {
@FXML
Label status_label;
@FXML
    TextField user_field;
@FXML
    TextField ip_field;
@FXML
    TextField port_field;


    public void loginAction(ActionEvent actionEvent) {
        String user_text=user_field.getText();
        String ip_text=ip_field.getText();
        String port_text=port_field.getText();
        if(user_text.compareTo("admin")==0) {
            status_label.setText("admin status");
        }
    }
}
