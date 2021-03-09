package modele;

import javafx.scene.shape.Box;
import javafx.scene.shape.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Plateforme {

    private final Sol eau = new Sol(true, 0);
    private final Sol beton = new Sol(false, 0.9);
    private final Sol bois = new Sol(false, 0.5);
    private final Sol gazon = new Sol(false, 2);

    public static String getNiveau1() {
        return new String("xooox\n" +
                "xooox\n" +
                "xoooxxxxxxx\n" +
                "xooooooooox\n" +
                "xooooooooox\n" +
                "xooooooooox\n" +
                "xxxxxxxxxxx");
    }
}
