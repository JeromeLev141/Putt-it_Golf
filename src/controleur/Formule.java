package controleur;
import javafx.geometry.Point3D;
import modele.Balle;
import modele.Forme;
import modele.Vecteur;

public class Formule {

    public static double MRUA(double position, double vitesse, double acceleration, double temps) {
        return position + (vitesse * temps + 0.5 * acceleration * Math.pow(temps, 2));
    }

    public static double MRUA_Vf(double vi, double a, double deltaT){
        return vi + (a * deltaT);
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

    public static void rebondissement(Vecteur balle, int positionImpact){
        double coefficient = 0.85;



        //Top et bottom
        if (positionImpact == 0 || positionImpact == 2) {
            balle.setVecteurVitesseResultant(2, balle.getVecteurVitesseResultant()[2] * -coefficient);
            balle.setVecteurVitesseResultant(0,balle.getVecteurVitesseResultant()[0] * coefficient);
        }
        //droite gauche
        else if (positionImpact == 1 || positionImpact == 3) {
            balle.setVecteurVitesseResultant(0, balle.getVecteurVitesseResultant()[0] * -coefficient);
            balle.setVecteurVitesseResultant(2, balle.getVecteurVitesseResultant()[2] * coefficient);
        }//jerome
        else {
            double angleFinal;
            double diff;
            if (positionImpact == 7) {
                    diff = balle.getAngleXZ() - 135;
                    angleFinal = 315 - diff;
            }
            else if (positionImpact == 4) {
                    diff = balle.getAngleXZ() - 45;
                    angleFinal = 225 - diff;
            }
            else if (positionImpact == 6) {
                diff = balle.getAngleXZ() - 225;
                angleFinal = 45 - diff;
            }
            else {
                diff = balle.getAngleXZ() - 315;
                angleFinal = 135 - diff;
            }
            System.out.println("avant : " + balle.getAngleXZ() + "  Apres : " + angleFinal);
            nouvelleVitesseAngle(balle.getVecteurVitesseResultant(), angleFinal,coefficient);
            balle.setAngleXZ(angleFinal);
        }
/*
        //bas gauche
        else if (positionImpact == 6){
            double angle = balle.getAngleXZ();
            if (angle > 225 && angle < 315) {
                angle = 2 * (315 - balle.getAngleXZ()) + balle.getAngleXZ();
                angle = ajustementAngle(angle);

            }
            else if (angle == 225)
                angle = 45;
            else if (angle > 135 && angle < 225)
                angle = -2 * (balle.getAngleXZ() - 135) + balle.getAngleXZ();

            nouvelleVitesseAngle(balle.getVecteurVitesseResultant(), angle,coefficient);
            balle.setAngleXZ(angle);
        }

        //haut droit
        else if (positionImpact == 4){
            double angle = balle.getAngleXZ();
            if (angle == 45)
                angle = 225;
            else if (angle < 45 || angle > 315){
                if (angle < 45)
                    angle = 225 + (90 - angle + 45);
                if (angle > 315)
                    angle = 225 + (45 - (360-angle));
            }


            nouvelleVitesseAngle(balle.getVecteurVitesseResultant(),angle,coefficient);
            balle.setAngleXZ(angle);
        }*/
    }

    public static double[] forcegravitationnel(Forme forme){
        double[] resultat = new double[3];
        if (forme == null  || forme.getAngleXY() == 0){
            resultat[0] = 0;
            resultat[1] = Balle.getMasse() * -9.8;
            resultat[2] = 0;
            return resultat;
        }else {
            double angle = forme.getAngleXY();
            resultat[1] = (Balle.getMasse() * -9.8) * Math.sin(Math.toRadians(angle));
            double hyp = (/*Balle.getMasse() * -9.8*/-2) * Math.cos(Math.toRadians(angle));
            resultat[0] = hyp * Math.cos(Math.toRadians(forme.getAngleXZ()));
            resultat[2] = hyp * Math.sin(Math.toRadians(forme.getAngleXZ()));
            return resultat;
        }
    }

    public static double forceAcceleration(double force){
        return force / Balle.getMasse();
    }

    public static double forceDeFrottement(double coefficienFrottement, double forceNormal){
        return coefficienFrottement * forceNormal;
    }

    public static double ajustementAngle(double angle){
        if (angle >= 360)
            return angle - 360;
        else if (angle < 0){
            return angle + 360;
        }
        else
            return angle;
    }

    private static void nouvelleVitesseAngle(double [] vitesse, double angleXZ, double coefficient){
        double hypo = Math.sqrt(Math.pow(vitesse[0],2) + Math.pow(vitesse[2],2));
        vitesse[0] = hypo * Math.cos(Math.toRadians(angleXZ)) * coefficient;
        vitesse[2] = hypo * Math.sin(Math.toRadians(angleXZ)) * coefficient;
    }
}
