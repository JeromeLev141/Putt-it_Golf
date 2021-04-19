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
                        "xo_ox\n" +
                        "x| |x\n" +
                        "xo_ox\n" +
                        "x_o_x\n" +
                        "x | x\n" +
                        "x_o_x\n" +
                        "xo_ox\n" +
                        "x| |x\n" +
                        "xo_ox\n" +
                        "xooox\n" +
                        "xotox\n" +
                        "xooox\n" +
                        "xxxxx";
            case 3 :
                return "xxxxx\n" +
                        "xooox\n" +
                        "xovox\n" +
                        "x___x\n+" +
                        "xooox\n" +
                        "xotox\n" +
                        "xooox\n" +
                        "xxxxx";
            default:
                return "xxxxx\n" +
                        "xooox\n" +
                        "xovox\n" +
                        "xooox\n" +
                        "xotox\n" +
                        "xooox\n" +
                        "xxxxx";
        }
    }

    public static String getNiveau3(){
        return "xooox\n" +
                "xxxxxxoooxxxxx\n" +
                "x      g     x\n" +
                "xggggggg     x\n";
    }
}
