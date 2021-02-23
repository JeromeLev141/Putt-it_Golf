import modele.Vecteur;
import java.util.Vector;

public class Main_jeremi{
    public static void main(String[]args){
        Vecteur vecteur = new Vecteur(new double[]{0,0,0});
        vecteur.addforceX(15.0);
        int position1 = vecteur.addforceX(-5);
        vecteur.addforceX(-7);
        vecteur.addforceX(6);
        vecteur.addforceY(10);
        System.out.println(vecteur.getVecteurForceResultant()[0] + "-" + vecteur.getVecteurForceResultant()[1] + "-" + vecteur.getVecteurForceResultant()[2]);
        vecteur.setForceX(position1,-2);
        System.out.println(vecteur.getVecteurForceResultant()[0] + "-" + vecteur.getVecteurForceResultant()[1] + "-" + vecteur.getVecteurForceResultant()[2]);
    }
}