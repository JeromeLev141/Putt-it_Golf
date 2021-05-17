package modele;

import javafx.geometry.Point3D;
import javafx.scene.shape.Box;

public class Forme{

    private Point3D positionEspace;
    private double angleXZ;
    private double angleXY;
    private Sol typeSol;
    private double width;
    private double height;
    private double depth;

    public Forme(Point3D position1,double x,double y,double z,double angleXY,double angleXZ,int sol){
        width = x/2;
        height = y/2;
        depth = z/2;
        positionEspace = position1;
        this.angleXY = angleXY;
        this.angleXZ = angleXZ;
        typeSol = new Sol(sol);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDepth() {
        return depth;
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

    public Sol getTypeSol() {
        return typeSol;
    }

    @Override
    public String toString() {
        return "Forme{" +
                "positionEspace=" + positionEspace +
                ", angleXZ=" + angleXZ +
                ", angleXY=" + angleXY +
                ", typeSol=" + typeSol +
                ", width=" + width * 2 +
                ", height=" + height * 2 +
                ", depth=" + depth * 2 +
                '}';
    }
}
