package modele;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class FormeCordonneSommet extends Forme {

    private Point3D[] sommets;
    private Point2D[] angleXYTableau;
    private Point2D[] angleXZTableau;
    private Point2D[] angleYZTableau;

    public FormeCordonneSommet(Point3D position1,double x, double y, double z,double angleXY1,double angleXZ1,int sol){
        super(position1,x,y,z,angleXY1,angleXZ1,sol);


        double formeX = getPositionEspace().getX();
        double formeY = getPositionEspace().getY();
        double formeZ = getPositionEspace().getZ();

        double width = getWidth()/2;
        double height = getHeight()/2;
        double depth = getDepth()/2;

        Point3D numero1 = trouverPointFormeAngle3D(formeX - width,formeY + height,formeZ - depth);
        Point3D numero2 = trouverPointFormeAngle3D(formeX + width,formeY + height,formeZ - depth);
        Point3D numero3 = trouverPointFormeAngle3D(formeX + width,formeY + height,formeZ + depth);
        Point3D numero4 = trouverPointFormeAngle3D(formeX - width,formeY + height,formeZ + depth);
        Point3D numero5 = trouverPointFormeAngle3D(formeX - width,formeY - height,formeZ - depth);
        Point3D numero6 = trouverPointFormeAngle3D(formeX + width,formeY - height,formeZ - depth);
        Point3D numero7 = trouverPointFormeAngle3D(formeX + width,formeY - height,formeZ + depth);
        Point3D numero8 = trouverPointFormeAngle3D(formeX - width,formeY - height,formeZ + depth);

        System.out.println("-----------------------------");
        System.out.println(numero1);
        System.out.println(numero2);
        System.out.println(numero3);
        System.out.println(numero4);
        System.out.println(numero5);
        System.out.println(numero6);
        System.out.println(numero7);
        System.out.println(numero8);
        System.out.println("-----------------------------");


        Point2D pointXZ1 = new Point2D(numero1.getX(),numero1.getZ());
        Point2D pointXZ2 = new Point2D(numero2.getX(),numero2.getZ());
        Point2D pointXZ3 = new Point2D(numero3.getX(),numero3.getZ());
        Point2D pointXZ4 = new Point2D(numero4.getX(),numero4.getZ());

        Point2D pointXY1 = trouverPointFormeAngleXY(formeX - width,formeY + height);
        Point2D pointXY2 = trouverPointFormeAngleXY(formeX + width,formeY + height);
        Point2D pointXY3 = trouverPointFormeAngleXY(formeX + width,formeY - height);
        Point2D pointXY4 = trouverPointFormeAngleXY(formeX - width,formeY - height);

        Point2D pointYZ1 = new Point2D(formeZ - depth, numero2.getY());
        Point2D pointYZ2 = new Point2D(formeZ + depth, numero3.getY());
        Point2D pointYZ3 = new Point2D(formeZ - depth, numero6.getY());
        Point2D pointYZ4 = new Point2D(formeZ + depth, numero7.getY());


        sommets = new Point3D[]{numero1,numero2,numero3,numero4,numero5,numero6,numero7,numero8};
        angleXZTableau = new Point2D[]{pointXZ1,pointXZ2,pointXZ3,pointXZ4};
        angleXYTableau = new Point2D[]{pointXY1,pointXY2,pointXY3,pointXY4};
        angleYZTableau = new Point2D[]{pointYZ1,pointYZ2,pointXZ3,pointYZ4};
/*
            System.out.println(numero1.distance(numero2) + " , " + numero2.distance(numero3));
            System.out.println(numero2.distance(numero3) + " , " + numero3.distance(numero4));
            System.out.println(numero1.angle(numero2,numero4));
            System.out.println(numero1.angle(numero4,numero2));
            System.out.println(numero2.angle(numero1,numero3));
            System.out.println(numero3.angle(numero2,numero4));
            System.out.println(numero4.angle(numero3,numero1));
            */
    }

    private Point2D trouverPointFormeAngleXY(double x, double y){
        double hypoXY = Math.sqrt(Math.pow(getPositionEspace().getX() - x, 2) + Math.pow(getPositionEspace().getY() - y, 2));
        double angleXY = resolutionangle(x -getPositionEspace().getX(),y - getPositionEspace().getY());
        y = arroudir((hypoXY * Math.sin(Math.toRadians(getAngleXY() + angleXY))) + getPositionEspace().getY());
        x = arroudir((hypoXY * Math.cos(Math.toRadians(getAngleXY() + angleXY))) + getPositionEspace().getX());

        return new Point2D(x,y);
    }

    public static Point2D trouverPointFormeAngleXZ(FormeCordonneSommet forme, double x, double z, double angle){

        double hypoXZ = Math.sqrt( Math.pow(forme.getPositionEspace().getX() - x, 2) + Math.pow(forme.getPositionEspace().getZ() - z, 2));
        if (hypoXZ == 0)
            return new Point2D(x,z);
        double angleXZ = resolutionangle(x - forme.getPositionEspace().getX(),z - forme.getPositionEspace().getZ());
        x = arroudir((hypoXZ * Math.cos(Math.toRadians(angle + angleXZ))) + forme.getPositionEspace().getX());
        z = arroudir((hypoXZ * Math.sin(Math.toRadians(angle + angleXZ))) + forme.getPositionEspace().getZ());
        return new Point2D(x,z);
    }

    private Point3D trouverPointFormeAngle3D(double x, double y, double z){
        System.out.println("\n" + x + " , " + y + " , " + z);

        Point2D pointXY = trouverPointFormeAngleXY(x,y);
        x = pointXY.getX();
        y = pointXY.getY();
        Point2D pointXZ = trouverPointFormeAngleXZ(this,x,z,this.getAngleXZ());
        x = pointXZ.getX();
        z = pointXZ.getY();



        return new Point3D(x,y,z);
    }

    private static double resolutionangle(double point1, double point2){
        double angle;
        angle = Math.toDegrees(Math.atan(point2/point1));

        if ((point1 < 0 && point2 >0) || (point1< 0 && point2<0))
            angle += 180;
        else if (point1 > 0 && point2 < 0)
            angle += 360;

        return angle;
    }

    private static double arroudir(double nombre){
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.########",symbols);
        double chiffre = Double.parseDouble(df.format(nombre));
        if (chiffre == -0.0)
            chiffre = 0;
        return chiffre;
    }

    public Point3D[] getSommets() {
        return sommets;
    }

    public Point2D[] getAngleXYTableau() {
        return angleXYTableau;
    }

    public Point2D[] getAngleXZTableau() {
        return angleXZTableau;
    }

    public Point2D[] getAngleYZTableau() {
        return angleYZTableau;
    }
/*
    public static List<FormeCordonneSommet> listeFormeToListFormeCordonne(List<Forme> liste){
        List<FormeCordonneSommet> cordonnerForme = new ArrayList<>();

        for (Forme forme1:liste) {
            cordonnerForme.add(new FormeCordonneSommet(forme1));
        }
        return cordonnerForme;
    }
*/

}


