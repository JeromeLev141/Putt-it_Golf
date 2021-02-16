import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vue.Menu;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane bdp = new BorderPane();
        Menu.menuPrincipale(bdp);
        Scene scene = new Scene(bdp, 800, 600);

        primaryStage.setTitle("Putt-it Golf");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
