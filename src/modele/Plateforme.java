package modele;

public class Plateforme {

    public static String getNiveau(int niv) {
        switch (niv) {
            case 5 :
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
                        "xbbbx\n" +
                        "xoxox\n" +
                        "x___x\n+" +
                        "xooox+xxxx\n" +
                        "-xbbb|+o_ox\n" +
                        "-xoxb|+|+t-|x\n" +
                        "-xoob|+o_ox\n" +
                        "-xxxxx+xxxx";
            case 4 :
                return  "0000xxxxxxx\n" +
                        "xxxxx     xxxxx\n" +
                        "xbbb|   L_|bbbx\n" +
                        "xbvb|_L | |btbx\n" +
                        "xbbb| | | |bbbx\n" +
                        "xxxxx |_| xxxxx\n" +
                        "0000xxxxxxx";
            case 1 :
                return"xxxxxxxxxxxxx-xxxxxx\n+" +
                        "xoooooooooooor- |oox\n+" +
                        "xovobbbbbbbbbr- |oox\n+" +
                        "xoooooooooooor- |oox\n+" +
                        "xxxxxxxxxxxxx-xxxxxx\n";

            default:
                return "xxxxx\n" +
                        "xooox\n" +
                        "xovox\n" +
                        "xooox\n" +
                        "xgggx\n" +
                        "xgggx\n" +
                        "xgggx\n" +
                        "xgggx\n" +
                        "xgggx\n" +
                        "xgggx\n" +
                        "xgggx\n" +
                        "xgggx\n" +
                        "xooox\n" +
                        "xotox\n" +
                        "xooox\n" +
                        "xxxxx";
        }
    }
}
