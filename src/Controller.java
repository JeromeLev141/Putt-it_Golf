import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    private Button bouton1;
    @FXML
    private Button bouton2;
    @FXML
    private Button bouton3;
    @FXML
    private Button bouton4;

    @FXML
    private void jouer() { System.out.println("jouer");}

    @FXML
    private void scoreboard() { System.out.println("score");}

    @FXML
    private void option() { System.out.println("option");}

    @FXML
    private void quitter() { System.exit(0);}

    @FXML
    private void hover1() { hover(bouton1);}

    @FXML
    private void hover2() { hover(bouton2);}

    @FXML
    private void hover3() { hover(bouton3);}

    @FXML
    private void hover4() { hover(bouton4);}

    @FXML
    private void hoverFin1() { hoverFin(bouton1);}

    @FXML
    private void hoverFin2() { hoverFin(bouton2);}

    @FXML
    private void hoverFin3() { hoverFin(bouton3);}

    @FXML
    private void hoverFin4() { hoverFin(bouton4);}

    @FXML
    private void hover(Button bouton) {
        bouton.setStyle(bouton.getStyle() + "-fx-border-width: 3px;");
    }

    @FXML
    private void hoverFin(Button bouton) {
        bouton.setStyle(bouton.getStyle().substring(0, bouton.getStyle().indexOf("-fx-border-width: 3px;")));
    }
}
