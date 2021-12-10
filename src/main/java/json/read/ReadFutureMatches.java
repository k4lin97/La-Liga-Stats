package json.read;

import data.FutureMatch;
import data.Team;
import json.Read;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReadFutureMatches<T extends FutureMatch> extends Read implements ReadJSON<T> {
    @Override
    public List<T> read(String pathToFile) {
        if (pathToFile == null || pathToFile.isEmpty()) {
            return null;
        }

        List<T> futureMatches = new ArrayList<>();
        String stringJSON = Read.readFromFile(pathToFile);
        JSONArray jsonArray = new JSONArray(stringJSON);
        String stringDate;
        Calendar date;
        Team homeTeam;
        Team awayTeam;
        double homeTeamOdd;
        double drawOdd;
        double awayTeamOdd;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            try {
                stringDate = jsonObject.getString("date");
                date = convertStringToCalendar(stringDate);
                homeTeam = Read.createTeamFromJson(jsonObject.getJSONObject("homeTeam"));
                awayTeam = Read.createTeamFromJson(jsonObject.getJSONObject("awayTeam"));
                homeTeamOdd = jsonObject.getDouble("homeTeamOdd");
                drawOdd = jsonObject.getDouble("drawOdd");
                awayTeamOdd = jsonObject.getDouble("awayTeamOdd");
            } catch (JSONException e) {
                System.out.println("Couldn't read - writing an empty team");
                date = Calendar.getInstance();
                homeTeam = new Team(0, "", 0, 0, 0, 0, 0, 0, 0);
                awayTeam = new Team(0, "", 0, 0, 0, 0, 0, 0, 0);
                homeTeamOdd = 0d;
                drawOdd = 0d;
                awayTeamOdd = 0d;
            }

            // T can be only a FutureMatch class, because none is derived from it
            @SuppressWarnings("unchecked")
            T match = (T) new FutureMatch(date, homeTeam, awayTeam, homeTeamOdd, drawOdd, awayTeamOdd);
            futureMatches.add(match);
        }
        return futureMatches;
    }
}
