package data;

public class Team {
    private int position;
    private String name;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesDrawn;
    private int gamesLost;
    private int goalsScored;
    private int goalsConceded;
    private int points;

    public Team(int position, String name, int gamesPlayed, int gamesWon, int gamesDrawn, int gamesLost, int goalsScored, int goalsConceded, int points) {
        this.position = position;
        this.name = name;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesDrawn = gamesDrawn;
        this.gamesLost = gamesLost;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
        this.points = points;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesDrawn() {
        return gamesDrawn;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Pos: ");
        stringBuilder.append(position);
        stringBuilder.append(", name: ");
        stringBuilder.append(name);
        stringBuilder.append(", gamesPlayed: ");
        stringBuilder.append(gamesPlayed);
        stringBuilder.append(", gamesWon: ");
        stringBuilder.append(gamesWon);
        stringBuilder.append(", gamesDrawn: ");
        stringBuilder.append(gamesDrawn);
        stringBuilder.append(", gamesLost: ");
        stringBuilder.append(gamesLost);
        stringBuilder.append(", goalsScored: ");
        stringBuilder.append(goalsScored);
        stringBuilder.append(", goalsConceded: ");
        stringBuilder.append(goalsConceded);
        stringBuilder.append(", points: ");
        stringBuilder.append(points);

        return stringBuilder.toString();
    }
}
