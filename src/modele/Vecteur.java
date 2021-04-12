package modele;

import controleur.Formule;
import javafx.geometry.Point3D;

import java.util.Vector;

public class Vecteur {
    /*
    private Point3D position;
    private Point3D force;
    private Point3D vitesse;
    private Point3D acceleration;

    public Vecteur(Point3D positionBalle){
        position = positionBalle;
        force = new Point3D(0,0,0);
        vitesse = new Point3D(0,0,0);
        acceleration = new Point3D(0, Formule.forcegravitationnel(),0);
    }
    */
    private double[] possition;
    private double[] vecteurForceResultant;
    private Vector<Double> forceX,forceY,forceZ;
    private double[] vecteurVitesseResultant;
    private double[] vecteurAccelerationResultant;
    private double angleXZ;

    public Vecteur(Point3D positionBalle){
        possition =  new double[]{positionBalle.getX(),positionBalle.getY(),positionBalle.getZ()};
        vecteurForceResultant = new double[]{0,0,0};
        vecteurVitesseResultant = new double[]{0,0,0};
        vecteurAccelerationResultant = new double[]{0,0,0};
        angleXZ = 0;
        forceX = new Vector<>();
        forceY = new Vector<>();
        forceZ = new Vector<>();
        refreshForce();
    }

    public int creeSection(){
        addforceX(0);
        addforceY(0);
        addforceZ(0);
        return forceX.size() -1;
    }

    private void refreshAngleXZ(){

        angleXZ = Math.toDegrees(Math.atan(Math.toRadians(vecteurVitesseResultant[2])/Math.toRadians(vecteurVitesseResultant[0])));

        if (vecteurVitesseResultant[0] < 0)
            angleXZ += 180;

        if (vecteurVitesseResultant[0] > 0 && vecteurVitesseResultant[2] < 0)
            angleXZ += 360;

        if (angleXZ == -90)
            angleXZ = 270;

    }

    private void refreshForce(){
        vecteurForceResultant[0] = forceX.stream().mapToDouble(Double ::doubleValue).sum();
        vecteurForceResultant[1] = forceY.stream().mapToDouble(Double ::doubleValue).sum();
        vecteurForceResultant[2] = forceZ.stream().mapToDouble(Double ::doubleValue).sum();
    }


    private void addforceX ( double vecteur){
        forceX.add(vecteur);
        refreshForce();

    }

    public void setForceX ( int positionForce, double force){
        forceX.set(positionForce, force);
        refreshForce();
    }

    private void addforceY ( double vecteur){
        forceY.add(vecteur);
        refreshForce();

    }

    public void setForceY ( int positionForce, double force){
        forceY.set(positionForce, force);
        refreshForce();
    }

    private void addforceZ ( double vecteur){
        forceZ.add(vecteur);
        refreshForce();
    }

    public void setForceZ ( int positionForce, double force){
        forceZ.set(positionForce, force);
        refreshForce();
    }

    public double[] getPossition() {
        return possition;
    }

    public double[] getVecteurForceResultant() {
        return vecteurForceResultant;
    }

    public void refreshVecteurAccelerationResultant(){
        for (int x = 0; x < vecteurAccelerationResultant.length; x++)
            vecteurAccelerationResultant[x] = Formule.forceAAcceleration(vecteurForceResultant[x]);
    }

    public double[] getVecteurVitesseResultant() {
        return vecteurVitesseResultant;
    }

    public void setVecteurVitesseResultant(int position, double vitesse){
        vecteurVitesseResultant[position] = vitesse;
        refreshAngleXZ();
    }

    public void setVecteurVitesseResultant(double[] vitesse){
        for (int x = 0; x<3 ;x++)
            vecteurVitesseResultant[x] = vitesse[x];
        refreshAngleXZ();
    }

    public double[] getVecteurAccelerationResultant() {
        return vecteurAccelerationResultant;
    }

    public Vector<Double> getForceX() {
        return forceX;
    }

    public Vector<Double> getForceY() {
        return forceY;
    }

    public Vector<Double> getForceZ() {
        return forceZ;
    }

    public double getAngleXZ() {
        return angleXZ;
    }

    public void setAngleXZ(double angleXZ) {
        this.angleXZ = angleXZ;
    }
}
