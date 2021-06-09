package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class InitialSelectionController extends StandardStage {


    @FXML
    GridPane initialCardLeader_grid;


    private int pNumber = -1;
    private ImageView[] cardLeaderArray;
    private int nRow = 4;
    private ArrayList<CardLeader> cardSelection;
    private ArrayList<CardLeader> cardsLeader;
    private ArrayList<Resource> resourceSelection;
    private int resourceSize = -1;


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

    public void setPlayerNumber(int pNumber) {
        this.pNumber = pNumber;
    }

    public void resourceSelection(ActionEvent actionEvent) {
        printClick("resource button");
        if (pNumber < 0) {
            printError("Not right time to choose resources!");
            resourceSelection.clear();
        } else {
            if (pNumber == 0) {
                resourceSize = 0;
            } else if (pNumber == 1 || pNumber == 2) {
                resourceSize = 1;
            } else resourceSize = 2;
            if (resourceSelection.size() == resourceSize) {
                printClick("resource selection button");
                GUI.resourceList = resourceSelection;
                GUI.semaphoreRequest.release();
            } else {
                printError("Not enough / Too much resources picked! You have to choose " + resourceSize + " resources.");
                resourceSelection.clear();
            }
        }
    }

    public void setCardLeaderDeck(ArrayList<CardLeader> cardsLeader) {
        this.cardsLeader = cardsLeader;
        cardSelection = new ArrayList<>();
        resourceSelection = new ArrayList<>(10);
        cardLeaderArray = new ImageView[4];
        for (int i = 0; i < nRow; i++) {


            Integer type;
            switch (cardsLeader.get(i).getDescription()) {
                case Deposit:
                    type = 2;
                    break;
                case Discount:
                    type = 1;
                    break;
                case Production:
                    type = 4;
                    break;
                default:
                    type = 3;
            }

            Integer color;
            switch (cardsLeader.get(i).getResource()) {
                case Coins:
                    color = 0;
                    break;
                case Servants:
                    color = 1;
                    break;
                case Shields:
                    color = 2;
                    break;
                case Stones:
                    color = 3;
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + cardsLeader.get(i).getResource().toString());
            }

            //REAL PATH
            String path = "/images/CardLeader/Card_Leader_" + type.toString() + "-" + color.toString() + ".jpg";

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


    public void setResources(int playerNumber) {
        // resourceSelection=new ArrayList<>(10);
        this.pNumber = playerNumber;
        if (pNumber == 0) {
            resourceSize = 0;
        } else if (pNumber == 1 || pNumber == 2) {
            resourceSize = 1;
        } else resourceSize = 2;
    }


    public ArrayList<CardLeader> getCardLeaderSelection() {
        return cardSelection;
    }

    public ArrayList<Resource> getResourceSelection() {
        return resourceSelection;
    }
}
