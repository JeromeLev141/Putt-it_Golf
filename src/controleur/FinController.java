package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

public class FinController extends RetourController{

    @FXML
    private Label node1;
    @FXML
    private TextField node2;

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
    private void retourPrep() throws IOException {
        jeux.musicStop();

        String nb = String.valueOf(score);
        if (nb.length() == 2)
            nb = "0" + nb;

        String nom = node2.getText();
        if (nom.equals(""))
            nom = "noname";

        ScoreChecker.ecrire(nb + " - " + nom);
        retour();
    }
}
