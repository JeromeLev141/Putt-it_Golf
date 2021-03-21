import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Test3d {

    private static final float WIDTH = 800;
    private static final float HEIGHT = 600;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private double tourne;
    MediaPlayer son;

    public void jouer(Stage primaryStage) throws Exception{

        Sphere balle = new Sphere(8);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("images/texture.png"));
        material.setDiffuseColor(Color.WHITE);
        balle.setMaterial(material);
        balle.setTranslateY(-40);
        balle.setRotationAxis(Rotate.X_AXIS);

        Media hit = new Media(new File("src/Puzzle-Dreams.mp3").toURI().toString());
        son = new MediaPlayer(hit);
        son.setVolume(0.2);
        son.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                son.seek(Duration.ZERO);
            }
        });
        son.play();

        String map = new String("xooox\n" +
                "xooox\n" +
                "xoooxxxxxxx\n" +
                "xooooooooox\n" +
                "xooooooooox\n" +
                "xooooooooox\n" +
                "xxxxxxxxxxx");

        Group niveau = new Group(prepareMap(map), debut());
        niveau.getChildren().add(balle);

        Camera camera = new PerspectiveCamera();
        Scene scene = new Scene(niveau, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        niveau.translateXProperty().set(WIDTH / 2);
        niveau.translateYProperty().set(HEIGHT / 2);
        niveau.getChildren().add(backround(balle));

        camera.setTranslateX(camera.getTranslateX() + 100);
        camera.setTranslateY(camera.getTranslateY() - 100);
        camera.getTransforms().addAll(new Rotate(-45, Rotate.Y_AXIS), new Rotate(-70, Rotate.X_AXIS), new Rotate(15, Rotate.Z_AXIS));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                balle.rotateProperty().set(balle.getRotate() - tourne);
            }
        };

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case W :
                    balle.setTranslateZ(balle.getTranslateZ() + 4);
                    balle.setRotationAxis(Rotate.X_AXIS);
                    tourne = 2;
                    timer.start();
                    break;
                case S :
                    balle.setTranslateZ(balle.getTranslateZ() - 4);
                    balle.setRotationAxis(Rotate.X_AXIS);
                    tourne = -2;
                    timer.start();
                    break;
                case A :
                    balle.setTranslateX(balle.getTranslateX() - 4);
                    balle.setRotationAxis(Rotate.Z_AXIS);
                    tourne = 2;
                    timer.start();
                    break;
                case D :
                    balle.setTranslateX(balle.getTranslateX() + 4);
                    balle.setRotationAxis(Rotate.Z_AXIS);
                    tourne = -2;
                    timer.start();
                    break;
                case Q :
                    balle.setTranslateZ(balle.getTranslateZ() + 4);
                    balle.setTranslateX(balle.getTranslateX() - 4);
                    balle.setRotationAxis(new Point3D(4, 0, 4));
                    tourne = 2;
                    timer.start();
                    break;
                case E :
                    balle.setTranslateZ(balle.getTranslateZ() + 4);
                    balle.setTranslateX(balle.getTranslateX() + 4);
                    balle.setRotationAxis(new Point3D(4, 0, -4));
                    tourne = 2;
                    timer.start();
                    break;
                case Z :
                    balle.setTranslateX(balle.getTranslateX() - 4);
                    balle.setTranslateZ(balle.getTranslateZ() - 4);
                    balle.setRotationAxis(new Point3D(4, 0, -4));
                    tourne = -2;
                    timer.start();
                    break;
                case C :
                    balle.setTranslateX(balle.getTranslateX() + 4);
                    balle.setTranslateZ(balle.getTranslateZ() - 4);
                    balle.setRotationAxis(new Point3D(4, 0, 4));
                    tourne = -2;
                    timer.start();
                    break;
            }
        });
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> timer.stop());

        camera.translateXProperty().bind(balle.translateXProperty());
        camera.translateZProperty().bind(balle.translateZProperty());

        primaryStage.setScene(scene);
    }

    private ImageView backround(Sphere balle) {
        Image image = new Image("images/backblurred.png");
        ImageView iv = new ImageView(image);
        iv.setFitHeight(2400);
        iv.setFitWidth(2600);
        //iv.setPreserveRatio(true);
        iv.getTransforms().add(new Translate(-2200, 1400, 1000));
        iv.getTransforms().addAll(new Rotate(-45, Rotate.Y_AXIS), new Rotate(-70, Rotate.X_AXIS), new Rotate(15, Rotate.Z_AXIS));

        iv.translateXProperty().bind(balle.translateXProperty());
        iv.translateZProperty().bind(balle.translateZProperty());

        return iv;
    }

    private Group prepareMap(String description) {
        int x = -2;
        int z = 2;
        Group group = new Group();
        for (int i = 0; i < description.length(); i ++) {
            if (description.charAt(i) == 'o') {
                Node bloc = prepareBox(x, z);
                group.getChildren().add(bloc);
                x++;
            }
            else if (description.charAt(i) == '\n') {
                z++;
                x = -2;
            }
            else if (description.charAt(i) == 'x') {
                Box bloc = (Box) prepareBox(x, z);
                bloc.setHeight(128);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("images/patern.png"));
                mat.setDiffuseColor(Color.DARKGREY);
                bloc.setMaterial(mat);
                group.getChildren().add(bloc);
                x++;
        }
            else if (description.charAt(i) == 'v') {
                Box bloc = (Box) prepareBox(x, z);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("images/patern.png"));
                mat.setDiffuseColor(Color.LIGHTGOLDENRODYELLOW);
                bloc.setMaterial(mat);
                group.getChildren().add(bloc);
                x++;
            }
        }
        return group;
    }

    private Group debut() {
        String des = new String("xxxxx\n" +
                "xooox\n" +
                "xovox\n" +
                "xooox");
        Group group = prepareMap(des);
        group.setTranslateZ(-256);
        return group;
    }

    private Node prepareBox(int x, int z) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("images/patern.png"));
        Box box = new Box(64, 64, 64);
        box.setMaterial(material);
        box.setTranslateX(x * 64);
        box.setTranslateZ(z * 64);
        return box;
    }

    /*private void prepareAnimation(Sphere balle) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                balle.rotateProperty().set(balle.getRotate() - tourne);
            }
        };
        timer.start();
    }*/
}
