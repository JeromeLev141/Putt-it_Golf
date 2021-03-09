package modele;

import javafx.geometry.Point3D;

public class Balle {

    private Point3D position;
    private final double masse = 0.045; //(en kilogramme)
    private final double rayon = 0.025; //(en metre)

    Balle (Point3D position) {
        this.position = position;
    }

    public Point3D getPosition() { return position; }

    public void setPosition(Point3D position) { this.position = position; }

    public double getMasse() { return masse; }
}
