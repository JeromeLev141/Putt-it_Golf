import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class JeuxController {

    @FXML
    private Node node1;

    private Jeux jeux;

    public void transfert(Jeux jeux, Stage stage) {
        this.jeux = jeux;

        StackPane stp = new StackPane();
        stp.getChildren().add(jeux.jouer(stage));
        stp.getChildren().add(node1);

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
