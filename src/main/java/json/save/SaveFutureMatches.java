package json.save;

import data.FutureMatch;
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

public class SaveFutureMatches<T extends FutureMatch> extends Save implements SaveJSON<T> {
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
            object.put("homeTeamOdd", t.getHomeTeamOdd());
            object.put("drawOdd", t.getDrawOdd());
            object.put("awayTeamOdd", t.getAwayTeamOdd());
            jsonArray.put(object);
        }

        format = new SimpleDateFormat("dd_MM_yyyy");
        Calendar savingDate = Calendar.getInstance();
        String PATH_TO_SAVE_MATCHES = "src//main//resources//futureMatches" + format.format(savingDate.getTime()) + ".json";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_SAVE_MATCHES))) {
            writer.write(jsonArray.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
