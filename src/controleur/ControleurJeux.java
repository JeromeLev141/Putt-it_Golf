package controleur;

import modele.Balle;
import modele.Espace3D;
import modele.Plateforme;
import modele.Vecteur;

public class ControleurJeux {

    private Balle balle;
    private Espace3D espace;
    private Vecteur vecteurBalle;

    public ControleurJeux(){

        espace = new Espace3D(balle.getPosition());
        //espace.addForme();

    }
}
