package controleur;

import controleur.Formule;
import javafx.animation.*;
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
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.*;

import javax.sound.midi.MidiFileFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Jeux {

    private boolean sonOn;
    private boolean musicOn;
    private double distance;
    private Color couleur;

    private int niv;
    private int coups;
    private int coupsTotal;

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
    private List<Point3D> positions;
    private double rotation;
    private Boolean roule;
    private Timeline timeline;
    private int eventProgress;

    private Vecteur vecteur;
    private List<FormeCordonneSommet> mur;
    private List<FormeCordonneSommet> sol;
    private List<FormeCordonneSommet> test;
    private Espace3D espace3D;

    public Jeux() {
        sonOn = true;
        musicOn = true;
        distance = 50;
        couleur = Color.WHITE;

        roule = false;
        timeline = new Timeline();
        eventProgress = 0;

        vecteur = null;
        sol = new ArrayList<>();
        mur = new ArrayList<>();

        Media hit = new Media(new File("src/ressources/Puzzle-Dreams.mp3").toURI().toString());
        music = new MediaPlayer(hit);
        music.setVolume(0.2);
        music.setOnEndOfMedia(() -> music.seek(Duration.ZERO));

        balle = new Balle(8);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("ressources/images/texture.png"));
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
            }
        });

        balle.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            if (!roule && eventProgress == 1) {
                fleche.setVisible(false);

                if (force > 100)
                    force = 100;

                frapper(-force * 2.5 * Math.sin(Math.toRadians(angle)), force * 2.5 * Math.cos(Math.toRadians(angle)));

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
        coupsTotal = 0;

        music.setMute(!musicOn);

        Group niveau = new Group(prepareMap(Plateforme.getNiveau(niv)));
        espace3D = new Espace3D(new Point3D(0,0,0), sol, mur);
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

        niveau.getChildren().add(fleche);

        music.play();
        return scene;
    }

    private void frapper(double x, double z) {
        vecteur = new Vecteur(new Point3D(balle.getTranslateX(), -balle.getTranslateY(), balle.getTranslateZ()));
        positions = bougerBalleEspaceTemps(new double[]{x, 0, z}, vecteur, espace3D);
        rotation = 0;

        coups++;
        coupsTotal++;

        System.out.println("---" + positions.size());
        roule = true;
        avancer();
        System.out.println("fini");
    }

    private void avancer() {
        timeline = new Timeline();
        timeline.setOnFinished(event -> roule = false);
        for (int i = 0; i < positions.size() - 1; i++){
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(20 * (i + 1)),
                    new KeyValue (balle.translateXProperty(), positions.get(i).getX()),
                    new KeyValue(balle.translateZProperty(), positions.get(i).getZ()),
                    new KeyValue (balle.translateYProperty(), -positions.get(i).getY()),
                    new KeyValue(balle.rotationAxisProperty(), getAxeRotation(i)),
                    new KeyValue(balle.rotateProperty(), getRotation(i))));
        }
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
        niv++;

        sol = new ArrayList<>();
        mur = new ArrayList<>();

        Group niveau = new Group(prepareMap(Plateforme.getNiveau(niv)));
        espace3D = new Espace3D(new Point3D(0,0,0), sol, mur);
        niveau.getChildren().addAll(balle, fleche);
        niveau.translateXProperty().set(400);
        niveau.translateYProperty().set(300);
        niveau.getChildren().add(backround(balle));

        resetBalle();
        scene.setRoot(niveau);
    }

    private void resetBalle() {
        timeline.stop();
        roule = false;
        PhongMaterial mat = (PhongMaterial) balle.getMaterial();
        mat.setDiffuseColor(couleur);
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
        int z = -2;

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
                prepareMapForme(mur,x,0,z,0,0,4,64,128,64);
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
            else if (description.charAt(i) == 't') {
                prepareMapForme(sol,x - 1,0,z,0,0,4,96,64,64);
                prepareMapForme(sol,x + 1,0,z,0,0,4,96,64,64);
                prepareMapForme(sol,x,0,z - 1,0,0,4,64,64,96);
                prepareMapForme(sol,x,0,z + 1,0,0,4,64,64,96);
                Box bloc = (Box) prepareBox(x, z);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/patern.png"));
                mat.setBumpMap(new Image("ressources/images/trou.png"));
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

    private Node prepareBox(int x, int z) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("ressources/images/patern.png"));
        Box box = new Box(64, 64, 64);
        box.setMaterial(material);
        box.setTranslateX(x * 64);
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

    public List<FormeCordonneSommet> getMur() {
        return mur;
    }

    public List<FormeCordonneSommet> getSol() {
        return sol;
    }

    public static void prepareMapForme(List<FormeCordonneSommet> liste, int x, int y, int z, int angleXZ, int angleXY, int sol, int widgh, int heigh, int depth){

        FormeCordonneSommet box = new FormeCordonneSommet(new Point3D(x * 64,y,z * 64), widgh, heigh, depth, angleXZ,angleXY,sol );
        liste.add(box);
    }

    public static List<Point3D> bougerBalleEspaceTemps(double[] vitesseinitial, Vecteur vecteur, Espace3D espace3D) {
        int fgPosition = vecteur.creeSection();
        int fnPosition = vecteur.creeSection();
        vecteur.setVecteurVitesseResultant(vitesseinitial);
        List<Point3D> coordonne = new ArrayList<>();

        double forceFrottement = 0.0D;

        do {
            FormeCordonneSommet formeSol = espace3D.detectColisionDansQuelleFormeSol();
            int positionImpact = espace3D.detectionColisionDansQuelleFormeMur();
            double [] fg = Formule.forcegravitationnel(formeSol);
            vecteur.setForceX(fgPosition, fg[0]);
            vecteur.setForceY(fgPosition, fg[1]);
            vecteur.setForceZ(fgPosition, fg[2]);

            if (formeSol == null || formeSol.getTypeSol().isTraversable()){
                vecteur.setForceY(fnPosition,-9.8 * 9.8);
            }
            else{
                if (!formeSol.getTypeSol().isTraversable())
                    vecteur.setForceY(fnPosition, (Double)vecteur.getForceY().get(fgPosition) * -1.0D);
                if (positionImpact != -1)
                    Formule.rebondissement(vecteur,positionImpact);
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
            }

            vecteur.refreshVecteurAccelerationResultant();

            for(int x = 0; x < 3; ++x) {
                vecteur.getPossition()[x] = Formule.MRUA(vecteur.getPossition()[x], vecteur.getVecteurVitesseResultant()[x], vecteur.getVecteurAccelerationResultant()[x], 0.02);
                vecteur.setVecteurVitesseResultant(x,Formule.MRUA_Vf(vecteur.getVecteurVitesseResultant()[x], vecteur.getVecteurAccelerationResultant()[x], 0.02));
            }
            Point3D nouvellePosition = new Point3D(vecteur.getPossition()[0],vecteur.getPossition()[1],vecteur.getPossition()[2]);
            espace3D.refreshPositionBalle(nouvellePosition);
            coordonne.add(nouvellePosition);


        } while(vecteur.getVecteurAccelerationResultant()[0] <= -0.1 || vecteur.getVecteurAccelerationResultant()[0] >= 0.1 ||
                vecteur.getVecteurAccelerationResultant()[1] != 0 || vecteur.getVecteurAccelerationResultant()[2] >= 0.1 || vecteur.getVecteurAccelerationResultant()[2] <= -0.1);

        return coordonne;
    }

}
