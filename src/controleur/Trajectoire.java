package controleur;

import controleur.utilitaires.Formule;
import javafx.geometry.Point3D;
import modele.Espace3D;
import modele.FormeCordonneSommet;
import modele.Vecteur;

import java.util.ArrayList;
import java.util.List;

public class Trajectoire {


    public static List<Point3D> bougerBalleEspaceTemps(double[] vitesseinitial, Vecteur vecteur, Espace3D espace3D) {
        int positionImpactAvant = -1;
        double forceFrottement;
        boolean angle = false;
        boolean avantNull = false;

        int fgPosition = vecteur.creeSection();
        int fnPosition = vecteur.creeSection();
        int fanglePosition = vecteur.creeSection();

        vecteur.setVecteurVitesseResultant(vitesseinitial);
        List<Point3D> coordonne = new ArrayList<>();
        coordonne.add(new Point3D(vecteur.getPosition()[0],vecteur.getPosition()[1],vecteur.getPosition()[2]));



        do {

            FormeCordonneSommet formeSol = espace3D.detectColisionDansQuelleFormeSol();

            int positionImpact = espace3D.detectionColisionDansQuelleFormeMur();
            double [] fg = Formule.forcegravitationnel(formeSol);
            vecteur.setForceX(fgPosition, fg[0]);
            vecteur.setForceY(fgPosition, fg[1]);
            vecteur.setForceZ(fgPosition, fg[2]);

            if (formeSol != null) {
                if (formeSol.getAngleXY() == 0 && angle)
                    angle = false;

                if (formeSol.getAngleXY() != 0) {
                    if (!angle) {
                        vecteur.setVecteurVitesseResultant(calculerVecteurAngle(vecteur.getVecteurVitesseResultant(), formeSol.getAngleXY(), formeSol.getAngleXZ(), vecteur.getAngleXZ()));
                    }

                    double[] d = new double[]{vecteur.getForceX().get(fgPosition), 0, vecteur.getForceZ().get(fgPosition)};
                    vecteur.setForce(fanglePosition, calculerVecteurAngle(d, formeSol.getAngleXY(), formeSol.getAngleXZ(), vecteur.getAngleXZ()));
                }
                else {
                    vecteur.setForceX(fanglePosition, 0);
                    vecteur.setForceY(fanglePosition, 0);
                    vecteur.setForceZ(fanglePosition, 0);
                }

                if (!formeSol.getTypeSol().isTraversable() && avantNull && vecteur.getVecteurVitesseResultant()[1] < 0) {
                    vecteur.getVecteurVitesseResultant()[1] = 0;
                    vecteur.setForceY(fnPosition, vecteur.getForceY().get(fgPosition) * -1.0D);
                    vecteur.setForceX(fnPosition, 0);
                    vecteur.setForceZ(fnPosition, 0);
                    vecteur.refreshVecteurAccelerationResultant();
                    avantNull = false;
                }

                if (formeSol.getTypeSol().getFrottement() == -1) {
                    coordonne.add(null);
                    return coordonne;
                }
                else if (formeSol.getTypeSol().getFrottement() == -2) {
                    resetBalle(vecteur,fnPosition,fgPosition,espace3D,coordonne);
                    return coordonne;
                }
                else {
                    if (positionImpact != -1 && positionImpact != positionImpactAvant) {
                        Formule.rebondissement(vecteur, positionImpact);
                        positionImpactAvant = positionImpact;
                    }
                }

                vecteur.setForceY(fnPosition, vecteur.getForceY().get(fgPosition) * -1.0D);
                if (vecteur.getVecteurVitesseResultant()[0] <= 0.2 && vecteur.getVecteurVitesseResultant()[0] >= -0.2 &&
                        vecteur.getVecteurVitesseResultant()[2] <= 0.2 && vecteur.getVecteurVitesseResultant()[2] >= -0.2) {
                    forceFrottement = 0.0D;
                    vecteur.setVecteurVitesseResultant(new double[]{0,0,0});
                    vecteur.setForceX(fnPosition, forceFrottement);
                    vecteur.setForceZ(fnPosition, forceFrottement);
                } else {
                    forceFrottement = Formule.forceDeFrottement(formeSol.getTypeSol().getFrottement(), vecteur.getForceY().get(fnPosition));
                    vecteur.setForceX(fnPosition, forceFrottement * Math.cos(Math.toRadians(vecteur.getAngleXZ() + 180.0D)));
                    vecteur.setForceZ(fnPosition, forceFrottement * Math.sin(Math.toRadians(vecteur.getAngleXZ() + 180.0D)));
                }

                if (formeSol.getTypeSol().isTraversable())
                    vecteur.setForceY(fnPosition, -64);

                if (formeSol.getAngleXY() > 0)
                    angle = true;
            }
            else
                vecteur.setForceY(fnPosition, -64);



            vecteur.refreshVecteurAccelerationResultant();
            for(int x = 0; x < 3; ++x) {
                vecteur.getPosition()[x] = Formule.MRUA(vecteur.getPosition()[x], vecteur.getVecteurVitesseResultant()[x], vecteur.getVecteurAccelerationResultant()[x], 0.02);
                vecteur.setVecteurVitesseResultant(x,Formule.MRUA_Vf(vecteur.getVecteurVitesseResultant()[x], vecteur.getVecteurAccelerationResultant()[x], 0.02));
            }

            if (formeSol != null && formeSol.getAngleXY() == 0 && !formeSol.getTypeSol().isTraversable() && vecteur.getVecteurVitesseResultant()[1] == 0){
                double positionY = formeSol.getPositionEspace().getY() + formeSol.getHeight()/2;
                if (positionY > (vecteur.getPosition()[1] - 8) && positionY - (vecteur.getPosition()[1] - 8) < 4){
                    vecteur.getPosition()[1] = positionY + 8;
                }

            }
            Point3D nouvellePosition = new Point3D(vecteur.getPosition()[0],vecteur.getPosition()[1],vecteur.getPosition()[2]);
            espace3D.refreshPositionBalle(nouvellePosition);
            coordonne.add(nouvellePosition);

            if (formeSol == null){
                avantNull = true;
            }

            if (vecteur.getPosition()[1] <= -640) {
                resetBalle(vecteur,fnPosition,fgPosition,espace3D,coordonne);
                return coordonne;
            }

        } while(vecteur.getVecteurAccelerationResultant()[0] <= -0.1 || vecteur.getVecteurAccelerationResultant()[0] >= 0.1 ||
                vecteur.getVecteurAccelerationResultant()[1] != 0 || vecteur.getVecteurAccelerationResultant()[2] >= 0.1 || vecteur.getVecteurAccelerationResultant()[2] <= -0.1);

        return coordonne;
    }

