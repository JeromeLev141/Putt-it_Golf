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
        Point2D [] tableau = new Point2D[]{position7,position8,position1,position2,position3,position4,position5,position6};

        for (FormeCordonneSommet mur : plateformeMur)
            for (int x = 0;x < tableau.length;x++)
                if (mur.getAngleXZTableau()[0].angle(tableau[x], mur.getAngleXZTableau()[1]) <= 90 && mur.getAngleXZTableau()[0].angle(tableau[x], mur.getAngleXZTableau()[3]) <= 90)
                    if (mur.getAngleXZTableau()[2].angle(tableau[x], mur.getAngleXZTableau()[1]) <= 90 && mur.getAngleXZTableau()[2].angle(tableau[x], mur.getAngleXZTableau()[3]) <= 90){
                        Point2D positionXY = new Point2D(tableau[x].getX(),positionBalle.getY());
                        //Point2D positionYZ = new Point2D(positionBalle.getY(),tableau[x].getY());
                        if (mur.getAngleXYTableau()[0].angle(positionXY,mur.getAngleXYTableau()[1]) <= 90 &&  mur.getAngleXYTableau()[0].angle(positionXY,mur.getAngleXYTableau()[3]) <= 90)
                            if (mur.getAngleXYTableau()[2].angle(positionXY,mur.getAngleXYTableau()[1]) <= 90 &&  mur.getAngleXYTableau()[2].angle(positionXY,mur.getAngleXYTableau()[3]) <= 90) {
                                System.out.println("collision x");
                                return x;
                            }
                        /*else if (mur.getAngleYZTableau()[0].angle(positionYZ,mur.getAngleYZTableau()[1]) <= 90 &&  mur.getAngleYZTableau()[0].angle(positionYZ,mur.getAngleYZTableau()[3]) <= 90)
                            if (mur.getAngleYZTableau()[2].angle(positionYZ,mur.getAngleYZTableau()[1]) <= 90 &&  mur.getAngleYZTableau()[2].angle(positionYZ,mur.getAngleYZTableau()[3]) <= 90) {
                                System.out.println("colision z");
                                return x;
                            }*/
                    }
        return  -1;
    }


    public FormeCordonneSommet detectColisionDansQuelleFormeSol(){
        Point2D positionBalleXZ = new Point2D(positionBalle.getX(),positionBalle.getZ());

        for (FormeCordonneSommet sol:plateformeSol) {

            if (sol.getAngleXZTableau()[0].angle(positionBalleXZ,sol.getAngleXZTableau()[1]) <= 90 && sol.getAngleXZTableau()[0].angle(positionBalleXZ,sol.getAngleXZTableau()[3]) <= 90)
                if (sol.getAngleXZTableau()[2].angle(positionBalleXZ,sol.getAngleXZTableau()[1]) <= 90 && sol.getAngleXZTableau()[2].angle(positionBalleXZ,sol.getAngleXZTableau()[3]) <= 90){
                    if (sol.getAngleXY() == 0 && (positionBalle.getY() - sol.getSommets()[0].getY()) <= 8) {
                        //System.out.println(" colosion sol");
                        return sol;
                    }
                    Point2D pointXZ = FormeCordonneSommet.trouverPointFormeAngleXZ(sol, positionBalleXZ.getX(), positionBalleXZ.getY() - 8, (360 - sol.getAngleXZ()));
                    Point2D pointXY = new Point2D(pointXZ.getX(),positionBalle.getY() - 8);

                    if (sol.getAngleXYTableau()[0].angle(pointXY,sol.getAngleXYTableau()[1]) <= 90 && sol.getAngleXYTableau()[0].angle(pointXY,sol.getAngleXYTableau()[3]) <= 90)
                        if (sol.getAngleXYTableau()[2].angle(pointXY, sol.getAngleXYTableau()[1]) <= 90 && sol.getAngleXYTableau()[2].angle(pointXY, sol.getAngleXYTableau()[3]) <= 90)
                            return sol;
                }
        }
        return null;
    }

}
