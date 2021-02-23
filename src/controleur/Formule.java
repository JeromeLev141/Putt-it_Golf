package controleur;

public class Formule {

    public static double MRUA(double position, double vitesse, double frottement, double temps) {
        return position + (vitesse * temps + 0.5 * frottement * Math.pow(temps, 2));
    }

    public static double rebondissement(Vecteur balle, Vecteur structure) {
        return 0;
    }
}
