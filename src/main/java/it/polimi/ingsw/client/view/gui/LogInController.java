package it.polimi.ingsw.client.view.gui;


import it.polimi.ingsw.client.ConnectionInfo;
import it.polimi.ingsw.client.IllegalAddressException;
import it.polimi.ingsw.client.IllegalNicknameException;
import it.polimi.ingsw.client.IllegalPortException;
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

    private ConnectionInfo connectionInfo;


    public void loginAction(ActionEvent actionEvent) {
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
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        /*
        if(user_text.compareTo("admin")==0) {
            status_label.setText("admin status");
        }
         */
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
}
