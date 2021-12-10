package json;

import data.Team;
import org.json.JSONObject;

public class Save {
    protected static JSONObject createJsonObjectFromTeam(Team team) {
        JSONObject object = new JSONObject();
        object.put("name", team.getName());
        object.put("position", team.getPosition());
        object.put("gamesPlayed", team.getGamesPlayed());
        object.put("gamesWon", team.getGamesWon());
        object.put("gamesDrawn", team.getGamesDrawn());
        object.put("gamesLost", team.getGamesLost());
        object.put("goalsScored", team.getGoalsScored());
        object.put("goalsConceded", team.getGoalsConceded());
        object.put("points", team.getPoints());
        return object;
    }
}
