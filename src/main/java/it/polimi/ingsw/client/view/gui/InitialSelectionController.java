package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class InitialSelectionController extends StandardStage {


    @FXML
    GridPane initialCardLeader_grid;

    @FXML
    Label resources_label;

    @FXML
    Label wait_label;


    private int pNumber = -1;
    private ImageView[] cardLeaderArray;
    private ArrayList<CardLeader> cardSelection;
    private ArrayList<Resource> resourceSelection;
    private int resourceSize = -1;

    //SETTERS

    public void setPlayerNumber(int pNumber) {
        this.pNumber = pNumber;
        setResources(pNumber);
    }


    /**
     * Sets card leader deck for scene
     * @param cardsLeader to choose from (size()==4)
     */
    public void setCardLeaderDeck(ArrayList<CardLeader> cardsLeader) {
        cardSelection = new ArrayList<>();
        resourceSelection = new ArrayList<>(10);
        cardLeaderArray = new ImageView[4];
        int nRow = 4;
        for (int i = 0; i < nRow; i++) {

            Integer type = switch (cardsLeader.get(i).getDescription()) {
                case Deposit -> 2;
                case Discount -> 1;
                case Production -> 4;
                default -> 3;
            };

            Integer color = switch (cardsLeader.get(i).getResource()) {
                case Coins -> 0;
                case Servants -> 1;
                case Shields -> 2;
                case Stones -> 3;
            };

            String path = "/images/CardLeader/Card_Leader_" + type + "-" + color + ".jpg";
            setImageToArray(i,path,cardLeaderArray,80,120);

            //Mouse Click Event
            int finalI = i;
            ColorAdjust monochrome = new ColorAdjust();
            monochrome.setSaturation(-1);

            cardLeaderArray[i].setOnMouseClicked(mouseEvent -> {
                if (cardSelection.contains(cardsLeader.get(finalI))) {
                    cardSelection.remove(cardsLeader.get(finalI));
                    cardLeaderArray[finalI].setEffect(null);
                } else {
                    cardSelection.add(cardsLeader.get(finalI));
                    cardLeaderArray[finalI].setEffect(monochrome);
                }
            });

            //Adding to GridPane
            initialCardLeader_grid.add(cardLeaderArray[i], i, 0);
        }
    }


    /**
     * Sets the number of resources depending on the player number
     * @param playerNumber of current player
     */
    public void setResources(int playerNumber) {
        this.pNumber = playerNumber;
        setResourcesSlave();
    }

    private void setResourcesSlave() {
        if (pNumber == 0) {
            resourceSize = 0;
            resources_label.setText("You haven't resources to choose");
        } else if (pNumber == 1 || pNumber == 2) {
            resourceSize = 1;
            resources_label.setText("You have 1 resource to choose");
        } else {
            resourceSize = 2;
            resources_label.setText("You have 2 resources to choose");
        }
    }


    //SELECTION BUTTONS

    /**
     * Resource initial selection button
     */
    public void resourceSelection(ActionEvent actionEvent) {
        printClick("resource button");
        if (pNumber < 0) {
            printError("Not right time to choose resources!");
            resourceSelection.clear();
        } else {
            setResourcesSlave();
            if (resourceSelection.size() == resourceSize) {
                printClick("resource selection button");
                GUI.resourceList = resourceSelection;
                wait_label.setText("Wait for other players selection...");
                GUI.semaphoreRequest.release();
            } else {
                printError("Not enough / Too much resources picked! You have to choose " + resourceSize + " resources.");
                resourceSelection.clear();
            }
        }
    }

    /**
     * Card Leader Selection button
     */
    public void cardLeaderSelection(ActionEvent actionEvent) {
        if (cardSelection.size() != 2) {
            printError("Not enough / Too much cards selected, try again!");
            cardSelection.clear();
        } else {
            printClick("card leader button");
            GUI.cardLeaderList = cardSelection;
            GUI.semaphoreRequest.release();
        }
    }


    //RESOURCES BUTTONS

    public void coinClick(MouseEvent mouseEvent) {
        printClick("coin");
        if (resourceSelection == null) {
            resourceSelection = new ArrayList<>();
        }
        resourceSelection.add(Resource.Coins);
    }

    public void stoneClick(MouseEvent mouseEvent) {
        printClick("stone");
        if (resourceSelection == null) {
            resourceSelection = new ArrayList<>();
        }
        resourceSelection.add(Resource.Stones);
    }

    public void servantClick(MouseEvent mouseEvent) {
        printClick("servant");
        if (resourceSelection == null) {
            resourceSelection = new ArrayList<>();
        }
        resourceSelection.add(Resource.Servants);
    }

    public void shieldClick(MouseEvent mouseEvent) {
        printClick("shield");
        if (resourceSelection == null) {
            resourceSelection = new ArrayList<>();
        }
        resourceSelection.add(Resource.Shields);
    }


}
