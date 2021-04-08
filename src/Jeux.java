import controleur.Formule;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private VBox fleche;

    private double xDebut;
    private double yDebut;
    private double force;
    private double angle;

    private Vecteur vecteur;
    private List<FormeCordonneSommet> mur;
    private List<FormeCordonneSommet> sol;
    private Espace3D espace3D;

    public Jeux() {
        sonOn = true;
        musicOn = true;
        distance = 50;
        couleur = Color.WHITE;

        vecteur = null;
        mur = null;
        sol = null;

        Media hit = new Media(new File("src/ressources/Puzzle-Dreams.mp3").toURI().toString());
        music = new MediaPlayer(hit);
        music.setVolume(0.2);
        music.setOnEndOfMedia(() -> music.seek(Duration.ZERO));
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

        niveau.getChildren().add(fleche);

        stage.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            fleche.setVisible(true);

            fleche.setTranslateX(balle.getTranslateX());
            fleche.setTranslateY(balle.getTranslateY());
            fleche.setTranslateZ(balle.getTranslateZ());

            xDebut = mouseEvent.getSceneX();
            yDebut = mouseEvent.getSceneY();

            fleche.getTransforms().add(new Rotate(0,Rotate.X_AXIS));
        });

        stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            angle = Math.atan((mouseEvent.getSceneX() - xDebut) / (mouseEvent.getSceneY() - yDebut)) * 180 / Math.PI + 20;
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
        });

        stage.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            fleche.setVisible(false);

            if (force > 100)
                force = 100;

            frapper(-force * Math.sin(Math.toRadians(angle)), force * Math.cos(Math.toRadians(angle)));

            fleche.getTransforms().clear();
            fleche.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
            corps.setHeight(0);
        });

        music.play();

        return scene;
    }

    public void frapper(double x, double z) {
        balle.setRotationAxis(new Point3D(-z, 0, x));

        TranslateTransition avancer = new TranslateTransition(Duration.seconds(1), balle);
        avancer.setByX(x);
        avancer.setByZ(z);

        RotateTransition rouler = new RotateTransition(Duration.seconds(1), balle);
        rouler.setByAngle((force / (2 * Math.PI * 8)) * 360);

        rouler.play();
        avancer.play();
        avancer.setOnFinished(event -> rouler.stop());
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
        niveau.getChildren().addAll(balle, fleche);
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

        sol = new ArrayList<>();
        mur = new ArrayList<>();

        Group group = new Group();
        for (int i = 0; i < description.length(); i ++) {
            if (description.charAt(i) == 'o') {
                prepareMapForme(sol,x,0,z,0,0,4,64,64,64);
                Node bloc = prepareBox(x, z);
                group.getChildren().add(bloc);
                x++;
            }
            else if (description.charAt(i) == '\n') {
                z++;
                x = -2;
            }
            else if (description.charAt(i) == 'x') {
                prepareMapForme(sol,x,0,z,0,0,4,64,64,64);
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
                prepareMapForme(sol,x,0,z,0,0,4,64,64,64);
                Box bloc = (Box) prepareBox(x, z);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/patern.png"));
                mat.setDiffuseColor(Color.LIGHTGOLDENRODYELLOW);
                bloc.setMaterial(mat);
                group.getChildren().add(bloc);
                x++;
            }
            else {
                prepareMapForme(sol,x,0,z,0,0,4,64,32,64);
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

    private void prepareMapForme(List<FormeCordonneSommet> liste,int x,int y,int z,int angleXZ, int angleXY,int sol,int widgh, int heigh, int depth){

        FormeCordonneSommet box = new FormeCordonneSommet(new Point3D(x * 64,y,z * 64), widgh, heigh, depth, angleXZ,angleXY,sol );
        liste.add(box);
    }

    private void bougerBalleEspaceTemps(double[] vitesseinitial) {
        this.vecteur = new Vecteur(this.balle.getPosition());
        this.espace3D = new Espace3D(this.balle.getPosition(), this.sol, this.mur);
        int fgPosition = this.vecteur.creeSection();
        int fnPosition = this.vecteur.creeSection();

        for(int x = 0; x < vitesseinitial.length; ++x) {
            this.vecteur.setVecteurVitesseResultant(x, vitesseinitial[x]);
        }

        double forceFrottement = 0.0D;

        do {
            Forme formeSol = this.espace3D.detectColisionDansQuelleFormeSol();
            double[] fg = Formule.forcegravitationnel(formeSol);
            this.vecteur.setForceX(fgPosition, fg[0]);
            this.vecteur.setForceY(fgPosition, fg[1]);
            this.vecteur.setForceZ(fgPosition, fg[2]);
            if (formeSol != null && !formeSol.getTypeSol().isTraversable()) {
                this.vecteur.setForceY(fnPosition, (Double)this.vecteur.getForceY().get(fgPosition) * -1.0D);
                if (this.vecteur.getVecteurVitesseResultant()[0] >= 0.01D && this.vecteur.getVecteurVitesseResultant()[0] <= 0.01D && this.vecteur.getVecteurVitesseResultant()[2] >= 0.01D && this.vecteur.getVecteurVitesseResultant()[2] <= 0.01D) {
                    forceFrottement = 0.0D;
                    this.vecteur.setForceX(fnPosition, forceFrottement);
                    this.vecteur.setForceZ(fnPosition, forceFrottement);
                } else {
                    forceFrottement = Formule.forceDeFrottement(formeSol.getTypeSol().getFrottement(), (Double)this.vecteur.getForceY().get(fnPosition));
                    this.vecteur.setForceX(fnPosition, forceFrottement * Math.cos(Math.toRadians(this.vecteur.getAngleXZ() + 180.0D)));
                    this.vecteur.setForceZ(fnPosition, forceFrottement * Math.sin(Math.toRadians(this.vecteur.getAngleXZ() + 180.0D)));
                }
            } else {
                forceFrottement = 0.0D;
                this.vecteur.setForceX(fnPosition, forceFrottement);
                this.vecteur.setForceZ(fnPosition, forceFrottement);
            }

            this.vecteur.refreshVecteurAccelerationResultant();

            for(int x = 0; x < 3; ++x) {
                this.vecteur.getPossition()[x] = Formule.MRUA(this.vecteur.getPossition()[x], this.vecteur.getVecteurVitesseResultant()[x], this.vecteur.getVecteurAccelerationResultant()[x], 0.1D);
                this.vecteur.getVecteurVitesseResultant()[x] = Formule.MRUA_Vf(this.vecteur.getVecteurVitesseResultant()[x], this.vecteur.getVecteurAccelerationResultant()[x], 0.1D);
            }
        } while(this.vecteur.getVecteurAccelerationResultant()[0] >= 0.01D && this.vecteur.getVecteurAccelerationResultant()[0] <= 0.01D && this.vecteur.getVecteurAccelerationResultant()[1] >= 0.0D && this.vecteur.getVecteurAccelerationResultant()[1] <= 0.0D && this.vecteur.getVecteurAccelerationResultant()[2] >= 0.01D && this.vecteur.getVecteurAccelerationResultant()[2] <= 0.01D);

    }
}
