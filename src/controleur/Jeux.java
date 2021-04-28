package controleur;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Point3D;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Jeux {

    private boolean sonOn;
    private boolean musicOn;
    private double distance;
    private Color couleur;

    private int niv;
    private int coups;
    private List<Label> scores;

    private MediaPlayer son;
    private MediaPlayer music;

    private Stage stage;
    private SubScene scene;
    private Balle balle;
    private VBox fleche;
    private Group map;
    private Camera camera;
    private ImageView iv;
    private int rotationMap;
    private Box spawn;
    private Color themeSol;
    private Color themeMur;

    private double xDebut;
    private double yDebut;
    private double force;
    private double angle;
    private List<Point3D> positions;
    private double rotation;
    private Boolean roule;
    private Timeline timeline;
    private int eventProgress;

    private Vecteur vecteur;
    private List<FormeCordonneSommet> mur;
    private List<FormeCordonneSommet> sol;
    private Espace3D espace3D;

    public Jeux() {
        sonOn = true;
        musicOn = true;
        distance = 50;
        couleur = Color.WHITE;

        scores = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Label nb = new Label("" + i);
            nb.setFont(Font.font("OCR A Extended", 12));
            GridPane.setHalignment(nb, HPos.CENTER);
            GridPane.setValignment(nb, VPos.CENTER);
            scores.add(nb);
        }

        roule = false;
        timeline = new Timeline();
        eventProgress = 0;

        balle = new Balle(8);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("ressources/images/textures/texture.png"));
        balle.setMaterial(material);
        balle.setTranslateY(-40);
        balle.setRotationAxis(Rotate.X_AXIS);

        Rectangle corps = new Rectangle(4, 20);
        corps.setFill(Color.RED);
        Polygon pointe = new Polygon();
        pointe.setTranslateX(-3);
        pointe.getPoints().addAll(5.0, 10.0,
                15.0, 10.0,
                10.0, 20.0);
        pointe.setFill(Color.RED);

        fleche = new VBox(corps, pointe);
        fleche.getTransforms().add(new Rotate(90, Rotate.X_AXIS));

        balle.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (!roule && eventProgress == 0) {
                fleche.setVisible(true);

                fleche.setTranslateX(balle.getTranslateX());
                fleche.setTranslateY(balle.getTranslateY());
                fleche.setTranslateZ(balle.getTranslateZ());

                xDebut = mouseEvent.getSceneX();
                yDebut = mouseEvent.getSceneY();

                fleche.getTransforms().add(new Rotate(0,Rotate.X_AXIS));

                eventProgress++;
            }
        });

        balle.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            if (!roule && eventProgress == 1) {

                angle = Math.atan((mouseEvent.getSceneX() - xDebut) / (mouseEvent.getSceneY() - yDebut)) * 180 / Math.PI + 30 + rotationMap;
                if ((mouseEvent.getSceneX() - xDebut) < 0 && (mouseEvent.getSceneY() - yDebut) < 0) {
                    fleche.getTransforms().set(1, new Rotate(-180 + angle, Rotate.Z_AXIS));
                    angle -= 180;
                }
                else if ((mouseEvent.getSceneX() - xDebut) > 0 && (mouseEvent.getSceneY() - yDebut) < 0) {
                    fleche.getTransforms().set(1, new Rotate(180 + angle, Rotate.Z_AXIS));
                    angle += 180;
                }
                else
                    fleche.getTransforms().set(1, new Rotate(angle, Rotate.Z_AXIS));

                force = Math.sqrt(Math.pow(mouseEvent.getSceneX() - xDebut, 2) + Math.pow(mouseEvent.getSceneY() - yDebut, 2)) / 2;
                if (force < 100)
                    corps.setHeight(force);
            }
        });

        balle.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
                    if (!roule && eventProgress == 1) {
                        fleche.setVisible(false);

                        if (force > 100)
                    force = 100;

                frapper(-force * 2.5 * Math.sin(Math.toRadians(angle)), force * 2.5 * Math.cos(Math.toRadians(angle)));
                force = 0;
                angle = 0;

                fleche.getTransforms().clear();
                fleche.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
                corps.setHeight(0);

                eventProgress--;
            }
        });
    }

    public SubScene jouer() {
        resetBalle();
        niv = 1;
        coups = 0;
        for (int i = 0; i < 9; i++) {
            scores.get(i).setText("0");
        }

        vecteur = null;
        sol = new ArrayList<>();
        mur = new ArrayList<>();

        Media hit = new Media(new File("src/ressources/musics/music1.mp3").toURI().toString());
        music = new MediaPlayer(hit);
        music.setVolume(0.2);
        music.setOnEndOfMedia(() -> music.seek(Duration.ZERO));
        music.setMute(!musicOn);
        music.play();

        rotationMap = 0;
        themeSol = Color.WHITE;
        themeMur = Color.DARKGREY;

        map = new Group(prepareMap(Plateforme.getNiveau(niv)));
        map.getChildren().addAll(balle, fleche);
        rotationMap = 0;

        Group niveau = new Group(map);
        espace3D = new Espace3D(new Point3D(0,0,0), sol, mur);

        camera = new PerspectiveCamera();
        scene = new SubScene(niveau, 800, 600,true, SceneAntialiasing.BALANCED);
        scene.setCamera(camera);

        niveau.translateXProperty().set(400);
        niveau.translateYProperty().set(300);

        iv = backround(balle);
        niveau.getChildren().add(iv);

        camera.setTranslateX(camera.getTranslateX() + 150 - distance);
        camera.setTranslateY(camera.getTranslateY() - 150 + distance);
        camera.getTransforms().addAll(new Rotate(-45, Rotate.Y_AXIS),
                new Rotate(-70, Rotate.X_AXIS), new Rotate(15, Rotate.Z_AXIS));
        camera.translateXProperty().bind(balle.translateXProperty());
        camera.translateZProperty().bind(balle.translateZProperty());

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                if (!roule && eventProgress == 0) {
                    Rotate rotate = new Rotate();
                    rotate.setAxis(Rotate.Y_AXIS);
                    rotate.setAngle(90);
                    map.getTransforms().add(rotate);

                    rotationMap += 90;
                    if (rotationMap != 360) {
                        if (rotationMap == 90) {
                            camera.translateXProperty().bind(balle.translateZProperty());
                            camera.translateZProperty().bind(balle.translateXProperty().negate());
                            iv.translateXProperty().bind(balle.translateZProperty());
                            iv.translateZProperty().bind(balle.translateXProperty().negate());
                        }
                        else if (rotationMap == 180) {
                            camera.translateXProperty().bind(balle.translateXProperty().negate());
                            camera.translateZProperty().bind(balle.translateZProperty().negate());
                            iv.translateXProperty().bind(balle.translateXProperty().negate());
                            iv.translateZProperty().bind(balle.translateZProperty().negate());
                        }
                        else {
                            camera.translateXProperty().bind(balle.translateZProperty().negate());
                            camera.translateZProperty().bind(balle.translateXProperty());
                            iv.translateXProperty().bind(balle.translateZProperty().negate());
                            iv.translateZProperty().bind(balle.translateXProperty());
                        }
                    }
                    else {
                        rotationMap = 0;
                        camera.translateXProperty().bind(balle.translateXProperty());
                        camera.translateZProperty().bind(balle.translateZProperty());
                        iv.translateXProperty().bind(balle.translateXProperty());
                        iv.translateZProperty().bind(balle.translateZProperty());
                    }
                }
            }
        });

        return scene;
    }

    private void frapper(double x, double z) {
        vecteur = new Vecteur(new Point3D(balle.getTranslateX(), -balle.getTranslateY(), balle.getTranslateZ()));
        positions = bougerBalleEspaceTemps(new double[]{x, 0, z}, vecteur, espace3D);
        rotation = 0;

        coups++;
        scores.get(niv - 1).setText("" + coups);

        System.out.println("---" + positions.size());
        roule = true;
        avancer();
        System.out.println("fini");
    }

    private void avancer() {
        timeline = new Timeline();
        timeline.setOnFinished(event -> {
            roule = false;
            if (positions.get(positions.size()-1) == null) {
                try {
                    niveauSuivant();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        for (int i = 0; i < positions.size() - 1; i++){
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(20 * (i + 1)),
                    new KeyValue (balle.translateXProperty(), positions.get(i).getX()),
                    new KeyValue(balle.translateZProperty(), positions.get(i).getZ()),
                    new KeyValue (balle.translateYProperty(), -positions.get(i).getY()),
                    new KeyValue(balle.rotationAxisProperty(), getAxeRotation(i)),
                    new KeyValue(balle.rotateProperty(), getRotation(i))));
        }
        sonCoup();
        timeline.play();
    }

    private double getRotation(int i) {
        if (i == 0)
            return  rotation;
        else return rotation += (Math.sqrt(Math.pow(positions.get(i).getZ() - positions.get(i - 1).getZ(), 2) +
                Math.pow(positions.get(i).getX() - positions.get(i - 1).getX(), 2)) / (2 * Math.PI * 8)) * 360;
    }

    private Point3D getAxeRotation(int i) {
        if (i == 0)
            return balle.getRotationAxis();
        else return new Point3D(-(positions.get(i).getZ() - positions.get(i - 1).getZ()), 0,
                    positions.get(i).getX() - positions.get(i - 1).getX());
    }

    private void sonCoup() {
        Media entre = new Media(new File("src/ressources/sons/coup.mp3").toURI().toString());
        son = new MediaPlayer(entre);
        son.setVolume(0.2);
        son.setMute(!sonOn);
        son.play();
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

    public void niveauSuivant() throws IOException {
        switch (coups) {
            case 1 : scores.get(8).setText("" + (Integer.parseInt(scores.get(8).getText()) + 100));
                break;
            case 2 : scores.get(8).setText("" + (Integer.parseInt(scores.get(8).getText()) + 80));
                break;
            case 3 : scores.get(8).setText("" + (Integer.parseInt(scores.get(8).getText()) + 60));
                break;
            case 4 : scores.get(8).setText("" + (Integer.parseInt(scores.get(8).getText()) + 50));
                break;
            case 5 : scores.get(8).setText("" + (Integer.parseInt(scores.get(8).getText()) + 30));
                break;
            default : scores.get(8).setText("" + (Integer.parseInt(scores.get(8).getText()) + 10));
        }

        if (niv == 2) {
            music.stop();
            Media hit = new Media(new File("src/ressources/musics/music2.mp3").toURI().toString());
            music = new MediaPlayer(hit);
            music.setVolume(0.2);
            music.setOnEndOfMedia(() -> music.seek(Duration.ZERO));
            music.setMute(!musicOn);
            music.play();

            themeSol = Color.BISQUE;
            themeMur = Color.DARKGREEN;
        }
        else if (niv == 4) {
            music.stop();
            Media hit = new Media(new File("src/ressources/musics/music3.mp3").toURI().toString());
            music = new MediaPlayer(hit);
            music.setVolume(0.2);
            music.setOnEndOfMedia(() -> music.seek(Duration.ZERO));
            music.setMute(!musicOn);
            music.play();

            themeSol = Color.SKYBLUE;
            themeMur = Color.BLACK;
        }
        else if (niv == 6) {
            music.stop();
            Media hit = new Media(new File("src/ressources/musics/music4.mp3").toURI().toString());
            music = new MediaPlayer(hit);
            music.setVolume(0.2);
            music.setOnEndOfMedia(() -> music.seek(Duration.ZERO));
            music.setMute(!musicOn);
            music.play();

            themeSol = Color.ORANGE;
            themeMur = Color.RED;
        }
        else if (niv == 8) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SceneFin.fxml"));
            Parent fin = loader.load();

            FinController finController = loader.getController();
            finController.transfert(this, Integer.parseInt(scores.get(8).getText()));

            Scene scene = new Scene(fin, 800, 600);
            stage.setScene(scene);
        }

        niv++;
        coups = 0;

        vecteur = null;
        sol = new ArrayList<>();
        mur = new ArrayList<>();

        rotationMap = 0;

        map = new Group(prepareMap(Plateforme.getNiveau(niv)));
        map.getChildren().addAll(balle, fleche);
        Group niveau = new Group(map);

        espace3D = new Espace3D(new Point3D(0,0,0), sol, mur);
        niveau.translateXProperty().set(400);
        niveau.translateYProperty().set(300);

        iv = backround(balle);
        niveau.getChildren().add(iv);

        camera.translateXProperty().bind(balle.translateXProperty());
        camera.translateZProperty().bind(balle.translateZProperty());
        iv.translateXProperty().bind(balle.translateXProperty());
        iv.translateZProperty().bind(balle.translateZProperty());

        resetBalle();
        scene.setRoot(niveau);
    }

    public void resetBalle() {
        timeline.stop();
        roule = false;
        PhongMaterial mat = (PhongMaterial) balle.getMaterial();
        mat.setDiffuseColor(couleur);
        if (spawn != null) {
            balle.translateXProperty().set(spawn.getTranslateX());
            balle.translateZProperty().set(spawn.getTranslateZ());
        }
        else {
            balle.translateXProperty().set(0);
            balle.translateZProperty().set(0);
        }
        balle.translateYProperty().set(-40);
    }

    private ImageView backround(Sphere balle) {
        Image image;
        if (niv == 3 || niv == 4)
            image = new Image("ressources/images/backs/montagnes.png");
        else if (niv == 5 || niv == 6)
            image = new Image("ressources/images/backs/techno.png");
        else if (niv == 7 || niv == 8)
            image = new Image("ressources/images/backs/wasteland.png");
        else
            image = new Image("ressources/images/backs/nuages.png");
        ImageView iv = new ImageView(image);
        iv.setFitHeight(2000);
        iv.setFitWidth(2600);
        iv.getTransforms().add(new Translate(-2100, 1500, 700));
        iv.getTransforms().addAll(new Rotate(-45, Rotate.Y_AXIS), new Rotate(-70, Rotate.X_AXIS), new Rotate(15, Rotate.Z_AXIS));
        iv.translateXProperty().bind(balle.translateXProperty());
        iv.translateZProperty().bind(balle.translateZProperty());
        return iv;
    }

    private Group prepareMap(String description) {
        int x = -2;
        int y = 0;
        int z = -2;

        Group group = new Group();
        for (int i = 0; i < description.length(); i ++) {
            if (description.charAt(i) == 'o' || description.charAt(i) == '|' || description.charAt(i) == '_' || description.charAt(i) == 'L'
            || description.charAt(i) == 'b' || description.charAt(i) == 'g') {
                Box bloc = (Box) prepareBox(x, y, z);

                if (description.charAt(i) == 'b') {
                    PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                    mat.setDiffuseMap(new Image("ressources/images/textures/boost.gif"));
                    mat.setDiffuseColor(Color.SADDLEBROWN);
                    bloc.setMaterial(mat);
                    prepareMapForme(sol,x,y,z,0,0,3,64,64,64);
                }
                else if (description.charAt(i) == 'g') {
                    PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                    mat.setDiffuseMap(new Image("ressources/images/textures/glace.png"));
                    mat.setDiffuseColor(Color.ALICEBLUE);
                    bloc.setMaterial(mat);
                    prepareMapForme(sol,x,y,z,0,0,2,64,64,64);
                }
                else prepareMapForme(sol,x,y,z,0,0,4,64,64,64);
                if (description.charAt(i) == '|')
                    prepareMapForme(mur,x,y+1,z,0,0,4,48,140,80);
                if (description.charAt(i) == '_')
                    prepareMapForme(mur,x,y+1,z,0,0,4,80,140,48);
                if (description.charAt(i) == 'L')
                    prepareMapForme(mur,x,y+1,z,0,0,4,48,140,48);

                group.getChildren().add(bloc);
                x++;
            }
            else if (description.charAt(i) == 'j') {
                Box bloc = (Box) prepareBox(x, y, z);
                i++;
                //vers l'avant
                if (description.charAt(i) == '1') {
                    bloc.setHeight(96);
                    bloc.setTranslateZ(bloc.getTranslateZ() + 20);
                    bloc.setTranslateY(bloc.getTranslateY() - 40);
                    bloc.getTransforms().add(new Rotate(-45, Rotate.X_AXIS));
                }

                //vers l'arriere
                if (description.charAt(i) == '3') {
                    bloc.setHeight(96);
                    bloc.setTranslateZ(bloc.getTranslateZ() - 20);
                    bloc.setTranslateY(bloc.getTranslateY() - 40);
                    bloc.getTransforms().add(new Rotate(45, Rotate.X_AXIS));
                }

                //vers la droite
                if (description.charAt(i) == '2') {
                    bloc.setWidth(96);
                    bloc.setTranslateX(bloc.getTranslateX() + 20);
                    bloc.setTranslateY(bloc.getTranslateY() - 40);
                    bloc.getTransforms().add(new Rotate(-45, Rotate.Z_AXIS));
                }

                //vers la gauche
                if (description.charAt(i) == '4') {
                    bloc.setWidth(96);
                    bloc.setTranslateX(bloc.getTranslateX() - 20);
                    bloc.setTranslateY(bloc.getTranslateY() - 40);
                    bloc.getTransforms().add(new Rotate(45, Rotate.Z_AXIS));
                }

                group.getChildren().add(bloc);
                x++;
            }
            else if (description.charAt(i) == '\n') {
                z++;
                x = -2;
            }
            else if (description.charAt(i) == 'x') {
                prepareMapForme(mur,x,y,z,0,0,4,64,128,64);
                Box bloc = (Box) prepareBox(x, y, z);
                bloc.setHeight(128);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/textures/patern.png"));
                mat.setDiffuseColor(themeMur);
                bloc.setMaterial(mat);
                group.getChildren().add(bloc);
                x++;
            }
            else if (description.charAt(i) == 'v') {
                prepareMapForme(sol,x,y,z,0,0,4,64,64,64);
                spawn = (Box) prepareBox(x, y, z);
                PhongMaterial mat = (PhongMaterial) spawn.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/textures/patern.png"));
                mat.setDiffuseColor(Color.LIGHTGOLDENRODYELLOW);
                spawn.setMaterial(mat);
                group.getChildren().add(spawn);
                x++;
            }
            else if (description.charAt(i) == 't') {

                prepareMapForme(sol,x,y,z,0,0,5,64,16,64);
                prepareMapForme(sol,x,y,z,0,0,1,16,64,16);
                prepareMapForme(sol,x,y,z,0,0,1,64,60,64);
                prepareMapForme(sol,x,y,z,0,0,4,64,64,64);
                prepareMapForme(mur,x+1,y,z,0,0,4,64,76,64);
                prepareMapForme(mur,x,y,z+1,0,0,4,64,76,64);
                prepareMapForme(mur,x-1,y,z,0,0,2,64,76 ,64);
                prepareMapForme(mur,x,y,z-1,0,0,2,64,76 ,64);
                Box bloc = (Box) prepareBox(x, y, z);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/textures/patern.png"));
                mat.setBumpMap(new Image("ressources/images/textures/trou.png"));
                bloc.setMaterial(mat);
                group.getChildren().add(bloc);
                x++;
            }
            else if (description.charAt(i) == '+')
                y++;
            else if (description.charAt(i) == '-')
                y--;
            else if (description.charAt(i) == '0')
                x++;
            else if (description.charAt(i) == '1' || description.charAt(i) == '2' ||
                    description.charAt(i) == '3' || description.charAt(i) == '4') {

                Box bloc = (Box) prepareBox(x, y, z);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseColor(themeMur);
                bloc.setMaterial(mat);
                bloc.setDepth(90);
                bloc.setHeight(112);


                //coin haut droite
                if (description.charAt(i) == '1') {
                    bloc.setTranslateZ(bloc.getTranslateZ() + 24);
                    bloc.setTranslateX(bloc.getTranslateX() + 24);
                    bloc.getTransforms().add(new Rotate(-45, Rotate.Y_AXIS));
                }

                //coin haut gauche
                if (description.charAt(i) == '2') {
                    bloc.setTranslateZ(bloc.getTranslateZ() + 24);
                    bloc.setTranslateX(bloc.getTranslateX() - 24);
                    bloc.getTransforms().add(new Rotate(45, Rotate.Y_AXIS));
                }

                //coin bas gauche
                if (description.charAt(i) == '3') {
                    bloc.setTranslateZ(bloc.getTranslateZ() - 24);
                    bloc.setTranslateX(bloc.getTranslateX() - 24);
                    bloc.getTransforms().add(new Rotate(-45, Rotate.Y_AXIS));
                }

                //coin bas droite
                if (description.charAt(i) == '4') {
                    bloc.setTranslateZ(bloc.getTranslateZ() - 24);
                    bloc.setTranslateX(bloc.getTranslateX() + 24);
                    bloc.getTransforms().add(new Rotate(45, Rotate.Y_AXIS));
                }

                group.getChildren().add(bloc);
            }
            else {
                prepareMapForme(sol,x,y,x,0,0,6,80,32,80);
                prepareMapForme(sol,x,y,z,0,0,1,64,48,64);

                Box bloc = (Box) prepareBox(x, y, z);
                bloc.setTranslateY(bloc.getTranslateY() + 16);
                bloc.setHeight(48);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/textures/eau.gif"));
                mat.setDiffuseColor(Color.SKYBLUE);
                bloc.setMaterial(mat);
                group.getChildren().add(bloc);
                x++;
            }
        }
        return group;
    }

    private Node prepareBox(int x, int y, int z) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(themeSol);
        material.setDiffuseMap(new Image("ressources/images/textures/patern.png"));
        Box box = new Box(64, 64, 64);
        box.setMaterial(material);
        box.setTranslateX(x * 64);
        box.setTranslateY(y * 64);
        box.setTranslateZ(z * 64);
        return box;
    }

    public boolean isSonOn() { return sonOn; }

    public void setSonOn(boolean sonOn) { this.sonOn = sonOn; }

    public boolean isMusicOn() { return musicOn; }

    public void setMusicOn(boolean musicOn) { this.musicOn = musicOn; }

    public double getDistance() { return distance; }

    public void setDistance(double distance) { this.distance = distance; }

    public Color getCouleur() { return couleur; }

    public void setCouleur(Color couleur) { this.couleur = couleur; }

    public List<Label> getScores() { return scores; }

    public void setStage(Stage stage) { this.stage = stage; }

    public List<FormeCordonneSommet> getMur() {
        return mur;
    }

    public List<FormeCordonneSommet> getSol() {
        return sol;
    }

    public static void prepareMapForme(List<FormeCordonneSommet> liste, int x, int y, int z, int angleXZ, int angleXY, int sol, int widgh, int heigh, int depth){

        FormeCordonneSommet box = new FormeCordonneSommet(new Point3D(x * 64,-y*64,z * 64), widgh, heigh, depth, angleXZ,angleXY,sol );
        liste.add(box);
    }

    public static List<Point3D> bougerBalleEspaceTemps(double[] vitesseinitial, Vecteur vecteur, Espace3D espace3D) {
        int fgPosition = vecteur.creeSection();
        int fnPosition = vecteur.creeSection();
        vecteur.setVecteurVitesseResultant(vitesseinitial);
        List<Point3D> coordonne = new ArrayList<>();
        coordonne.add(new Point3D(vecteur.getPossition()[0],vecteur.getPossition()[1],vecteur.getPossition()[2]));

        int positionImpactAvant = -1;
        int decompte = 0;
        double forceFrottement = 0.0D;

        do {

            FormeCordonneSommet formeSol = espace3D.detectColisionDansQuelleFormeSol();


            int positionImpact = espace3D.detectionColisionDansQuelleFormeMur();
            double [] fg = Formule.forcegravitationnel(formeSol);
            vecteur.setForceX(fgPosition, fg[0]);
            vecteur.setForceY(fgPosition, fg[1]);
            vecteur.setForceZ(fgPosition, fg[2]);

            if (formeSol != null && !formeSol.getTypeSol().isTraversable()){
                vecteur.getVecteurVitesseResultant()[1] = 0;
                vecteur.setForceY(fnPosition, (Double)vecteur.getForceY().get(fgPosition) * -1.0D);
                vecteur.setForceX(fnPosition,0);
                vecteur.setForceZ(fnPosition,0);
                vecteur.refreshVecteurAccelerationResultant();
            }
            if (formeSol == null || formeSol.getTypeSol().isTraversable()){
                //System.out.println(" vole");
                vecteur.setForceY(fnPosition, -64);
            }
            else if (formeSol.getTypeSol().getFrottement() == -1) {
                System.out.println("But");
                coordonne.add(null);
                return coordonne;
            }
            else if (formeSol.getTypeSol().getFrottement() == -2){
                System.out.println("eau");
                vecteur.setVecteurVitesseResultant(new double[]{0, 0, 0});
                vecteur.setForceY(fnPosition, (Double)vecteur.getForceY().get(fgPosition) * -1.0D);
                vecteur.setForceX(fnPosition,0);
                vecteur.setForceZ(fnPosition,0);
                vecteur.refreshVecteurAccelerationResultant();
                espace3D.refreshPositionBalle(coordonne.get(0));
                positionImpactAvant = -1;
                coordonne.add(coordonne.get(0));
                coordonne.add(coordonne.get(0));
                return coordonne;
            }

            //else if (formeSol.getTypeSol().getFrottement() == -2) {
                /*
                if (decompte == 20) {
                Point3D nouvellePosition = coordonne.get(0);
                espace3D.refreshPositionBalle(nouvellePosition);
                coordonne.add(nouvellePosition);
                coordonne.add(new Point3D(0,0,0));
                return coordonne;
                }
                else decompte++;*/
            //}
            else{

                if (positionImpact != -1){
                    if (positionImpact != positionImpactAvant) {
                        System.out.println("avant = " + positionImpactAvant + " nouvelle =" + positionImpact);
                        Formule.rebondissement(vecteur, positionImpact);
                        positionImpactAvant = positionImpact;
                    }
                }
                if (!formeSol.getTypeSol().isTraversable())
                    vecteur.setForceY(fnPosition, (Double)vecteur.getForceY().get(fgPosition) * -1.0D);

                if (vecteur.getVecteurVitesseResultant()[0] <= 0.2 && vecteur.getVecteurVitesseResultant()[0] >= -0.2 &&
                        vecteur.getVecteurVitesseResultant()[2] <= 0.2 && vecteur.getVecteurVitesseResultant()[2] >= -0.2) {
                    forceFrottement = 0.0D;
                    vecteur.setVecteurVitesseResultant(new double[]{0,0,0});
                    vecteur.setForceX(fnPosition, forceFrottement);
                    vecteur.setForceZ(fnPosition, forceFrottement);
                } else {
                    forceFrottement = Formule.forceDeFrottement(formeSol.getTypeSol().getFrottement(), (Double)vecteur.getForceY().get(fnPosition));
                    vecteur.setForceX(fnPosition, forceFrottement * Math.cos(Math.toRadians(vecteur.getAngleXZ() + 180.0D)));
                    vecteur.setForceZ(fnPosition, forceFrottement * Math.sin(Math.toRadians(vecteur.getAngleXZ() + 180.0D)));
                }
                if (decompte > 0)
                    decompte++;
            }

            vecteur.refreshVecteurAccelerationResultant();

            for(int x = 0; x < 3; ++x) {
                vecteur.getPossition()[x] = Formule.MRUA(vecteur.getPossition()[x], vecteur.getVecteurVitesseResultant()[x], vecteur.getVecteurAccelerationResultant()[x], 0.02);
                vecteur.setVecteurVitesseResultant(x,Formule.MRUA_Vf(vecteur.getVecteurVitesseResultant()[x], vecteur.getVecteurAccelerationResultant()[x], 0.02));
            }



            Point3D nouvellePosition = new Point3D(vecteur.getPossition()[0],vecteur.getPossition()[1],vecteur.getPossition()[2]);
            espace3D.refreshPositionBalle(nouvellePosition);
            coordonne.add(nouvellePosition);

            if (vecteur.getPossition()[1] <= -300) {
                vecteur.setVecteurVitesseResultant(new double[]{0, 0, 0});
                vecteur.setForceY(fnPosition, (Double)vecteur.getForceY().get(fgPosition) * -1.0D);
                vecteur.setForceX(fnPosition,0);
                vecteur.setForceZ(fnPosition,0);
                vecteur.refreshVecteurAccelerationResultant();
                espace3D.refreshPositionBalle(coordonne.get(0));
                positionImpactAvant = -1;
                coordonne.add(coordonne.get(0));
                coordonne.add(coordonne.get(0));
                return coordonne;
            }

        } while(vecteur.getVecteurAccelerationResultant()[0] <= -0.1 || vecteur.getVecteurAccelerationResultant()[0] >= 0.1 ||
                vecteur.getVecteurAccelerationResultant()[1] != 0 || vecteur.getVecteurAccelerationResultant()[2] >= 0.1 || vecteur.getVecteurAccelerationResultant()[2] <= -0.1);

        return coordonne;
    }
}
