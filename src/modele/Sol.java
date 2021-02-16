package modele;

public class Sol {

    private boolean traversable;
    private double frottement;

    public Sol(Boolean traversable, double frottement) {
        this.traversable = traversable;
        this.frottement = frottement;
    }

    public boolean isTraversable() { return traversable; }

    public void setTraversable(boolean traversable) { this.traversable = traversable; }

    public double getFrottement() { return frottement; }

    public void setFrottement(double frottement) { this.frottement = frottement; }
}
