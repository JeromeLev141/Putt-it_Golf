package vue;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Menu {

    public static void menuPrincipale(BorderPane bdp) {
        ImageView imageView = new ImageView(new Image("images/test.png"));
        imageView.setFitHeight(20);
        imageView.setFitWidth(40);
        Button test = new Button(null, imageView);
        test.setFocusTraversable(false);
        bdp.setCenter(test);
    }
}
