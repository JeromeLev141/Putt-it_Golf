package modele;
import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.List;


public class Espace3D {

    private List<Forme> plateforme;
    private Point3D positionBalle;

    public Espace3D(Point3D balle){
        plateforme = new ArrayList<>();
        positionBalle = balle;
    }

    public void addForme(Forme forme){
        plateforme.add(forme);
    }

    /*
    private boolean detectColision(){

    }
    */
}
