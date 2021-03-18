import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreChecker {

    public static void ecrire(String informations) {
        File file = new File("src/scoreboard");
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            writer.write(informations);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void topScores() {
        File file = new File("src/scoreboard");
        try {
            String entree = new String(Files.readAllBytes(file.toPath()));
            entree = entree.replace("\n", ",").replace("\r", "");


            String[] token = entree.split(",");
            List<String> scores = new ArrayList<>(Arrays.asList(token));
            scores.sort(Comparator.comparing((String sc) -> sc.substring(0, 2)));
            Collections.reverse(scores);

            String[] top5 = new String[5];
            for (int i = 0; i < 5; i++)
                if (i < scores.size())
                    top5[i] = scores.get(i);
                else
                    top5[i] = "0 - vide";

            System.out.println(Arrays.toString(top5));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
