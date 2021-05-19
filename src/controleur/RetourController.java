package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RetourController {

    @FXML
    public Node node1;

    public Jeux jeux;

    @FXML
    public void retour() throws IOException {
        jeux.sonRetour();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/SceneMenu.fxml"));
        Parent option = loader.load();

        MenuController menuController = loader.getController();
        menuController.transfert(jeux);

        Scene scene = new Scene(option, 800, 600);
        Stage stage = (Stage) node1.getScene().getWindow();
        stage.setScene(scene);
    }
}
