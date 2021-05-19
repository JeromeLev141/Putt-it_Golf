package controleur;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;

public class ScoreController extends RetourController {

    @FXML
    private ListView<String> node2;

    public void transfert(Jeux jeux) {
        this.jeux = jeux;

        node2.setItems(ScoreChecker.topScores());
        node2.getStylesheets().add("ressources/listCell.css");
    }

    @FXML
    private void hover1() { hover(node1);}

    @FXML
    private void hoverFin1() { hoverFin(node1);}

    @FXML
    private void hover(Node node) { node.setStyle(node.getStyle() + "-fx-border-width: 3px;"); }

    @FXML
    private void hoverFin(Node node) {
        node.setStyle(node.getStyle().substring(0, node.getStyle().indexOf("-fx-border-width: 3px;")));
    }
}
