package TestUnitaires;

import controleur.Jeux;
import javafx.geometry.Point3D;
import modele.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class Espace3DTest {

    @Test
    public void detecterColisionTest(){

        List<FormeCordonneSommet> mur1 = new ArrayList<>();
        List<FormeCordonneSommet> sol1 = new ArrayList<>();

        String map1 = "xxxxx\n" +
                "xooox\n" +
                "xooox\n" +
                "xooox\n" +
                "xxxxx";
        prepareMap(map1,sol1,mur1);
        Espace3D test1 = new Espace3D(new Point3D(-10,40,280),sol1,mur1);
        FormeCordonneSommet cordonneSommetSol1 = test1.detectColisionDansQuelleFormeSol();
        int cordonneSommetMur1 = test1.detectionColisionDansQuelleFormeMur();

        assertEquals(0.0,cordonneSommetSol1.getPositionEspace().getX());
        assertEquals(256.0,cordonneSommetSol1.getPositionEspace().getZ());
        assertEquals(-1,cordonneSommetMur1);

        // Pas De Colision avec le sol
        Espace3D test2 = new Espace3D(new Point3D(0,40.01,256),sol1,mur1);
        FormeCordonneSommet cordonneSommetsol2 = test2.detectColisionDansQuelleFormeSol();
        int cordonneSommetMur2 = test2.detectionColisionDansQuelleFormeMur();
        assertNull(cordonneSommetsol2);
        assertEquals(-1 , cordonneSommetMur2);

        //colision avec mur
        Espace3D test3 = new Espace3D(new Point3D(-88, 40,192),sol1,mur1);
        FormeCordonneSommet cordonneSommetsol3 = test3.detectColisionDansQuelleFormeSol();
        int cordonneSommetMur3 = test3.detectionColisionDansQuelleFormeMur();
        assertEquals(-64.0,cordonneSommetsol3.getPositionEspace().getX());
        assertEquals(192.0,cordonneSommetsol3.getPositionEspace().getZ());
        assertEquals(6,cordonneSommetMur3);

        //pas de colision avec le mur
        Espace3D test4 = new Espace3D(new Point3D(-87, 40,192),sol1,mur1);
        FormeCordonneSommet cordonneSommetsol4 = test4.detectColisionDansQuelleFormeSol();
        int cordonneSommetMur4 = test4.detectionColisionDansQuelleFormeMur();
        assertEquals(-64.0,cordonneSommetsol4.getPositionEspace().getX());
        assertEquals(192.0,cordonneSommetsol4.getPositionEspace().getZ());
        assertEquals(-1 ,cordonneSommetMur4);
    }

    @Test
    public void deplacementBalleTest(){
        List<FormeCordonneSommet> mur1 = new ArrayList<>();
        List<FormeCordonneSommet> sol1 = new ArrayList<>();

        String map1 = "xxxxx\n" +
                "xooox\n" +
                "xooox\n" +
                "xooox\n" +
                "xxxxx";
        prepareMap(map1,sol1,mur1);

        //test1 vitesse x
        Point3D positionBalle1 = new Point3D(0,40,256);
        Espace3D test1 = new Espace3D(positionBalle1,sol1,mur1);
        Vecteur vecteur1 = new Vecteur(positionBalle1);
        double []vitesse1 = new double[]{10.0,0,0};
        Jeux.bougerBalleEspaceTemps(vitesse1,vecteur1,test1);

        assertEquals(2.55,vecteur1.getPossition()[0],0.01);
        assertEquals(40.0,vecteur1.getPossition()[1]);
        assertEquals(256.0,vecteur1.getPossition()[2]);

        //test2 vitesse x plus gros
        Vecteur vecteur2 = new Vecteur(positionBalle1);
        double []vitesse2 = new double[]{20.0,0,0};
        double []vitesse2_1 = new double[]{25,0,-15};
        double []vitesse2_2 = new double[]{0,0,20};
        Jeux.bougerBalleEspaceTemps(vitesse2,vecteur2,test1);

        assertEquals(10.2,vecteur2.getPossition()[0],0.01);
        assertEquals(40.0,vecteur2.getPossition()[1]);
        assertEquals(256.0,vecteur2.getPossition()[2]);

        Jeux.bougerBalleEspaceTemps(vitesse2_1,vecteur2,test1);

        assertEquals(28.8,vecteur2.getPossition()[0],0.01);
        assertEquals(40.0,vecteur2.getPossition()[1]);
        assertEquals(244.84,vecteur2.getPossition()[2],0.01);

        Jeux.bougerBalleEspaceTemps(vitesse2_2,vecteur2,test1);

        assertEquals(28.8,vecteur2.getPossition()[0],0.01);
        assertEquals(40.0,vecteur2.getPossition()[1]);
        assertEquals(255.04,vecteur2.getPossition()[2],0.01);



        //test3 vitesse z
        Vecteur vecteur3 = new Vecteur(positionBalle1);
        double []vitesse3 = new double[]{0,0,20};
        Jeux.bougerBalleEspaceTemps(vitesse3,vecteur3,test1);

        assertEquals(0.0,vecteur3.getPossition()[0],0.01);
        assertEquals(40.0,vecteur3.getPossition()[1]);
        assertEquals(266.2,vecteur3.getPossition()[2], 0.01);

        //test4 balle tombe et touche le sol
        Point3D positionBalle2 = new Point3D(0,50,256);
        Vecteur vecteur4 = new Vecteur(positionBalle2);
        double []vitesse4 = new double[]{0,0,0};
        test1.refreshPositionBalle(positionBalle2);
        Jeux.bougerBalleEspaceTemps(vitesse4,vecteur4,test1);

        assertEquals(0.0,vecteur4.getPossition()[0],0.01);
        assertEquals(40.0,vecteur4.getPossition()[1],0.2);
        assertEquals(256.0,vecteur4.getPossition()[2], 0.01);

        //test5 vitesse x et z
        Vecteur vecteur5 = new Vecteur(positionBalle1);
        double []vitesse5 = new double[]{25,0,-15};
        test1.refreshPositionBalle(positionBalle1);
        Jeux.bougerBalleEspaceTemps(vitesse5,vecteur5,test1);

        assertEquals(18.6,vecteur5.getPossition()[0],0.01);
        assertEquals(40.0,vecteur5.getPossition()[1],0.1);
        assertEquals(244.84,vecteur5.getPossition()[2], 0.01);

        //test6
        Vecteur vecteur6 = new Vecteur(positionBalle1);
        double []vitesse6 = new double[]{100,0,0};
        test1.refreshPositionBalle(positionBalle1);
        Jeux.bougerBalleEspaceTemps(vitesse6,vecteur6,test1);

        assertEquals(-79.1,vecteur6.getPossition()[0],2);
        assertEquals(40.0,vecteur6.getPossition()[1],0.1);
        assertEquals(256.0,vecteur6.getPossition()[2], 0.01);

        //test7
        Vecteur vecteur7 = new Vecteur(positionBalle1);
        double []vitesse7 = new double[]{-1,0,22.6};
        test1.refreshPositionBalle(positionBalle1);
        List<Point3D> liste = Jeux.bougerBalleEspaceTemps(vitesse7,vecteur7,test1);
        System.out.println(liste.size());
        for (Point3D point: liste)
            System.out.println(point);
    }


    //copie de controleur.Jeux
    private void prepareMap(String description ,List<FormeCordonneSommet> sol, List<FormeCordonneSommet> mur){
        int x = -2;
        int z = 2;

        for (int i = 0; i < description.length(); i ++) {
            if (description.charAt(i) == 'o') {
                Jeux.prepareMapForme(sol,x,0,z,0,0,4,64,64,64);
                x++;
            }
            else if (description.charAt(i) == '\n') {
                z++;
                x = -2;
            }
            else if (description.charAt(i) == 'x') {
                Jeux.prepareMapForme(mur,x,0,z,0,0,4,64,128,64);
                x++;
            }
            else if (description.charAt(i) == 'v') {
                Jeux.prepareMapForme(sol,x,0,z,0,0,4,64,64,64);
                x++;
            }
            else {
                Jeux.prepareMapForme(sol,x,0,z,0,0,4,64,32,64);
                x++;
            }
        }
    }

}