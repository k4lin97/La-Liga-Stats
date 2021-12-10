package data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FutureMatch extends Match {
    private double homeTeamOdd;
    private double drawOdd;
    private double awayTeamOdd;

    public FutureMatch(Calendar date, Team homeTeam, Team awayTeam, double homeTeamOdd, double drawOdd, double awayTeamOdd) {
        super(date, homeTeam, awayTeam, -1, -1);
        this.homeTeamOdd = homeTeamOdd;
        this.drawOdd = drawOdd;
        this.awayTeamOdd = awayTeamOdd;
    }

    public double getHomeTeamOdd() {
        return homeTeamOdd;
    }

    public double getDrawOdd() {
        return drawOdd;
    }

    public double getAwayTeamOdd() {
        return awayTeamOdd;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getHomeTeam().getName());
        stringBuilder.append(" ");
        stringBuilder.append(homeTeamOdd);
        stringBuilder.append(" | Draw ");
        stringBuilder.append(drawOdd);
        stringBuilder.append(" | ");
        stringBuilder.append(awayTeamOdd);
        stringBuilder.append(" ");
        stringBuilder.append(getAwayTeam().getName());

        stringBuilder.append(" | Data: ");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        stringBuilder.append(format.format(getDate().getTime()));

        return stringBuilder.toString();
    }
}
