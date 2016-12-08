package replication;

import org.apache.logging.log4j.core.util.IOUtils;
import protocol.model.Food;
import org.json.JSONArray;
import org.json.JSONObject;
import protocol.model.Cell;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Ольга on 01.12.2016.
 */
public class ReplicationJsonParser {

    public Food[] getFoodFromJson() {
        Food[] food = null;
        try {
//            FileReader fileReader = new FileReader(
//                    "C:\\JavaProjects\\atom\\zagar_server\\src\\main\\resources\\replicator.json");
            String content = new Scanner(new File("C:\\JavaProjects\\atom\\zagar_server\\src\\main\\resources\\replicator.json")).useDelimiter("\\Z").next();
            JSONObject obj = new JSONObject(content);

            JSONArray arr = obj.getJSONArray("food");
            food = new Food[arr.length()];
            for (int i = 0; i < arr.length(); i++) {
                int x = Integer.parseInt(arr.getJSONObject(i).getString("x"));
                int y = Integer.parseInt(arr.getJSONObject(i).getString("y"));
                food[i] = new Food();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return food;
    }
    public Cell[] getCellsFromJson() {
        Cell[] cells = null;
        try {
            String content = new Scanner(new File("C:\\JavaProjects\\atom\\zagar_server\\src\\main\\resources\\replicator.json")).useDelimiter("\\Z").next();
            JSONObject obj = new JSONObject(content);
            JSONArray arr = obj.getJSONArray("cells");
            cells = new Cell[arr.length()];
            for (int i = 0; i < arr.length(); i++) {
                int cellId = Integer.parseInt(arr.getJSONObject(i).getString("cellId"));
                int playerId = Integer.parseInt(arr.getJSONObject(i).getString("playerId"));
                boolean isVirus = Boolean.parseBoolean(arr.getJSONObject(i).getString("isVirus"));
                float size = Float.parseFloat(arr.getJSONObject(i).getString("size"));
                int x = Integer.parseInt(arr.getJSONObject(i).getString("x"));
                int y = Integer.parseInt(arr.getJSONObject(i).getString("y"));
                cells[i] = new Cell(cellId,playerId,isVirus,size,x,y);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cells;
    }
}
