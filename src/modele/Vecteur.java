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
    private Vector<Double> vitesseX,vitesseY,vitesseZ;
    private double[] vecteurAccelerationResultant;
    private Vector<Double> accelerationX,accelerationY,accelerationZ;
    private double angleXZ;

    public Vecteur(double[] positionBalle){
        possition = positionBalle;
        vecteurForceResultant = new double[]{0,0,0};
        vecteurVitesseResultant = new double[]{0,0,0};
        vecteurAccelerationResultant = new double[]{0,0,0};
        angleXZ = 0;
        forceX = new Vector<>();
        forceY = new Vector<>();
        forceZ = new Vector<>();
        vitesseX = new Vector<>();
        vitesseY = new Vector<>();
        vitesseZ = new Vector<>();
        accelerationX = new Vector<>();
        accelerationY = new Vector<>();
        accelerationY.add(9.8);
        accelerationZ = new Vector<>();
        refreshVitesse();
        refreshForce();
        refreshAcceleration();
    }

    private void refreshAngleXZ(){

        angleXZ = Math.toDegrees(Math.atan(Math.toRadians(vecteurVitesseResultant[2])/Math.toRadians(vecteurVitesseResultant[0])));

        if (vecteurVitesseResultant[0] < 0)
            angleXZ += 180;

        if (vecteurVitesseResultant[0] > 0 && vecteurVitesseResultant[2] < 0)
            angleXZ += 360;

    }

    private void refreshForce(){
        vecteurForceResultant[0] = forceX.stream().mapToDouble(Double ::doubleValue).sum();
        vecteurForceResultant[1] = forceY.stream().mapToDouble(Double ::doubleValue).sum();
        vecteurForceResultant[2] = forceZ.stream().mapToDouble(Double ::doubleValue).sum();
    }

    private void refreshVitesse(){
        vecteurVitesseResultant[0] = vitesseX.stream().mapToDouble(Double ::doubleValue).sum();
        vecteurVitesseResultant[1] = vitesseY.stream().mapToDouble(Double ::doubleValue).sum();
        vecteurVitesseResultant[2] = vitesseZ.stream().mapToDouble(Double ::doubleValue).sum();
        refreshAngleXZ();
    }

    private void refreshAcceleration(){
        vecteurAccelerationResultant[0] = accelerationX.stream().mapToDouble(Double ::doubleValue).sum();
        vecteurAccelerationResultant[1] = accelerationY.stream().mapToDouble(Double ::doubleValue).sum();
        vecteurAccelerationResultant[2] = accelerationZ.stream().mapToDouble(Double ::doubleValue).sum();
    }

    public int addforceX ( double vecteur){
        forceX.add(vecteur);
        refreshForce();
        return forceX.size() - 1;
    }

    public void setForceX ( int positionForce, double force){
        forceX.set(positionForce, force);
        refreshForce();
    }

    public int addforceY ( double vecteur){
        forceY.add(vecteur);
        refreshForce();
        return forceY.size() - 1;
    }

    public void setForceY ( int positionForce, double force){
        forceY.set(positionForce, force);
        refreshForce();
    }

    public int addforceZ ( double vecteur){
        forceZ.add(vecteur);
        refreshForce();
        return forceZ.size() - 1;
    }

    public void setForceZ ( int positionVitesse, double vitesse){
        forceZ.set(positionVitesse, vitesse);
        refreshForce();
    }



    public int addVitesseX(double vecteur){
        vitesseX.add(vecteur);
        refreshVitesse();
        return vitesseX.size()-1;
    }

    public void setVitesseX(int positionVitesse, double vitesse){
        forceX.set(positionVitesse,vitesse);
        refreshVitesse();
    }

    public int addVitesseY(double vecteur){
        vitesseY.add(vecteur);
        refreshVitesse();
        return vitesseY.size()-1;
    }

    public void setVitesseY(int positionVitesse, double vitesse){
        vitesseY.set(positionVitesse,vitesse);
        refreshVitesse();
    }

    public int addVitesseZ(double vecteur){
        vitesseZ.add(vecteur);
        refreshVitesse();
        return vitesseZ.size()-1;
    }

    public void setVitesseZ(int positionVitesse, double vitesse){
        forceZ.set(positionVitesse,vitesse);
        refreshVitesse();
    }



    public int addAccelerationX(double vecteur){
        accelerationX.add(vecteur);
        refreshVitesse();
        return accelerationX.size()-1;
    }

    public void setAccelerationX(int positionAcceleration, double acceleration){
        forceX.set(positionAcceleration,acceleration);
        refreshAcceleration();
    }

    public int addAccelerationY(double vecteur){
        accelerationY.add(vecteur);
        refreshVitesse();
        return accelerationY.size()-1;
    }

    public void setAccelerationY(int positionAcceleration, double acceleration){
        forceY.set(positionAcceleration,acceleration);
        refreshAcceleration();
    }

    public int addAccelerationZ(double vecteur){
        accelerationZ.add(vecteur);
        refreshVitesse();
        return accelerationZ.size()-1;
    }

    public void setAccelerationZ(int positionAcceleration, double acceleration){
        forceZ.set(positionAcceleration,acceleration);
        refreshAcceleration();
    }



    public double[] getPossition() {
        return possition;
    }

    public double[] getVecteurForceResultant() {
        return vecteurForceResultant;
    }

    public double[] getVecteurVitesseResultant() {
        return vecteurVitesseResultant;
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

    public Vector<Double> getVitesseX() {
        return vitesseX;
    }

    public Vector<Double> getVitesseY() {
        return vitesseY;
    }

    public Vector<Double> getVitesseZ() {
        return vitesseZ;
    }

    public Vector<Double> getAccelerationX() {
        return accelerationX;
    }

    public Vector<Double> getAccelerationY() {
        return accelerationY;
    }

    public Vector<Double> getAccelerationZ() {
        return accelerationZ;
    }

    public double getAngleXZ() {
        return angleXZ;
    }

    public void setAngleXZ(double angleXZ) {
        this.angleXZ = angleXZ;
    }
}
