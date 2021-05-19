import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent menu = FXMLLoader.load(getClass().getResource("controleur/scenes/SceneMenu.fxml"));
        Scene scene = new Scene(menu, 800, 600);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Putt-it Golf");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
