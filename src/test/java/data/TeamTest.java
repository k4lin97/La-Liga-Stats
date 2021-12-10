package data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

class TeamTest {
    private Calendar date;
    private Team team;

    @BeforeEach
    void setUp() {
        date = Calendar.getInstance();
        team = new Team(1, "Barcelona", 5, 3, 2, 1, 30, 8, 11);
    }

    @Test
    void testToString() {
        Assertions.assertEquals("Pos: 1, name: Barcelona, gamesPlayed: 5, gamesWon: 3, gamesDrawn: 2, gamesLost: " +
                "1, goalsScored: 30, goalsConceded: 8, points: 11", team.toString());
    }
}