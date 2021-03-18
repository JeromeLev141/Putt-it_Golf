import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OptionController {

    @FXML
    private Node node1;
    @FXML
    private Node node2;
    @FXML
    private Node node3;
    @FXML
    private Node node4;
    @FXML
    private Node node5;

    private boolean sonOn = true;
    private boolean musicOn = true;

    public void transfert(boolean sonOn, boolean musicOn) {
      this.sonOn = sonOn;
      this.musicOn = musicOn;
      if (!this.sonOn){
          node1.setStyle( node1.getStyle().replace("-fx-graphic: url(/images/volume.png);",
                  "-fx-graphic:  url(/images/volumeferme.png);"));
      }
      if (!this.musicOn){
          node2.setStyle( node2.getStyle().replace("-fx-graphic: url(/images/music.png);",
                  "-fx-graphic:  url(/images/musicferme.png);"));
      }
    }

    @FXML
    private void retour() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SceneMenu.fxml"));
        Parent option = loader.load();

        MenuController menuController = loader.getController();
        menuController.transfert(sonOn, musicOn);

        Scene scene = new Scene(option, 800, 600);
        Stage stage = (Stage) node1.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void sonStatut() {
        if (sonOn) {
            node1.setStyle( node1.getStyle().replace("-fx-graphic: url(/images/volume.png);",
                    "-fx-graphic:  url(/images/volumeferme.png);"));
            sonOn = false;
        }
        else {
            node1.setStyle( node1.getStyle().replace("-fx-graphic:  url(/images/volumeferme.png);",
                    "-fx-graphic: url(/images/volume.png);"));
            sonOn = true;
        }
    }

    @FXML
    private void musicStatut() {
        if (musicOn) {
            node2.setStyle( node2.getStyle().replace("-fx-graphic: url(/images/music.png);",
                    "-fx-graphic:  url(/images/musicferme.png);"));
            musicOn = false;
        }
        else {
            node2.setStyle( node2.getStyle().replace("-fx-graphic:  url(/images/musicferme.png);",
                    "-fx-graphic: url(/images/music.png);"));
            musicOn = true;
        }
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
    private void hover5() { hover(node5); }

    @FXML
    private void hoverFin1() { hoverFin(node1);}

    @FXML
    private void hoverFin2() { hoverFin(node2);}

    @FXML
    private void hoverFin3() { hoverFin(node3);}

    @FXML
    private void hoverFin4() { hoverFin(node4);}

    @FXML
    private void hoverFin5() { hoverFin(node5);}

    @FXML
    private void hover(Node node) { node.setStyle(node.getStyle() + "-fx-border-width: 3px;"); }

    @FXML
    private void hoverFin(Node node) {
        node.setStyle(node.getStyle().substring(0, node.getStyle().indexOf("-fx-border-width: 3px;")));
    }
}
