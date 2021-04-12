package TestUnitaires;

import controleur.Formule;
import javafx.geometry.Point3D;
import modele.Forme;
import modele.Vecteur;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class FormuleTest {

    @Test
    public void MRUATest() {
        assertEquals(50.0, Formule.MRUA(0, 5, 0, 10));
        assertEquals(60.0, Formule.MRUA(10, 5, 0, 10));
        assertEquals(250.0, Formule.MRUA(0, 5, 4, 10));

        assertEquals(-50.0, Formule.MRUA(0, -5, 0, 10));
        assertEquals(40.0, Formule.MRUA(-10, 5, 0, 10));
        assertEquals(-150.0, Formule.MRUA(0, 5, -4, 10));

        assertEquals(22.559375, Formule.MRUA(7.82, 8.32, -2.10, 5.25));
    }

    @Test
    public void MRUA_VFTest(){
        assertEquals(50.0,Formule.MRUA_Vf(0,5,10));
        assertEquals(-20.0,Formule.MRUA_Vf(30,-5,10));
        assertEquals(-4.42,Formule.MRUA_Vf(-4.68,2.6,0.1));
    }

    @Test
    public void rebondissementTest2(){
        // test avec pas d'angle dans les murs
        Vecteur a = new Vecteur(new Point3D(0,0,0));
        double[] vitesse1 = new double[]{10,0,20};
        double[] vitesse2 = new double[]{-15,0,15};
        double[] vitesse3 = new double[]{0,0,50};
        double[] vitesse4 = new double[]{20,0,0};

        a.setVecteurVitesseResultant(vitesse1);
        assertEquals(63.43,a.getAngleXZ(),0.01);
        Formule.rebondissement(a,0);
        assertEquals(296.56,a.getAngleXZ(),0.01);

        a.setVecteurVitesseResultant(vitesse2);
        assertEquals(135.0,a.getAngleXZ(),0.01);
        Formule.rebondissement(a,6);
        assertEquals(45.0,a.getAngleXZ(),0.01);

        a.setVecteurVitesseResultant(vitesse3);
        assertEquals(90.0,a.getAngleXZ(),0.01);
        Formule.rebondissement(a,6);
        assertEquals(270.0,a.getAngleXZ(),0.01);

        a.setVecteurVitesseResultant(vitesse4);
        assertEquals(0.0,a.getAngleXZ(),0.01);
        Formule.rebondissement(a,6);
        assertEquals(180.0,a.getAngleXZ(),0.01);

    }

    @Test
    public void rebondissementTest() {
        Vecteur a = new Vecteur(new Point3D(0,0,0));
        a.setAngleXZ(15);
        Vecteur b = new Vecteur(new Point3D(0,0,0));
        b.setAngleXZ(110);
        Vecteur c = new Vecteur(new Point3D(0,0,0));
        c.setAngleXZ(195);
        Vecteur d = new Vecteur(new Point3D(0,0,0));
        d.setAngleXZ(305);

        Vecteur h = new Vecteur(new Point3D(0,0,0));
        h.setAngleXZ(0);
        Vecteur l = new Vecteur(new Point3D(0,0,0));
        l.setAngleXZ(90);

        assertEquals(345.0, Formule.rebondissement(a, h));
        assertEquals(250.0, Formule.rebondissement(b, h));
        assertEquals(165.0, Formule.rebondissement(c, h));
        assertEquals(55.0, Formule.rebondissement(d, h));

        assertEquals(165.0, Formule.rebondissement(a, l));
        assertEquals(70.0, Formule.rebondissement(b, l));
        assertEquals(345.0, Formule.rebondissement(c, l));
        assertEquals(235.0, Formule.rebondissement(d, l));

        assertEquals(180.0, Formule.rebondissement(h, l));
        assertEquals(270.0, Formule.rebondissement(l, h));
    }

    @Test
    public void fgTest(){
        Forme numero1 = new Forme(new Point3D(0,0,0),40,10,40,0,0,4);
        Forme numero2 = new Forme(new Point3D(0,0,0),40,10,40,45,0,4);
        Forme numero3 = new Forme(new Point3D(0,0,0),40,10,40,0,90,4);
        Forme numero4 = new Forme(new Point3D(0,0,0),40,10,40,45,180,4);
        Forme numero5 = new Forme(new Point3D(0,0,0),40,10,40,45,90,4);

        double[] force1 = Formule.forcegravitationnel(numero1);
        assertEquals(0.0,force1[0]);
        assertEquals(-0.441,force1[1]);
        assertEquals(0.0,force1[2]);

        double[] force2 = Formule.forcegravitationnel(numero2);
        assertEquals(-0.3118,force2[1],0.0001);
        assertEquals(-0.3118,force2[0],0.0001);
        assertEquals(0.0,force2[2],0.0001);

        double[] force3 = Formule.forcegravitationnel(numero3);
        assertEquals(0.0,force3[0]);
        assertEquals(-0.441,force3[1]);
        assertEquals(0.0,force3[2]);

        double[] force4 = Formule.forcegravitationnel(numero4);
        assertEquals(-0.3118,force4[1],0.0001);
        assertEquals(0.3118,force4[0],0.0001);
        assertEquals(0.0,force4[2],0.0001);

        double[] force5 = Formule.forcegravitationnel(numero5);
        assertEquals(-0.3118,force5[1],0.0001);
        assertEquals(0.0,force5[0],0.0001);
        assertEquals(-0.3118,force5[2],0.0001);

        double[] force6 = Formule.forcegravitationnel(null);
        assertEquals(0.0,force6[0]);
        assertEquals(-0.441,force6[1]);
        assertEquals(0.0,force6[2]);
    }
}
