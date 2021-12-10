package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.ShortMessage;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class MatchTest {
    private Match match;
    private Match sameMatch;
    private Match notTheSameMatch;
    private Calendar date;
    private Team homeTeam;
    private Team awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;

    @BeforeEach
    void setUp() {
        date = Calendar.getInstance();
        homeTeam = new Team(1, "Barcelona", 5, 3, 2, 1, 30, 8, 11);
        awayTeam = new Team(10, "Cadiz", 7, 2, 0, 5, 14, 28, 6);
        homeTeamScore = 1;
        awayTeamScore = 3;

        match = new Match(date, homeTeam, awayTeam, homeTeamScore, awayTeamScore);
        sameMatch = new Match(date, homeTeam, awayTeam, homeTeamScore, awayTeamScore);
        notTheSameMatch = new Match(date, awayTeam, homeTeam, homeTeamScore, awayTeamScore);
    }

    @Test
    void testToString() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();

        Assertions.assertEquals("Barcelona 1 - 3 Cadiz | Data: " + format.format(calendar.getTime()), match.toString());
    }

    @Test
    void testEquals_equalObjects() {
        Assertions.assertEquals(match, sameMatch);
    }

    @Test
    void testEquals_notEqualObjects() {
        Assertions.assertNotEquals(match, notTheSameMatch);
    }
}