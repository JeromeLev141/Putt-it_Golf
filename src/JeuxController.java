import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private Jeux jeux;

    public void transfert(Jeux jeux, Stage stage) {
        this.jeux = jeux;
        stp.getChildren().add(0, jeux.jouer(stage));
        stage.setScene(new Scene(stp));
    }

    @FXML
    private void retour() throws IOException {
        jeux.musicStop();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SceneMenu.fxml"));
        Parent option = loader.load();

        MenuController menuController = loader.getController();
        menuController.transfert(jeux);

        Scene scene = new Scene(option, 800, 600);
        Stage stage = (Stage) node1.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void suivant() { jeux.niveauSuivant(); }

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
