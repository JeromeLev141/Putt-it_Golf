package modele;

import javafx.geometry.Point3D;

import java.util.Vector;

public class Vecteur {

    private Point3D possition;
    private Point3D vecteurForce;
    private Point3D vecteurVitesse;
    private Point3D vecteurAcceleration;

    public Vecteur(int[] positionBalle){
        possition = new Point3D(positionBalle[0],positionBalle[1],positionBalle[2]);
        vecteurForce = new Point3D(0,0,0);
        vecteurVitesse = new Point3D(0,0,0);
        vecteurAcceleration = new Point3D(0,0,0);
    }



}
