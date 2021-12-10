package json.read;

import data.Match;
import data.Team;
import json.Read;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReadMatches<T extends Match> extends Read implements ReadJSON<T> {
    @Override
    public List<T> read(String pathToFile) {
        List<T> matches = new ArrayList<>();

        String stringJSON = Read.readFromFile(pathToFile);
        if (stringJSON == null || stringJSON.isEmpty()) {
            return matches;
        }

        JSONArray jsonArray = new JSONArray(stringJSON);
        String stringDate;
        Calendar date;
        Team homeTeam;
        Team awayTeam;
        int homeTeamScore;
        int awayTeamScore;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            try {
                stringDate = jsonObject.getString("date");
                date = convertStringToCalendar(stringDate);
                homeTeam = Read.createTeamFromJson(jsonObject.getJSONObject("homeTeam"));
                awayTeam = Read.createTeamFromJson(jsonObject.getJSONObject("awayTeam"));
                homeTeamScore = jsonObject.getInt("homeTeamScore");
                awayTeamScore = jsonObject.getInt("awayTeamScore");
            } catch (JSONException e) {
                System.out.println("Couldn't read - writing an empty team");
                date = Calendar.getInstance();
                homeTeam = new Team(0, "", 0, 0, 0, 0, 0, 0, 0);
                awayTeam = new Team(0, "", 0, 0, 0, 0, 0, 0, 0);
                homeTeamScore = 0;
                awayTeamScore = 0;
            }

            // T can be only a Match class, because none is derived from it
            @SuppressWarnings("unchecked")
            T match = (T) new Match(date, homeTeam, awayTeam, homeTeamScore, awayTeamScore);
            matches.add(match);
        }
        return matches;
    }
}
