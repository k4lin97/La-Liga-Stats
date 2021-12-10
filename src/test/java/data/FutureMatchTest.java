package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;


class FutureMatchTest {
    private FutureMatch match;
    private Calendar date;
    private Team homeTeam;
    private Team awayTeam;
    private double homeTeamOdd;
    private double drawOdd;
    private double awayTeamOdd;

    @BeforeEach
    void setUp() {
        date = Calendar.getInstance();
        homeTeam = new Team(1, "Barcelona", 5, 3, 2, 1, 30, 8, 11);
        awayTeam = new Team(10, "Cadiz", 7, 2, 0, 5, 14, 28, 6);
        homeTeamOdd = 1.5;
        drawOdd = 1.8;
        awayTeamOdd = 3.3;

        match = new FutureMatch(date, homeTeam, awayTeam, homeTeamOdd, drawOdd, awayTeamOdd);
    }

    @Test
    void testToString() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();

        Assertions.assertEquals("Barcelona 1.5 | Draw 1.8 | 3.3 Cadiz | Data: " + format.format(calendar.getTime()), match.toString());
    }
}