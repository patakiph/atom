package replication.leaderboardtest;


import model.GameConstants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;

/**
 * Created by Ольга on 29.11.2016.
 */
public class LeaderboardJsonReader {
    private static JSONParser parser = new JSONParser();

    public static String[] read() {
        String[] result = new String[GameConstants.LEADERBOARD_SIZE];
        try {

            Object obj = parser.parse(new FileReader(
                    "C:\\JavaProjects\\atom\\zagar_server\\src\\main\\resources\\leaderboard.json"));
            HashMap<String, String> ldb = (HashMap<String, String>) obj;
            ArrayList<String> scores = new ArrayList<>(ldb.values());
            ArrayList<Map.Entry<String, String>> toSort = new ArrayList(ldb.entrySet());
            Collections.sort(toSort, (a, b) -> -(Integer.parseInt(a.getValue()) - Integer.parseInt(b.getValue())));
//            for (int i = 0; i < GameConstants.LEADERBOARD_SIZE; i++){
//                result[i] = ldb.get(scores.get(i)) + " " + scores.get(i);
//            }
            int i = 0;
            for (Map.Entry<String, String> entry : toSort) {
                result[i] = entry.getKey() + " " + entry.getValue();
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
