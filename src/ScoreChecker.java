import java.io.*;
import java.nio.file.Files;
import java.util.*;

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
            entree = entree.replaceAll("\\s", "");

            String[] scores = entree.split(",");
            HashMap<Integer, String> map = new HashMap<>();
            for (int x = 0; x < (scores.length / 2); x++) {
                map.put(Integer.parseInt(scores[x * 2]), scores[1 + 2 * x]);
            }

            List<Integer> scoresTries = new ArrayList<>(map.keySet());
            Collections.sort(scoresTries);
            Collections.reverse(scoresTries);

            String[] top5 = new String[5];
            for (int i = 0; i < 5; i++)
                if (i < scoresTries.size())
                    top5[i] = scoresTries.get(i) + " - " + map.get(scoresTries.get(i));
                else
                    top5[i] = "0 - vide";
            System.out.println(Arrays.toString(top5));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
