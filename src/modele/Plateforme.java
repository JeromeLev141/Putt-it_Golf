package modele;

public class Plateforme {

    private final Sol eau = new Sol(true, 0);
    private final Sol beton = new Sol(false, 0.9);
    private final Sol bois = new Sol(false, 0.5);
    private final Sol gazon = new Sol(false, 2);

    public static String getNiveau1() {
        return "xooox\n" +
                "xooox\n" +
                "xoooxxxxxxx\n" +
                "xooooooooox\n" +
                "xooooooooox\n" +
                "xooooooooox\n" +
                "xxxxxxxxxxx";
    }

    public static String getNiveau2() {
        return "xooox\n" +
                "xo ox\n" +
                "xooox\n" +
                "xooox\n" +
                "x o x\n" +
                "xooox\n" +
                "xooox\n" +
                "xo ox\n" +
                "xooox\n" +
                "xooox\n" +
                "xooox\n" +
                "xooox\n" +
                "xxxxx\n";
    }
}
