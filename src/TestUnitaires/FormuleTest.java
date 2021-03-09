package TestUnitaires;

import controleur.Formule;
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
    public void rebondissementTest() {
        Vecteur a = new Vecteur(new double[]{0,0,0});
        a.setAngleXZ(15);
        Vecteur b = new Vecteur(new double[]{0,0,0});
        b.setAngleXZ(110);
        Vecteur c = new Vecteur(new double[]{0,0,0});
        c.setAngleXZ(195);
        Vecteur d = new Vecteur(new double[]{0,0,0});
        d.setAngleXZ(305);

        Vecteur h = new Vecteur(new double[]{0,0,0});
        h.setAngleXZ(0);
        Vecteur l = new Vecteur(new double[]{0,0,0});
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
}
