package controleur;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;

public class OptionController extends RetourController{

    @FXML
    private Node node2;
    @FXML
    private Node node3;
    @FXML
    private Node node4;
    @FXML
    private Node node5;

    public void transfert(Jeux jeux) {
        this.jeux = jeux;

      if (!jeux.isSonOn()){
          node1.setStyle( node1.getStyle().replace("-fx-graphic: url(/ressources/images/volume.png);",
                  "-fx-graphic:  url(/ressources/images/volumeferme.png);"));
      }
      if (!jeux.isMusicOn()){
          node2.setStyle( node2.getStyle().replace("-fx-graphic: url(/ressources/images/music.png);",
                  "-fx-graphic:  url(/ressources/images/musicferme.png);"));
      }
      Slider sld = (Slider) node3;
      sld.setValue(jeux.getDistance());
      ColorPicker cp =  (ColorPicker) node4;
      cp.setValue(jeux.getCouleur());
    }

    @FXML
    private void sonStatut() {
        jeux.sonEntre();

        if (jeux.isSonOn()) {
            node1.setStyle( node1.getStyle().replace("-fx-graphic: url(/ressources/images/volume.png);",
                    "-fx-graphic:  url(/ressources/images/volumeferme.png);"));
            jeux.setSonOn(false);
        }
        else {
            node1.setStyle( node1.getStyle().replace("-fx-graphic:  url(/ressources/images/volumeferme.png);",
                    "-fx-graphic: url(/ressources/images/volume.png);"));
            jeux.setSonOn(true);
        }
    }

    @FXML
    private void musicStatut() {
        jeux.sonEntre();

        if (jeux.isMusicOn()) {
            node2.setStyle( node2.getStyle().replace("-fx-graphic: url(/ressources/images/music.png);",
                    "-fx-graphic:  url(/ressources/images/musicferme.png);"));
            jeux.setMusicOn(false);
        }
        else {
            node2.setStyle( node2.getStyle().replace("-fx-graphic:  url(/ressources/images/musicferme.png);",
                    "-fx-graphic: url(/ressources/images/music.png);"));
            jeux.setMusicOn(true);
        }
    }

    @FXML
    private void distanceCamera() {
        Slider sld = (Slider) node3;
        jeux.setDistance(sld.getValue());
    }

    @FXML
    private void CouleurChoisi() {
        jeux.sonEntre();

        ColorPicker cp =  (ColorPicker) node4;
        jeux.setCouleur(cp.getValue());
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
