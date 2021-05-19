package modele;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

import java.util.List;

public class MapMaker {

    protected Box spawn;
    protected Color themeSol;
    protected Color themeMur;

    protected List<FormeCordonneSommet> mur;
    protected List<FormeCordonneSommet> sol;

    protected Group prepareMap(String description) {
        int x = -2;
        int y = 0;
        int z = -2;
        int[] decal = new int[]{0,0,0};

        javafx.scene.Group group = new Group();
        for (int i = 0; i < description.length(); i ++) {
            if (description.charAt(i) == '|')
                prepareMapForme(mur,x,y+1,z,0,0,4,48,140,80,decal);
            else if (description.charAt(i) == '_')
                prepareMapForme(mur,x,y+1,z,0,0,4,80,140,48,decal);
            else if (description.charAt(i) == 'L')
                prepareMapForme(mur,x,y+1,z,0,0,4,48,140,48,decal);
            else if (description.charAt(i) == 'o' || description.charAt(i) == 'b' || description.charAt(i) == 'g') {
                Box bloc = (Box) prepareBox(x, y, z);

                if (description.charAt(i) == 'b') {
                    PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                    mat.setDiffuseMap(new Image("ressources/images/textures/boost.gif"));
                    mat.setDiffuseColor(themeSol.darker());
                    bloc.setMaterial(mat);
                    prepareMapForme(sol,x,y,z,0,0,3,64,64,64,decal);
                }
                else if (description.charAt(i) == 'g') {
                    PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                    mat.setDiffuseMap(new Image("ressources/images/textures/glace.png"));
                    mat.setDiffuseColor(Color.ALICEBLUE);
                    bloc.setMaterial(mat);
                    prepareMapForme(sol,x,y,z,0,0,2,64,64,64,decal);
                }
                else prepareMapForme(sol,x,y,z,0,0,4,64,64,64,decal);


                group.getChildren().add(bloc);
                x++;
            }
            else if (description.charAt(i) == 'j') {
                Box bloc = (Box) prepareBox(x, y, z);
                i++;
                //vers l'avant
                if (description.charAt(i) == '1') {
                    bloc.setHeight(96);
                    bloc.setTranslateZ(bloc.getTranslateZ() + 20);
                    bloc.setTranslateY(bloc.getTranslateY() - 40);
                    bloc.getTransforms().add(new Rotate(-45, Rotate.X_AXIS));
                    prepareMapForme(sol,x,y,z,90,45,4,64,64,64,decal);
                }

                //vers l'arriere
                else if (description.charAt(i) == '3') {
                    bloc.setHeight(96);
                    bloc.setTranslateZ(bloc.getTranslateZ() - 20);
                    bloc.setTranslateY(bloc.getTranslateY() - 40);
                    bloc.getTransforms().add(new Rotate(45, Rotate.X_AXIS));
                    prepareMapForme(sol,x,y,z,270,45,4,64,64,64,decal);
                }

                //vers la droite
                else if (description.charAt(i) == '2') {
                    bloc.setWidth(96);
                    bloc.setTranslateX(bloc.getTranslateX() + 20);
                    bloc.setTranslateY(bloc.getTranslateY() - 40);
                    bloc.getTransforms().add(new Rotate(-45, Rotate.Z_AXIS));
                    prepareMapForme(sol,x,y,z,0,45,4,64,64,64,decal);
                }

                //vers la gauche
                else if (description.charAt(i) == '4') {
                    bloc.setWidth(96);
                    bloc.setTranslateX(bloc.getTranslateX() - 20);
                    bloc.setTranslateY(bloc.getTranslateY() - 40);
                    bloc.getTransforms().add(new Rotate(45, Rotate.Z_AXIS));
                    prepareMapForme(sol,x,y,z,180,45,4,64,64,64,decal);
                }

                group.getChildren().add(bloc);
                x++;
            }
            else if (description.charAt(i) == '\n') {
                z++;
                x = -2;
            }
            else if (description.charAt(i) == 'r'){
                prepareMapForme(sol,x,y,z,0,45,4,64,64,64,decal);
                x++;
            }
            else if (description.charAt(i) == 'x') {
                prepareMapForme(mur,x,y,z,0,0,4,64,128,64,decal);
                Box bloc = (Box) prepareBox(x, y, z);
                bloc.setHeight(128);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/textures/patern.png"));
                mat.setDiffuseColor(themeMur);
                bloc.setMaterial(mat);
                group.getChildren().add(bloc);
                x++;
            }
            else if (description.charAt(i) == 'v') {
                prepareMapForme(sol,x,y,z,0,0,4,64,64,64,decal);
                spawn = (Box) prepareBox(x, y, z);
                PhongMaterial mat = (PhongMaterial) spawn.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/textures/patern.png"));
                mat.setDiffuseColor(Color.LIGHTGOLDENRODYELLOW);
                spawn.setMaterial(mat);
                group.getChildren().add(spawn);
                x++;
            }
            else if (description.charAt(i) == 't') {

                prepareMapForme(sol,x,y,z,0,0,5,64,16,64,decal);
                prepareMapForme(sol,x,y,z,0,0,1,16,64,16,decal);
                prepareMapForme(sol,x,y,z,0,0,1,64,60,64,decal);
                prepareMapForme(sol,x,y,z,0,0,4,64,64,64,decal);
                prepareMapForme(mur,x+1,y,z,0,0,4,64,76,64,decal);
                prepareMapForme(mur,x,y,z+1,0,0,4,64,76,64,decal);
                prepareMapForme(mur,x-1,y,z,0,0,2,64,76 ,64,decal);
                prepareMapForme(mur,x,y,z-1,0,0,2,64,76 ,64,decal);
                Box bloc = (Box) prepareBox(x, y, z);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/textures/patern.png"));
                mat.setBumpMap(new Image("ressources/images/textures/trou.png"));
                bloc.setMaterial(mat);
                group.getChildren().add(bloc);
                x++;
            }
            else if (description.charAt(i) == '+')
                y++;
            else if (description.charAt(i) == '-')
                y--;
            else if (description.charAt(i) == '0')
                x++;
            else if (description.charAt(i) == '1' || description.charAt(i) == '2' ||
                    description.charAt(i) == '3' || description.charAt(i) == '4') {

                Box bloc = (Box) prepareBox(x, y, z);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/textures/corner.png"));
                mat.setDiffuseColor(themeMur);
                bloc.setMaterial(mat);
                bloc.setDepth(90);
                bloc.setHeight(112);


                //coin haut droite
                if (description.charAt(i) == '1') {
                    prepareMapForme(mur,x,y,z,45,0,4,64,112,90,new int[]{24,0,24});
                    bloc.setTranslateZ(bloc.getTranslateZ() + 24);
                    bloc.setTranslateX(bloc.getTranslateX() + 24);
                    bloc.getTransforms().add(new Rotate(-45, Rotate.Y_AXIS));
                }

                //coin haut gauche
                if (description.charAt(i) == '2') {
                    prepareMapForme(mur,x,y,z,135,0,4,64,112,90,new int[]{-24,0,24});
                    bloc.setTranslateZ(bloc.getTranslateZ() + 24);
                    bloc.setTranslateX(bloc.getTranslateX() - 24);
                    bloc.getTransforms().add(new Rotate(45, Rotate.Y_AXIS));
                }

                //coin bas gauche
                if (description.charAt(i) == '3') {
                    prepareMapForme(mur,x,y,z,225,0,4,64,112,90,new int[]{-24,0,-24});
                    bloc.setTranslateZ(bloc.getTranslateZ() - 24);
                    bloc.setTranslateX(bloc.getTranslateX() - 24);
                    bloc.getTransforms().add(new Rotate(-45, Rotate.Y_AXIS));
                }

                //coin bas droite
                if (description.charAt(i) == '4') {
                    prepareMapForme(mur,x,y,z,315,0,4,64,112,90,new int[]{24,0,-24});
                    bloc.setTranslateZ(bloc.getTranslateZ() - 24);
                    bloc.setTranslateX(bloc.getTranslateX() + 24);
                    bloc.getTransforms().add(new Rotate(45, Rotate.Y_AXIS));
                }

                group.getChildren().add(bloc);
            }
            else {
                if (description.charAt(i) == 'i')
                    prepareMapForme(sol,x,y,z,0,0,4,64,64,64,decal);

                prepareMapForme(sol,x,y,x,0,0,6,80,40,80,decal);
                prepareMapForme(sol,x,y,z,0,0,1,64,48,64,decal);

                Box bloc = (Box) prepareBox(x, y, z);
                bloc.setTranslateY(bloc.getTranslateY() + 16);
                bloc.setHeight(48);
                PhongMaterial mat = (PhongMaterial) bloc.getMaterial();
                mat.setDiffuseMap(new Image("ressources/images/textures/eau.gif"));
                mat.setDiffuseColor(Color.SKYBLUE);
                bloc.setMaterial(mat);
                group.getChildren().add(bloc);
                x++;
            }
        }
        return group;
    }

    private Node prepareBox(int x, int y, int z) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(themeSol);
        material.setDiffuseMap(new Image("ressources/images/textures/patern.png"));
        Box box = new Box(64, 64, 64);
        box.setMaterial(material);
        box.setTranslateX(x * 64);
        box.setTranslateY(y * 64);
        box.setTranslateZ(z * 64);
        return box;
    }

    public static void prepareMapForme(List<FormeCordonneSommet> liste, int x, int y, int z, int angleXZ, int angleXY, int sol, int widgh, int heigh, int depth, int[] decal){

        FormeCordonneSommet box = new FormeCordonneSommet(new Point3D(x * 64 + decal[0],-y*64 - decal[1],z * 64 + decal[2]), widgh, heigh, depth, angleXY,angleXZ,sol);
        liste.add(box);
    }
}
