package modele;

import javafx.geometry.Point3D;

public class Balle {

    private Point3D position;

    Balle (Point3D position) {
        this.position = position;
    }

    public Point3D getPosition() { return position; }

    public void setPosition(Point3D position) { this.position = position; }
}
