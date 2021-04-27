import javafx.geometry.Point3D;
import modele.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main_jeremi{
    public static void main(String[]args){
        Vecteur vecteur = new Vecteur(new Point3D(0,0,0));
        vecteur.creeSection();
        vecteur.setForceX(0,10.0);
        Sol gazon = new Sol(4);

        System.out.println( "Sol = " + gazon.getFrottement());
        vecteur.setForceY(0,0);
        vecteur.setForceZ(0,10);
        vecteur.refreshVecteurAccelerationResultant();
        System.out.println(vecteur.getVecteurForceResultant()[0] + " : " + vecteur.getVecteurForceResultant()[1] + " : " + vecteur.getVecteurForceResultant()[2]);

        FormeCordonneSommet forme = new FormeCordonneSommet(new Point3D(5,-0.5,10),10,1,20, 15,41,4,false);
        List<FormeCordonneSommet> liste = new ArrayList<>();
        liste.add(forme);
        Espace3D plateforme = new Espace3D(new Point3D(5,0.0039,10),liste,null);// position parfait 0.29

        System.out.println("La balle est dans la forme à la position : " + plateforme.detectColisionDansQuelleFormeSol().getTypeSol().getFrottement());

    }
}