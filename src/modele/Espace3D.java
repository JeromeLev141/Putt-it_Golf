package modele;
import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.List;


public class Espace3D {

    private List<Forme> plateformeSol;
    private List<Forme> plateformeMur;
    private Point3D positionBalle;

    public Espace3D(Point3D balle, List<Forme> sol,List<Forme> mur){
        plateformeSol = sol;
        plateformeMur = mur;
        positionBalle = balle;
    }

    /*
    public void addForme(Forme forme){
        plateformemur.add(forme);
    }
    */

    private boolean detectColisionSol(){
        for (Forme sol:plateformeSol) {
            double distance = sol.getPositionEspace().distance(positionBalle)
        }
    }
}
