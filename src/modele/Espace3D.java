package modele;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;


public class Espace3D {


    private List<FormeCordonneSommet> plateformeSol;
    private List<FormeCordonneSommet> plateformeMur;
    private Point3D positionBalle;
    private FormeCordonneSommet formeOuEstLaBalle;

    public Espace3D(Point3D balle, List<FormeCordonneSommet> sol,List<FormeCordonneSommet> mur){
        plateformeSol = sol;
        plateformeMur = mur;
        positionBalle = balle;
        formeOuEstLaBalle = detectColisionDansQuelleFormeSol();
    }

    public Sol colisionSolMateriaux(){
        return formeOuEstLaBalle.getTypeSol();
    }

    public Point3D getPositionBalle() {
        return positionBalle;
    }

    public List<FormeCordonneSommet> getPlateformeSol() {
        return plateformeSol;
    }

    public void refreshPositionBalle(Point3D nouvellePosition){
        this.positionBalle = nouvellePosition;
        formeOuEstLaBalle = detectColisionDansQuelleFormeSol();
    }

    public int detectionColisionDansQuelleFormeMur(){
        Point2D position1 = new Point2D(positionBalle.getX() + 8,positionBalle.getZ());
        Point2D position2 = new Point2D(positionBalle.getX() + 4,positionBalle.getZ() - 4);
        Point2D position3 = new Point2D(positionBalle.getX(),positionBalle.getZ() - 8);
        Point2D position4 = new Point2D(positionBalle.getX() - 4,positionBalle.getZ() - 4);
        Point2D position5 = new Point2D(positionBalle.getX() - 8,positionBalle.getZ());
        Point2D position6 = new Point2D(positionBalle.getX() - 4,positionBalle.getZ() + 4);
        Point2D position7 = new Point2D(positionBalle.getX() ,positionBalle.getZ() + 8);
        Point2D position8 = new Point2D(positionBalle.getX() + 4,positionBalle.getZ() + 4);
        Point2D [] tableaudroit = new Point2D[]{position7,position1,position3,position5};
        Point2D [] tableauangle = new Point2D[]{position8,position2,position4,position6};


        for (FormeCordonneSommet mur : plateformeMur)
            if (mur.getAngleXZ() == 0) {//Calcule colision mur en angle droit
                for (int x = 0; x < tableaudroit.length; x++)
                    if (mur.getAngleXZTableau()[0].angle(tableaudroit[x], mur.getAngleXZTableau()[1]) <= 90 && mur.getAngleXZTableau()[0].angle(tableaudroit[x], mur.getAngleXZTableau()[3]) <= 90)
                        if (mur.getAngleXZTableau()[2].angle(tableaudroit[x], mur.getAngleXZTableau()[1]) <= 90 && mur.getAngleXZTableau()[2].angle(tableaudroit[x], mur.getAngleXZTableau()[3]) <= 90) {
                            Point2D positionXY = new Point2D(tableaudroit[x].getX(), positionBalle.getY());
                            if (mur.getAngleXYTableau()[0].angle(positionXY, mur.getAngleXYTableau()[1]) <= 90 && mur.getAngleXYTableau()[0].angle(positionXY, mur.getAngleXYTableau()[3]) <= 90)
                                if (mur.getAngleXYTableau()[2].angle(positionXY, mur.getAngleXYTableau()[1]) <= 90 && mur.getAngleXYTableau()[2].angle(positionXY, mur.getAngleXYTableau()[3]) <= 90) {
                                    return x;
                                }
                        }
            }
            else//Calcule colision mur en angle
                for (int x = 0; x < tableaudroit.length; x++)
                    if (mur.getAngleXZTableau()[0].angle(tableauangle[x], mur.getAngleXZTableau()[1]) <= 90 && mur.getAngleXZTableau()[0].angle(tableauangle[x], mur.getAngleXZTableau()[3]) <= 90)
                        if (mur.getAngleXZTableau()[2].angle(tableauangle[x], mur.getAngleXZTableau()[1]) <= 90 && mur.getAngleXZTableau()[2].angle(tableauangle[x], mur.getAngleXZTableau()[3]) <= 90){
                                    return x+4;

                        }
        return  -1;
    }


    public FormeCordonneSommet detectColisionDansQuelleFormeSol(){
        Point2D positionBalleXZ = new Point2D(positionBalle.getX(),positionBalle.getZ());

        for (FormeCordonneSommet sol:plateformeSol) {

            if (sol.getAngleXZTableau()[0].angle(positionBalleXZ,sol.getAngleXZTableau()[1]) <= 90 && sol.getAngleXZTableau()[0].angle(positionBalleXZ,sol.getAngleXZTableau()[3]) <= 90)
                if (sol.getAngleXZTableau()[2].angle(positionBalleXZ,sol.getAngleXZTableau()[1]) <= 90 && sol.getAngleXZTableau()[2].angle(positionBalleXZ,sol.getAngleXZTableau()[3]) <= 90){
                    if (sol.getAngleXY() == 0 && (positionBalle.getY() - sol.getSommets()[0].getY()) <= 8)
                        return sol;
                    Point2D pointXZ = FormeCordonneSommet.trouverPointFormeAngleXZ(sol, positionBalleXZ.getX(), positionBalleXZ.getY(), (360 - sol.getAngleXZ()));
                    Point2D pointXY = new Point2D(pointXZ.getX(),positionBalle.getY() - 8);
                    double angleA = sol.getAngleXYTableau()[0].angle(sol.getAngleXYTableau()[3],sol.getAngleXYTableau()[1]);
                    double angleB = sol.getAngleXYTableau()[2].angle(sol.getAngleXYTableau()[3], sol.getAngleXYTableau()[1]);

                    if (sol.getAngleXYTableau()[0].angle(pointXY,sol.getAngleXYTableau()[1]) <= angleA && sol.getAngleXYTableau()[0].angle(pointXY,sol.getAngleXYTableau()[3]) <= angleA)
                        if (sol.getAngleXYTableau()[2].angle(pointXY, sol.getAngleXYTableau()[1]) <= angleB && sol.getAngleXYTableau()[2].angle(pointXY, sol.getAngleXYTableau()[3]) <= angleB)
                            return sol;
                }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Espace3D{" +
                "plateformeSol=" + plateformeSol +
                ", plateformeMur=" + plateformeMur +
                ", positionBalle=" + positionBalle +
                ", formeOuEstLaBalle=" + formeOuEstLaBalle +
                '}';
    }
}
