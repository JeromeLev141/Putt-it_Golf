import javafx.geometry.Point3D;
import modele.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main_jeremi{
    public static void main(String[]args){
        Vecteur vecteur = new Vecteur(new double[]{0,0,0});
        vecteur.addforceX(15.0);
        Sol gazon = new Sol(4);

        System.out.println(gazon.getFrottement());
        int position1 = vecteur.addforceX(-5);
        System.out.println(vecteur.getForceX());
        vecteur.addforceX(-7);
        vecteur.addforceX(6);
        vecteur.addforceY(10);
        System.out.println(vecteur.getAngleXZ());
        vecteur.addVitesseX(5);
        System.out.println(vecteur.getAngleXZ());
        vecteur.addVitesseZ(-5);
        System.out.println(vecteur.getVecteurVitesseResultant()[0] + "-" + vecteur.getVecteurVitesseResultant()[1] + "-" + vecteur.getVecteurVitesseResultant()[2]);
        System.out.println(vecteur.getAngleXZ());
        System.out.println(vecteur.getVecteurForceResultant()[0] + "-" + vecteur.getVecteurForceResultant()[1] + "-" + vecteur.getVecteurForceResultant()[2]);
        vecteur.setForceX(position1,-2);
        System.out.println(vecteur.getForceX());
        System.out.println(vecteur.getVecteurForceResultant()[0] + "-" + vecteur.getVecteurForceResultant()[1] + "-" + vecteur.getVecteurForceResultant()[2]);

        FormeCordonneSommet forme = new FormeCordonneSommet(new Point3D(5,-0.5,10),10,1,20, 15,41,4);
        List<FormeCordonneSommet> liste = new ArrayList<>();
        liste.add(forme);
        Espace3D plateforme = new Espace3D(new Point3D(5,0.0039,10),liste,null);// position parfait 0.29

        System.out.println("La balle est dans la forme à la position : " + plateforme.detectColisionDansQuelleFormeSol().getTypeSol().getFrottement());

    }
}