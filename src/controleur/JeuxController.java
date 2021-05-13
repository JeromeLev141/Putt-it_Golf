package controleur;

import controleur.Jeux;
import controleur.MenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class JeuxController {

    @FXML
    private Node node1;
    @FXML
    private Node node2;
    @FXML
    private StackPane stp;
    @FXML
    private GridPane grid;

    private Jeux jeux;

    public void transfert(Jeux jeux, Stage stage) {
        this.jeux = jeux;
        stp.getChildren().add(0, jeux.jouer());
        stage.setScene(new Scene(stp));

        for (int i = 0; i < 9; i++) {
            if (i == 8) {
                Label score = new Label("Score");
                grid.add(score, i, 0);
                GridPane.setHalignment(score, HPos.CENTER);
                GridPane.setValignment(score, VPos.CENTER);
            }
            else {
                Label niveau = new Label("niv. " + (i + 1));
                grid.add(niveau, i, 0);
                GridPane.setHalignment(niveau, HPos.CENTER);
                GridPane.setValignment(niveau, VPos.CENTER);
            }
            grid.add(jeux.getScores().get(i), i,1);
        }
    }

    @FXML
    private void retour() throws IOException {
        jeux.musicStop();
        jeux.sonRetour();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SceneMenu.fxml"));
        Parent option = loader.load();

        MenuController menuController = loader.getController();
        menuController.transfert(jeux);

        Scene scene = new Scene(option, 800, 600);
        Stage stage = (Stage) node1.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void suivant() throws IOException { jeux.niveauSuivant(); }

    @FXML
    private void hover1() { hover(node1);}

    @FXML
    private void hoverFin1() { hoverFin(node1);}

    @FXML
    private void hover2() { hover(node2);}

    @FXML
    private void hoverFin2() { hoverFin(node2);}

    @FXML
    private void hover(Node node) { node.setStyle(node.getStyle() + "-fx-border-width: 3px;"); }

    @FXML
    private void hoverFin(Node node) {
        node.setStyle(node.getStyle().substring(0, node.getStyle().indexOf("-fx-border-width: 3px;")));
    }
}
