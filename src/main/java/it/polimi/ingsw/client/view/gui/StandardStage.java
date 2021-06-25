package it.polimi.ingsw.client.view.gui;


import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class StandardStage {

    private final static Utils utils = new Utils();
    String messages = "";

    public void init(){
    }

    public void printClick(String object){
        System.out.println("Object "+ object+" clicked");
    }

    public void printError(String errorType){
        System.out.println("Error! "+ errorType);
    }

    public String getDialogPane(){
        return messages;
    }

    public void setDialogPane(String text, DialogPane dialogPane){
        messages = messages + "\n" + text;
        messages = messagePolisher(messages);
        if(dialogPane != null) {
            dialogPane.setContentText(messages);
        }
    }

    private String messagePolisher(String messages) {
        String[] messagesList = messages.split("\\r?\\n");
        StringBuilder polishedMessage = new StringBuilder();
        for (String message:
             messagesList) {
            if(!message.strip().trim().equals("")){
                if(!(polishedMessage.toString().equals("")))
                    polishedMessage.append("\n");
                polishedMessage.append(message);
            }
        }
        polishedMessage = checkLimit(polishedMessage);
        return polishedMessage.toString();
    }

    private StringBuilder checkLimit(StringBuilder polishedMessage) {
        String[] messagesList = polishedMessage.toString().split("\\r?\\n");
        StringBuilder cutMessage = new StringBuilder();
        if(messagesList.length > 15){
            for (int i = 0; i < 15; i++) {
                if(!cutMessage.isEmpty())
                    cutMessage.insert(0, "\n");
                cutMessage.insert(0,messagesList[messagesList.length - i - 1]);
            }
            return cutMessage;
        } else {
            return polishedMessage;
        }
    }

    public void printDialog(String s,DialogPane dialogPane){
        dialogPane.setContentText(s);
    }

    public void execute(){
    }

    /**
     * Loads a given path to FXMLLoader
     * @param path String
     * @return loader
     */
    public FXMLLoader load(String path){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        return loader;
    }

    /**
     * Sets the secondary Scene
     * @param loader FXMLLoader
     * @return loaded Scene
     */
    public Scene setScene(FXMLLoader loader){
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return secondScene;
    }

    /**
     * Loads and fits a given image to ImageView Array
     * @param index of array
     * @param path of image
     * @param array of ImageView
     * @param width to fit
     * @param height to fit
     */
    public void setImageToArray(int index,String path,ImageView[] array,int width,int height){
        Image image=new Image(GUI.class.getResourceAsStream(path));
        array[index]=new ImageView(image);

        //Fitting Image
        array[index].setFitWidth(width);
        array[index].setFitHeight(height);

    }

    /**
     * Loads and fits a given image to ImageView Matrix
     * @param row of matrix
     * @param column of matrix
     * @param matrix of ImageView
     * @param path image
     * @param width to fit
     * @param height to fit
     */
    public void setImageToMatrix(int row,int column,ImageView[][] matrix, String path,int width,int height){
        Image image = new Image(GUI.class.getResourceAsStream(path));
        matrix[row][column] = new ImageView(image);

        //Fitting Image
        matrix[row][column].setFitWidth(width);
        matrix[row][column].setFitHeight(height);
    }


    /**
     * Loads and fits a given image to ImageView Matrix with ImageGrid included
     * @param row of matrix
     * @param column of matrix
     * @param matrix of ImageView
     * @param path image
     * @param width to fit
     * @param height to fit
     * @param gridPane to fill
     */
    public static void setImageToMatrix(int row, int column, ImageView[][] matrix, String path, int width, int height, GridPane gridPane){
        Image image = new Image(GUI.class.getResourceAsStream(path));
        matrix[row][column] = new ImageView(image);

        //Fitting Image
        matrix[row][column].setFitWidth(width);
        matrix[row][column].setFitHeight(height);
        gridPane.add(matrix[row][column],column,row);
    }





    /**
     * Showing and Waiting a given Scene
     * @param scene of new window
     */
    public void showStage(Scene scene){
        Stage newWindow = new Stage();
        newWindow.setScene(scene);
        newWindow.setResizable(false);
        newWindow.showAndWait();
    }

    /**
     * Closing Stage
     * @param actionEvent
     */
    public void closeStage(ActionEvent actionEvent){
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets a Standard Matrix of given resource labels
     * @param deposit HashMap
     * @param coin_label
     * @param servant_label
     * @param shield_label
     * @param stone_label
     */
    static void resourceHandler(HashMap<Resource, Integer> deposit, Label coin_label, Label servant_label, Label shield_label, Label stone_label) {
        for(Resource resource: deposit.keySet()){
            switch (resource) {
                case Coins -> coin_label.setText("x" + deposit.get(resource));
                case Servants -> servant_label.setText("x" + deposit.get(resource));
                case Shields -> shield_label.setText("x" + deposit.get(resource));
                case Stones -> stone_label.setText("x" + deposit.get(resource));
            }
        }
    }


    /**
     * Loads and sets Deposit Level and calls loadDepositMatric
     * @param depositLevel
     * @param deposit
     * @param deposit_grid
     * @param resourceMatrix
     */
   static void loadDepositLevels(Boolean[] depositLevel, HashMap<Resource,Integer> deposit, GridPane deposit_grid, ImageView[][] resourceMatrix ) {
       for (Resource resource : deposit.keySet()) {
           if (deposit.get(resource) == 3) {
               loadDepositMatrix(resource, resourceMatrix, 2, 1, 3, deposit_grid);
               depositLevel[2] = true;
           } else if (deposit.get(resource) == 2) {
               if (!depositLevel[1]) {
                   loadDepositMatrix(resource, resourceMatrix, 1, 1, 2, deposit_grid);
                   depositLevel[1] = true;
               } else {
                   loadDepositMatrix(resource, resourceMatrix, 2, 1, 2, deposit_grid);
                   depositLevel[2] = true;
               }
           } else if (deposit.get(resource) == 1) {
               if (!depositLevel[0]) {
                   loadDepositMatrix(resource, resourceMatrix, 0, 2, 1, deposit_grid);
                   depositLevel[0] = true;
               } else if (!depositLevel[1]) {
                   loadDepositMatrix(resource, resourceMatrix, 1, 1, 1, deposit_grid);
                   depositLevel[1] = true;
               } else {
                   loadDepositMatrix(resource, resourceMatrix, 2, 1, 1, deposit_grid);
                   depositLevel[2] = true;
               }
           }
       }
   }

    /**
     * Loads resources to Deposit GridPane
     * @param resource
     * @param resourceMatrix
     * @param row
     * @param startingColumn
     * @param nResources
     * @param gridPane
     */
    private static void loadDepositMatrix(Resource resource, ImageView[][] resourceMatrix, int row, int startingColumn, int nResources, GridPane gridPane) {
        String path = utils.getResourcePath(resource);
        while (nResources > 0) {
            setImageToMatrix(row, startingColumn, resourceMatrix, path, 20, 20, gridPane);
            startingColumn++;
            nResources--;
        }
    }


    /**
     * Sets a Standard Card Leader Array
     * @param cardsLeaderArray arraylist of card leader
     * @param cardLeaderArray ImageView array
     * @param cardleader_grid GridPane
     */
    public void setCardLeaderDeck(ArrayList<CardLeader> cardsLeaderArray,ImageView[] cardLeaderArray,GridPane cardleader_grid) {
        cardLeaderArray = new ImageView[2];
        for (int i = 0; i < 2; i++) {
            if((cardsLeaderArray.size()==1 &&  i==1) || cardsLeaderArray.size()==0 || cardsLeaderArray.get(i)==null){

                //Standard path for empty slot
                String path="/images/CardDevelopment/Card_Development_Empty.png";
                setImageToArray(i,path, cardLeaderArray,50,70);

            }
            else {

                //Generation of image path
                Integer type = getCardLeaderType(cardsLeaderArray.get(i));

                Integer color = getCardLeaderColor(cardsLeaderArray.get(i));

                String path="/images/CardLeader/Card_Leader_"+type.toString()+"-"+ color.toString()+".jpg";

                setImageToArray(i,path, cardLeaderArray,50,70);
                //Adding to GridPane
            }
            cardleader_grid.add(cardLeaderArray[i],i,0);
        }

    }

    /**
     * Gets corresponding number depending on Card Leader Type
     * @param cardLeader
     * @return
     */
    static Integer getCardLeaderType(CardLeader cardLeader){
        return switch (cardLeader.getDescription()) {
            case Deposit -> 2;
            case Discount -> 1;
            case Production -> 4;
            case WhiteMarble -> 3;
        };
    }


    /**
     * Gets corresponding number depending on Card Leader Color
     * @param cardLeader
     * @return
     */
    static Integer getCardLeaderColor(CardLeader cardLeader){
        return  switch (cardLeader.getResource()) {
            case Coins -> 0;
            case Servants -> 1;
            case Shields -> 2;
            case Stones -> 3;
        };
    }

}
