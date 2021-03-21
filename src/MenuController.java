import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private BorderPane bdp;
    @FXML
    private Node node1;
    @FXML
    private Node node2;
    @FXML
    private Node node3;
    @FXML
    private Node node4;

    private boolean sonOn = true;
    private boolean musicOn = true;
    private double distance = 50;
    private Color couleur = Color.WHITE;

    public void transfert(boolean sonOn, boolean musicOn, Color couleur, double distance) {
        this.sonOn = sonOn;
        this.musicOn = musicOn;
        this.distance = distance;
        this.couleur = couleur;
    }

    @FXML
    private void jouer() throws Exception {
        Test3d test = new Test3d();
        test.jouer((Stage) node1.getScene().getWindow());
    }

    @FXML
    private void scoreboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SceneScore.fxml"));
        Parent scoreboard = loader.load();

        ScoreController scoreController = loader.getController();
        scoreController.transfert(sonOn, musicOn, couleur, distance);

        Scene scene = new Scene(scoreboard, 800, 600);
        Stage stage = (Stage) node1.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void option() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SceneOption.fxml"));
        Parent option = loader.load();

        OptionController optionController = loader.getController();
        optionController.transfert(sonOn, musicOn, couleur, distance);

        Scene scene = new Scene(option, 800, 600);
        Stage stage = (Stage) node1.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void quitter() { System.exit(0);}

    @FXML
    private void hover1() { hover(node1);}

    @FXML
    private void hover2() { hover(node2);}

    @FXML
    private void hover3() { hover(node3);}

    @FXML
    private void hover4() { hover(node4);}

    @FXML
    private void hoverFin1() { hoverFin(node1);}

    @FXML
    private void hoverFin2() { hoverFin(node2);}

    @FXML
    private void hoverFin3() { hoverFin(node3);}

    @FXML
    private void hoverFin4() { hoverFin(node4);}

    @FXML
    private void hover(Node node) { node.setStyle(node.getStyle() + "-fx-border-width: 3px;"); }

    @FXML
    private void hoverFin(Node node) {
        node.setStyle(node.getStyle().substring(0, node.getStyle().indexOf("-fx-border-width: 3px;")));
    }
}
