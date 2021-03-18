import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Controller {

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

    @FXML
    private void jouer() { System.out.println("jouer");}

    @FXML
    private void scoreboard() { System.out.println("score");}

    @FXML
    private void option() throws IOException {
        bdp.setCenter(FXMLLoader.load(getClass().getResource("SceneOption.fxml")));
    }

    @FXML
    private void quitter() { System.exit(0);}

    @FXML
    private void sonStatut() {
        if (node1.getStyle().contains("-fx-graphic: url(/images/volume.png);"))
            node1.setStyle( node1.getStyle().replace("-fx-graphic: url(/images/volume.png);",
                    "-fx-graphic:  url(/images/volumeferme.png);"));
        else
            node1.setStyle( node1.getStyle().replace("-fx-graphic:  url(/images/volumeferme.png);",
                    "-fx-graphic: url(/images/volume.png);"));
    }

    @FXML
    private void musicStatut() {
        if (node2.getStyle().contains("-fx-graphic: url(/images/music.png);"))
            node2.setStyle( node2.getStyle().replace("-fx-graphic: url(/images/music.png);",
                    "-fx-graphic:  url(/images/musicferme.png);"));
        else
            node2.setStyle( node2.getStyle().replace("-fx-graphic:  url(/images/musicferme.png);",
                    "-fx-graphic: url(/images/music.png);"));
    }

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
