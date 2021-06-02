package it.polimi.ingsw.client.view.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController extends StandardScene{
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
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        if(user_text.compareTo("admin")==0) {
            status_label.setText("admin status");
        }

    }

    @Override
    public void init() {
        super.init();
    }

    public void singlePlayerAction(ActionEvent actionEvent) {

    }


    public String getIp_field() {
        return ip_field.getText();
    }

    public String getPort_field() {
        return port_field.getText();
    }

    public String getUser_field() {
        return user_field.getText();
    }
}