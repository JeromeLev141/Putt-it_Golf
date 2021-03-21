import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class ScoreController {

    @FXML
    private Node node1;
    @FXML
    private ListView<String> node2;

    private boolean sonOn = true;
    private boolean musicOn = true;
    private double distance = 50;
    private Color couleur = Color.WHITE;

    public void transfert(boolean sonOn, boolean musicOn, Color couleur, double distance) {
        this.sonOn = sonOn;
        this.musicOn = musicOn;
        this.distance = distance;
        this.couleur = couleur;

        node2.setItems(ScoreChecker.topScores());
    }

    @FXML
    private void retour() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SceneMenu.fxml"));
        Parent option = loader.load();

        MenuController menuController = loader.getController();
        menuController.transfert(sonOn, musicOn, couleur, distance);

        Scene scene = new Scene(option, 800, 600);
        Stage stage = (Stage) node1.getScene().getWindow();
        stage.setScene(scene);
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
