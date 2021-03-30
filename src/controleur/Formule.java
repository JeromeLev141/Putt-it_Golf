package controleur;
import javafx.geometry.Point3D;
import modele.Balle;
import modele.Vecteur;

public class Formule {

    public static double MRUA(double position, double vitesse, double acceleration, double temps) {
        return position + (vitesse * temps + 0.5 * acceleration * Math.pow(temps, 2));
    }

    public static double rebondissement(Vecteur balle, Vecteur structure) {
        if (structure.getAngleXZ() == 90) {
            if (balle.getAngleXZ() == 0)
                return 180;
            else if (balle.getAngleXZ() == 180)
                return 0;
            else if (balle.getAngleXZ() < 180)
                return 180 - balle.getAngleXZ();
            else if (balle.getAngleXZ() < 360)
                return 540 - balle.getAngleXZ();
        }
        else {
            if (balle.getAngleXZ() == 90)
                return 270;
            else if (balle.getAngleXZ() == 270)
                return 90;
            else
                return 360 - balle.getAngleXZ();
        }
        return 0;
    }

    public static double forcegravitationnel(){ return 0.045 * 9.8;}

    //public static double
}
