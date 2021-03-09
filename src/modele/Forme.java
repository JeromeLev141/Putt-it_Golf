package modele;

import javafx.geometry.Point3D;
import javafx.scene.shape.Box;

public class Forme {

    private Box prisme;
    private Point3D positionEspace;
    private double angleXZ;
    private double angleXY;

    public Forme(Point3D position1,double x, double y, double z,double angleXY,double angleXZ){
        positionEspace = position1;
        prisme = new Box(x,y,z);
        this.angleXY = angleXY;
        this.angleXZ = angleXZ;
    }

    public Box getPrisme() {
        return prisme;
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
}