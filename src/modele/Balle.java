package modele;

import javafx.geometry.Point3D;
import javafx.scene.shape.Sphere;

public class Balle extends Sphere {

    private Point3D position;
    private final double masse = 0.045; //(en kilogramme)
    private final double rayon = 0.025; //(en metre)

    public Balle (Point3D position) {
        this.position = position;
    }

    public Balle(double radius) {
        this.setRadius(radius);
    }

    public Point3D getPosition() { return position; }

    public void setPosition(Point3D position) { this.position = position; }

    public double getMasse() { return masse; }
}