    private static double[] calculerVecteurAngle(double[] vecteur, double angleXYForme,double angleXZForme, double angleXZ){

        double angleXYAjuster;
        angleXYAjuster = angleXZ - angleXZForme;
        angleXYAjuster = Formule.ajustementAngle(angleXYAjuster);
        double hypoAjuster = 64/Math.cos(Math.toRadians(angleXYAjuster));
        double hauteur = Math.tan(Math.toRadians(angleXYForme)) * 64;
        angleXYAjuster = Math.toDegrees(Math.atan(hauteur / hypoAjuster));

        double hypo1 = Math.sqrt(Math.pow(vecteur[0], 2) + Math.pow(vecteur[2], 2));
        double hypo2 = hypo1 * Math.cos(Math.toRadians(angleXYAjuster));
        return new double[]{hypo2 * Math.cos(Math.toRadians(angleXZ)),hypo1 * Math.sin(Math.toRadians(angleXYAjuster)),hypo2 * Math.sin(Math.toRadians(angleXZ))};
    }

    private static void resetBalle(Vecteur vecteur,int fnPosition,int fgPosition,Espace3D espace3D,List<Point3D> coordonne){
        vecteur.setVecteurVitesseResultant(new double[]{0, 0, 0});
        vecteur.setForceY(fnPosition, vecteur.getForceY().get(fgPosition) * -1.0D);
        vecteur.setForceX(fnPosition, 0);
        vecteur.setForceZ(fnPosition, 0);
        vecteur.refreshVecteurAccelerationResultant();
        espace3D.refreshPositionBalle(coordonne.get(0));
        coordonne.add(coordonne.get(0));
        coordonne.add(coordonne.get(0));
    }
}
