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

    public FormeCordonneSommet(Point3D position1,double x, double y, double z,double angleXY1,double angleXZ1,int sol,boolean triangle){
        super(position1,x,y,z,angleXY1,angleXZ1,sol,triangle);


        double formeX = getPositionEspace().getX();
        double formeY = getPositionEspace().getY();
        double formeZ = getPositionEspace().getZ();

        double width = getWidth()/2;
        double height = getHeight()/2;
        double depth = getDepth()/2;

        if (isTriangle()){

        }
        else{
            Point3D numero1 = trouverPointFormeAngle3D(formeX - width,formeY + height,formeZ - depth,true);
            Point3D numero2 = trouverPointFormeAngle3D(formeX + width,formeY + height,formeZ - depth,false);
            Point3D numero3 = trouverPointFormeAngle3D(formeX + width,formeY + height,formeZ + depth,false);
            Point3D numero4 = trouverPointFormeAngle3D(formeX - width,formeY + height,formeZ + depth,true);
            Point3D numero5 = trouverPointFormeAngle3D(formeX - width,formeY - height,formeZ - depth,true);
            Point3D numero6 = trouverPointFormeAngle3D(formeX + width,formeY - height,formeZ - depth,false);
            Point3D numero7 = trouverPointFormeAngle3D(formeX + width,formeY - height,formeZ + depth,false);
            Point3D numero8 = trouverPointFormeAngle3D(formeX - width,formeY - height,formeZ + depth,true);

            Point2D pointXZ1 = new Point2D(numero1.getX(),numero1.getZ());
            Point2D pointXZ2 = new Point2D(numero2.getX(),numero2.getZ());
            Point2D pointXZ3 = new Point2D(numero3.getX(),numero3.getZ());
            Point2D pointXZ4 = new Point2D(numero4.getX(),numero4.getZ());

            Point2D pointXY1 = trouverPointFormeAngleXY(formeX - width,formeY + height,true);
            Point2D pointXY2 = trouverPointFormeAngleXY(formeX + width,formeY + height,false);
            Point2D pointXY3 = trouverPointFormeAngleXY(formeX + width,formeY - height,false);
            Point2D pointXY4 = trouverPointFormeAngleXY(formeX - width,formeY - height,true);

            sommets = new Point3D[]{numero1,numero2,numero3,numero4,numero5,numero6,numero7,numero8};
            angleXZTableau = new Point2D[]{pointXZ1,pointXZ2,pointXZ3,pointXZ4};
            angleXYTableau = new Point2D[]{pointXY1,pointXY2,pointXY3,pointXY4};

        }
    }

    private Point2D trouverPointFormeAngleXY(double x, double y,boolean gauche){
        if (gauche){
            return new Point2D(x,y);
        }
        else {

            double elevation = getWidth() * Math.tan(Math.toRadians(getAngleXY()));
        /*
            double hypoXY = Math.sqrt(Math.pow(getPositionEspace().getX() - x, 2) + Math.pow(getPositionEspace().getY() - y, 2));
            double angleXY = resolutionangle(x - getPositionEspace().getX(), y - getPositionEspace().getY());
            y = arroudir((hypoXY * Math.sin(Math.toRadians(getAngleXY() + angleXY))) + getPositionEspace().getY());
*/
            return new Point2D(x, y + elevation);
        }
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

    private Point3D trouverPointFormeAngle3D(double x, double y, double z,boolean gauche){
        //System.out.println("\n" + x + " , " + y + " , " + z);

        Point2D pointXY = trouverPointFormeAngleXY(x,y,gauche);
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

}


