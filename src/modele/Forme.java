package modele;

import javafx.geometry.Point3D;
import javafx.scene.shape.Box;

public class Forme extends Box{

    private Point3D positionEspace;
    private boolean triangle;
    private double angleXZ;
    private double angleXY;
    private Sol typeSol;

    public Forme(Point3D position1,double x,double y,double z,double angleXY,double angleXZ,int sol, Boolean triangle){
        super(x,y,z);
        positionEspace = position1;
        this.angleXY = angleXY;
        this.angleXZ = angleXZ;
        typeSol = new Sol(sol);
        this.triangle = triangle;
    }


    public Point3D getPositionEspace() {
        return positionEspace;
    }

    public double getAngleXY() {
        return angleXY;
    }

    public double getAngleXZ() {
        return angleXZ;
    }

    public boolean isTriangle() {
        return triangle;
    }

    public Sol getTypeSol() {
        return typeSol;
    }
}
