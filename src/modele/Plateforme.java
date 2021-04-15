package modele;

public class Plateforme {

    public static String getNiveau(int niv) {
        switch (niv) {
            case 1 :
                return "xxxxx\n" +
                        "xooox\n" +
                        "xovox\n" +
                        "xooox\n" +
                        "xooox\n" +
                        "xooox\n" +
                        "xoooxxxxxxx\n" +
                        "xooooooooox\n" +
                        "xoooooootox\n" +
                        "xooooooooox\n" +
                        "xxxxxxxxxxx";
            case 2 :
                return "xxxxx\n" +
                        "xooox\n" +
                        "xovox\n" +
                        "xooox\n" +
                        "xooox\n" +
                        "xo ox\n" +
                        "xooox\n" +
                        "xooox\n" +
                        "x o x\n" +
                        "xooox\n" +
                        "xooox\n" +
                        "xo ox\n" +
                        "xooox\n" +
                        "xooox\n" +
                        "xotox\n" +
                        "xooox\n" +
                        "xxxxx\n";
            default:
                return "xxxxx\n" +
                        "xooox\n" +
                        "xovox\n" +
                        "xooox\n" +
                        "xxxxx\n";
        }
    }
}
