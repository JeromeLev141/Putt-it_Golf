package controleur;
import javafx.geometry.Point3D;
import modele.Balle;
import modele.Vecteur;

public class Formule {

    public static double MRUA(double position, double vitesse, double acceleration, double temps) {
        return position + (vitesse * temps + 0.5 * acceleration * Math.pow(temps, 2));
    }

    public static double rebondissement(Vecteur balle, Vecteur structure) {
        return 0;
    }

    public static double forcegravitationnel(){ return 0.045 * 9.8;}
}
