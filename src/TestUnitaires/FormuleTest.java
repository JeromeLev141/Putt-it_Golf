package TestUnitaires;

import controleur.Formule;
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
}
