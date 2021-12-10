package json.read;

import data.Team;
import json.Read;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReadTeams<T extends Team> extends Read implements ReadJSON<T> {
    @Override
    public List<T> read(String pathToFile) {
        if (pathToFile == null || pathToFile.isEmpty()) {
            return null;
        }

        List<T> teams = new ArrayList<>();
        String stringJSON = Read.readFromFile(pathToFile);

        if (stringJSON == null) return null;

        JSONArray jsonArray = new JSONArray(stringJSON);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            // T can be only a Team class, because none is derived from it
            @SuppressWarnings("unchecked")
            T team = (T) Read.createTeamFromJson(jsonObject);
            teams.add(team);
        }

        return teams;
    }
}
