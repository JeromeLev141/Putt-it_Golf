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

/*
    public Sol colisionSolMateriaux(int positionDansLaListe){
        Forme sol = plateformeSol.get(positionDansLaListe);
        Point3D[] cordonnesommet = cordonnerFormeSol.get(positionDansLaListe);
        if (sol.getAngleXZ() == 0){
            if ((positionBalle.getY() - 0.025) <= cordonnesommet[0].getY())
                return sol.getTypeSol();
            else
                return null;
        }
        else{

            double distanceQR = cordonnesommet[3].distance(positionBalle) * Math.cos(Math.toRadians(cordonnesommet[0].angle(positionBalle,cordonnesommet[3])));
            double x = (distanceQR/cordonnesommet[3].distance(cordonnesommet[0])) * (cordonnesommet[3].getX()-cordonnesommet[0].getX()) + cordonnesommet[0].getX();
            double z = (distanceQR/cordonnesommet[3].distance(cordonnesommet[0])) * (cordonnesommet[3].getZ()-cordonnesommet[0].getZ()) + cordonnesommet[0].getZ();
            Point3D point3D1 = new Point3D(positionBalle.getX(),cordonnesommet[3].getY(),positionBalle.getZ());
            Point3D point3D2 = new Point3D(x,cordonnesommet[3].getY(),z);
            Point3D point3D3 = new Point3D(positionBalle.getX(),positionBalle.getY() - 0.025,positionBalle.getZ());
            Point3D point3D000 = new Point3D(0,0,0);
            Point3D point3D4 = new Point3D(cordonnesommet[2].getX(),cordonnesommet[3].getY(),cordonnesommet[2].getZ());
            Point2D point1 = new Point2D(x,z);
            Point2D pointBalle = new Point2D(positionBalle.getX(),positionBalle.getZ());
            double y =  Math.tan(Math.toRadians(sol.getAngleXY())) * distanceQR;//point1.distance(pointBalle);

            System.out.println("distance = " + cordonnesommet[3].distance(positionBalle));
            System.out.println("angle = " + cordonnesommet[0].angle(positionBalle,cordonnesommet[3]));
            System.out.println("angle Sin = " + Math.sin(Math.toRadians(cordonnesommet[0].angle(positionBalle,cordonnesommet[3]))));
            System.out.println("QR = " + distanceQR);
            System.out.println("X = " + x);
            System.out.println("Z = " + z);
            System.out.println(Math.tan(sol.getAngleXY()));
            System.out.println("Y = " + y);
            System.out.println("position de la balle = " + (positionBalle.getY() - 0.025));
            System.out.println("angle A1 = " + point3D2.angle(point3D1,point3D3));
            System.out.println("angle A2 = " + point3D1.angle(point3D2,point3D3));
            System.out.println("angle A3 = " + point3D3.angle(point3D1,point3D2));
            System.out.println("angle A3 = " + point3D2.angle(point3D1,point3D000));
            System.out.println("angle B = " + cordonnesommet[3].angle(cordonnesommet[2],point3D4));
            System.out.println(point3D1);
            System.out.println(point3D2);
            System.out.println(point3D3);
            if (point3D3.getY() <= cordonnesommet[3].getY())
                return sol.getTypeSol();
            else if (point3D2.angle(point3D1,point3D3) <= cordonnesommet[3].angle(cordonnesommet[2],point3D4))
                return sol.getTypeSol();
            else
                return null;
        }
    }

 */
    public Sol colisionSolMateriaux(){
        return formeOuEstLaBalle.getTypeSol();
    }

    public List<FormeCordonneSommet> getPlateformeSol() {
        return plateformeSol;
    }

    public void refreshPositionBalle(Point3D nouvellePosition){
        this.positionBalle = nouvellePosition;
        formeOuEstLaBalle = detectColisionDansQuelleFormeSol();
    }

    public FormeCordonneSommet detectionColisionDansQuelleFormeMur(){
        Point2D position1 = new Point2D(positionBalle.getX() + 0.025,positionBalle.getZ());
        Point2D position2 = new Point2D(positionBalle.getX() + 0.0125,positionBalle.getZ() - 0.0125);
        Point2D position3 = new Point2D(positionBalle.getX(),positionBalle.getZ() - 0.025);
        Point2D position4 = new Point2D(positionBalle.getX() - 0.0125,positionBalle.getZ() - 0.0125);
        Point2D position5 = new Point2D(positionBalle.getX() - 0.025,positionBalle.getZ());
        Point2D position6 = new Point2D(positionBalle.getX() - 0.0125,positionBalle.getZ() + 0.0125);
        Point2D position7 = new Point2D(positionBalle.getX() ,positionBalle.getZ() + 0.025);
        Point2D position8 = new Point2D(positionBalle.getX() + 0.0125,positionBalle.getZ() + 0.0125);
        Point2D [] tableau = new Point2D[]{position1,position2,position3,position4,position5,position6,position7,position8};

        for (FormeCordonneSommet mur : plateformeMur)
            for (int x = 0;x < tableau.length;x++)
                if (mur.getAngleXZTableau()[0].angle(tableau[x], mur.getAngleXZTableau()[1]) <= 90 && mur.getAngleXZTableau()[0].angle(tableau[x], mur.getAngleXZTableau()[3]) <= 90)
                    if (mur.getAngleXZTableau()[2].angle(tableau[x], mur.getAngleXZTableau()[1]) <= 90 && mur.getAngleXZTableau()[2].angle(tableau[x], mur.getAngleXZTableau()[3]) <= 90)
                        return mur;
        return  null;
    }


    public FormeCordonneSommet detectColisionDansQuelleFormeSol(){
        Point2D positionBalleXZ = new Point2D(positionBalle.getX(),positionBalle.getZ());

        for (FormeCordonneSommet sol:plateformeSol) {

            System.out.println("Angle 1 = " + sol.getAngleXZTableau()[0].angle(positionBalleXZ,sol.getAngleXZTableau()[1]));
            System.out.println("Angle 2 = " + sol.getAngleXZTableau()[0].angle(positionBalleXZ,sol.getAngleXZTableau()[3]));
            System.out.println("Angle 3 = " + sol.getAngleXZTableau()[2].angle(positionBalleXZ,sol.getAngleXZTableau()[1]));
            System.out.println("Angle 4 = " + sol.getAngleXZTableau()[2].angle(positionBalleXZ,sol.getAngleXZTableau()[3]));


            if (sol.getAngleXZTableau()[0].angle(positionBalleXZ,sol.getAngleXZTableau()[1]) <= 90 && sol.getAngleXZTableau()[0].angle(positionBalleXZ,sol.getAngleXZTableau()[3]) <= 90)
                if (sol.getAngleXZTableau()[2].angle(positionBalleXZ,sol.getAngleXZTableau()[1]) <= 90 && sol.getAngleXZTableau()[2].angle(positionBalleXZ,sol.getAngleXZTableau()[3]) <= 90){
                    if (sol.getAngleXY() == 0 && (positionBalle.getY() - sol.getSommets()[0].getY()) <= 0.025)
                        return  sol;
                    Point2D pointXZ = FormeCordonneSommet.trouverPointFormeAngleXZ(sol, positionBalleXZ.getX(), positionBalleXZ.getY() - 0.025, (360 - sol.getAngleXZ()));
                    Point2D pointXY = new Point2D(pointXZ.getX(),positionBalle.getY() - 0.025);


                    System.out.println(sol.getAngleXYTableau()[0]);
                    System.out.println(sol.getAngleXYTableau()[1]);
                    System.out.println(sol.getAngleXYTableau()[2]);
                    System.out.println(sol.getAngleXYTableau()[3]);
                    System.out.println("pointXY Balle" + pointXY);
                    System.out.println("Angle 5 = " + sol.getAngleXYTableau()[0].angle(pointXY,sol.getAngleXYTableau()[1]));
                    System.out.println("Angle 6 = " + sol.getAngleXYTableau()[2].angle(pointXY,sol.getAngleXYTableau()[1]));
                    System.out.println("Angle 7 = " + sol.getAngleXYTableau()[0].angle(pointXY,sol.getAngleXYTableau()[3]));
                    System.out.println("Angle 8 = " + sol.getAngleXYTableau()[2].angle(pointXY,sol.getAngleXYTableau()[3]));
                    if (sol.getAngleXYTableau()[0].angle(pointXY,sol.getAngleXYTableau()[1]) <= 90 && sol.getAngleXYTableau()[0].angle(pointXY,sol.getAngleXYTableau()[3]) <= 90)
                        if (sol.getAngleXYTableau()[2].angle(pointXY, sol.getAngleXYTableau()[1]) <= 90 && sol.getAngleXYTableau()[2].angle(pointXY, sol.getAngleXYTableau()[3]) <= 90)
                            return sol;
                }
        }
        return null;
    }

}
