import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
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
import modele.Balle;
import modele.Plateforme;

import java.io.File;

public class Jeux {

    private boolean sonOn;
    private boolean musicOn;
    private double distance;
    private Color couleur;

    private MediaPlayer son;
    private MediaPlayer music;
    private double tourne;

    private SubScene scene;
    private Balle balle;

    public Jeux() {
        sonOn = true;
        musicOn = true;
        distance = 50;
        couleur = Color.WHITE;

        Media hit = new Media(new File("src/ressources/Puzzle-Dreams.mp3").toURI().toString());
        music = new MediaPlayer(hit);
        music.setVolume(0.2);
        music.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                music.seek(Duration.ZERO);
            }
        });
    }

    public SubScene jouer(Stage stage) {
        music.setMute(!musicOn);

        balle = new Balle(8);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("ressources/images/texture.png"));
        material.setDiffuseColor(couleur);
        balle.setMaterial(material);
        balle.setTranslateY(-40);
        balle.setRotationAxis(Rotate.X_AXIS);

        Group niveau = new Group(prepareMap(Plateforme.getNiveau1()), debut());
        niveau.getChildren().add(balle);

        Camera camera = new PerspectiveCamera();
        scene = new SubScene(niveau, 800, 600,true, SceneAntialiasing.BALANCED);
        scene.setCamera(camera);

        niveau.translateXProperty().set(400);
        niveau.translateYProperty().set(300);
        niveau.getChildren().add(backround(balle));

        camera.setTranslateX(camera.getTranslateX() + 150 - distance);
        camera.setTranslateY(camera.getTranslateY() - 150 + distance);
        camera.getTransforms().addAll(new Rotate(-45, Rotate.Y_AXIS),
                new Rotate(-70, Rotate.X_AXIS), new Rotate(15, Rotate.Z_AXIS));
        camera.translateXProperty().bind(balle.translateXProperty());
        camera.translateZProperty().bind(balle.translateZProperty());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                balle.rotateProperty().set(balle.getRotate() - tourne);
            }
        };

        stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
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
        stage.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> timer.stop());

        music.play();

        return scene;
    }

    public void sonEntre() {
        Media entre = new Media(new File("src/ressources/sons/entre.mp3").toURI().toString());
        son = new MediaPlayer(entre);
        son.setVolume(0.2);
        son.setMute(!sonOn);
        son.play();
    }

    public void sonRetour() {
        Media retour = new Media(new File("src/ressources/sons/retour.mp3").toURI().toString());
        son = new MediaPlayer(retour);
        son.setVolume(0.2);
        son.setMute(!sonOn);
        son.play();
    }

    public void musicStop() {
        music.stop();
    }

    public void niveauSuivant() {
        Group niveau = new Group(prepareMap(Plateforme.getNiveau2()), debut());
        niveau.getChildren().add(balle);
        niveau.translateXProperty().set(400);
        niveau.translateYProperty().set(300);
        niveau.getChildren().add(backround(balle));

        resetBalle();
        scene.setRoot(niveau);
    }

    private void resetBalle() {
        balle.translateXProperty().set(0);
        balle.translateZProperty().set(0);
        balle.translateYProperty().set(-40);
    }

    private ImageView backround(Sphere balle) {
        Image image = new Image("ressources/images/nuages.png");
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
                mat.setDiffuseMap(new Image("ressources/images/patern.png"));
                mat.setDiffuseColor(Color.DARKGREY);
                bloc.setMaterial(mat);
                group.getChildren().add(bloc);
                x++;
            }
            else if (description.charAt(i) == 'v') {
                Box bloc = (Box) prepareBox(x, z);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/patern.png"));
                mat.setDiffuseColor(Color.LIGHTGOLDENRODYELLOW);
                bloc.setMaterial(mat);
                group.getChildren().add(bloc);
                x++;
            }
            else {
                Box bloc = (Box) prepareBox(x, z);
                bloc.setTranslateY(32);
                bloc.setHeight(32);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/patern.png"));
                mat.setDiffuseColor(Color.LIGHTBLUE);
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
        material.setDiffuseMap(new Image("ressources/images/patern.png"));
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

    public boolean isSonOn() { return sonOn; }

    public void setSonOn(boolean sonOn) { this.sonOn = sonOn; }

    public boolean isMusicOn() { return musicOn; }

    public void setMusicOn(boolean musicOn) { this.musicOn = musicOn; }

    public double getDistance() { return distance; }

    public void setDistance(double distance) { this.distance = distance; }

    public Color getCouleur() { return couleur; }

    public void setCouleur(Color couleur) { this.couleur = couleur; }
}
