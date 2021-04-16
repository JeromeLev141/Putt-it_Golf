package controleur;

import controleur.Jeux;
import controleur.ScoreChecker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class FinController {

    @FXML
    private VBox vBox;
    @FXML
    private Label node1;
    @FXML
    private TextField node2;

    private Jeux jeux;
    private int score;

    public void transfert(Jeux jeux, int score) {
        this.jeux = jeux;
        this.score = score;

        node1.setText(node1.getText() + score);
        node2.textProperty().addListener((ov, oldValue, newValue) -> {
            if (node2.getText().length() > 8) {
                String s = node2.getText().substring(0, 8);
                node2.setText(s);
            }
        });
    }

    @FXML
    private void retour() throws IOException {
        String nb = String.valueOf(score);
        if (nb.length() == 2)
            nb = "0" + nb;

        String nom = node2.getText();
        if (nom.equals(""))
            nom = "noname";

        ScoreChecker.ecrire(nb + " - " + nom);
        jeux.sonRetour();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SceneMenu.fxml"));
        Parent option = loader.load();

        MenuController menuController = loader.getController();
        menuController.transfert(jeux);

        Scene scene = new Scene(option, 800, 600);
        Stage stage = (Stage) node1.getScene().getWindow();
        stage.setScene(scene);
    }
}
