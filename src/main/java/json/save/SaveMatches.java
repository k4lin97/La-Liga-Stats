package json.save;

import data.Match;
import json.Save;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SaveMatches<T extends Match> extends Save implements SaveJSON<T> {
    private static final String PATH_TO_SAVE_MATCHES = "src//main//resources//matches.json";

    @Override
    public void save(List<T> listToSave) {
        if (listToSave == null || listToSave.isEmpty()) {
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        JSONArray jsonArray = new JSONArray();

        for (T t : listToSave) {
            JSONObject object = new JSONObject();
            Calendar date = t.getDate();
            object.put("date", format.format(date.getTime()));
            object.put("homeTeam", Save.createJsonObjectFromTeam(t.getHomeTeam()));
            object.put("awayTeam", Save.createJsonObjectFromTeam(t.getAwayTeam()));
            object.put("homeTeamScore", t.getHomeTeamScore());
            object.put("awayTeamScore", t.getAwayTeamScore());
            jsonArray.put(object);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_SAVE_MATCHES))) {
            writer.write(jsonArray.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
