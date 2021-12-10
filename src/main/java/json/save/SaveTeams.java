package json.save;

import data.Team;
import json.Save;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;

public class SaveTeams<T extends Team> extends Save implements SaveJSON<T> {
    private static final String PATH_TO_SAVE_TEAMS = "src//main//resources//teams.json";

    @Override
    public void save(List<T> listToSave) {
        if (listToSave == null || listToSave.isEmpty()) {
            return;
        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < listToSave.size(); i++) {
            Team team = listToSave.get(i);
            jsonArray.put(Save.createJsonObjectFromTeam(team));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_SAVE_TEAMS))) {
            writer.write(jsonArray.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
