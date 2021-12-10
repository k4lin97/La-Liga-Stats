package json;

import data.Team;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Read {
    protected static String readFromFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            return null;
        }
        return stringBuilder.toString();
    }

    protected static Team createTeamFromJson(JSONObject jsonObject) {
        int position;
        String name;
        int gamesPlayed;
        int gamesWon;
        int gamesDrawn;
        int gamesLost;
        int goalsScored;
        int goalsConceded;
        int points;

        try {
            position = jsonObject.getInt("position");
            name = jsonObject.getString("name");
            gamesPlayed = jsonObject.getInt("gamesPlayed");
            gamesWon = jsonObject.getInt("gamesWon");
            gamesDrawn = jsonObject.getInt("gamesDrawn");
            gamesLost = jsonObject.getInt("gamesLost");
            goalsScored = jsonObject.getInt("goalsScored");
            goalsConceded = jsonObject.getInt("goalsConceded");
            points = jsonObject.getInt("points");
        } catch (JSONException e) {
            System.out.println("Couldn't read - writing an empty team");
            position = 0;
            name = "";
            gamesPlayed = 0;
            gamesWon = 0;
            gamesDrawn = 0;
            gamesLost = 0;
            goalsScored = 0;
            goalsConceded = 0;
            points = 0;
        }
        Team team = new Team(position, name, gamesPlayed, gamesWon, gamesDrawn,
                gamesLost, goalsScored, goalsConceded, points);
        return team;
    }

    protected Calendar convertStringToCalendar(String stringDate) {
        Calendar date = Calendar.getInstance();
        String regex = "(\\d+)-(\\d+)-(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringDate);

        if (!matcher.find()) return date;

        int day = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
        int year = Integer.parseInt(matcher.group(3));

        date.set(Calendar.DAY_OF_MONTH, day);
        date.set(Calendar.MONTH, month - 1);
        date.set(Calendar.YEAR, year);

        return date;
    }
}
