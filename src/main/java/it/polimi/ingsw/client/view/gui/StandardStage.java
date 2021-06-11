package it.polimi.ingsw.client.view.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StandardStage {

    public void init(){
    }

    public void printClick(String object){
        System.out.println("Object "+ object+" clicked");
    }

    public void printError(String errorType){
        System.out.println("Error! "+ errorType);
    }

    public String setDialogPane(String text,DialogPane dialogPane, String messages){
        messages=messages+"\n"+text;
        dialogPane.setContentText(messages);
        return messages;
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
    public void setImageToMatrix(int row, int column, ImageView[][] matrix, String path, int width, int height, GridPane gridPane){
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
}
