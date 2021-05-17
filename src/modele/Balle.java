package modele;

import javafx.geometry.Point3D;
import javafx.scene.shape.Sphere;

public class Balle extends Sphere {

    private Point3D position;

    public Balle (Point3D position) {
        this.position = position;
    }

    public Balle(double radius) {
        this.setRadius(radius);
    }

    public static double getMasse() { return 0.045; }

    @Override
    public String toString() {
        return "Balle{" +
                "position=" + position +
                '}';
    }
}
