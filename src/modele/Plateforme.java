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
                        "xooox\n" +
                        "xoxox\n" +
                        "x___x\n+" +
                        "xooox+xxxx\n" +
                        "-xooo|+ooox\n" +
                        "-xoxo|+o+t-ox\n" +
                        "-xooo|+ooox\n" +
                        "-xxxxx+xxxx";
            case 4 :
                return  "0000xxxxxxx\n" +
                        "xxxxx     xxxxx\n" +
                        "xooo|   |_|ooox\n" +
                        "xovo|_| | |otox\n" +
                        "xooo| | | |ooox\n" +
                        "xxxxx |_| xxxxx\n" +
                        "0000xxxxxxx";
            default:
                return "xxxxx\n" +
                        "xooox\n" +
                        "xovox\n" +
                        "xooox\n" +
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
