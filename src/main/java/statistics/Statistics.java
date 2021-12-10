package statistics;

import data.FutureMatch;
import data.Match;
import json.read.ReadFutureMatches;
import json.read.ReadJSON;
import json.read.ReadMatches;

import java.io.File;
import java.util.*;

public class Statistics {
    private ReadJSON<Match> readMatches = new ReadMatches<>();
    private ReadJSON<FutureMatch> readFutureMatches = new ReadFutureMatches<>();

    private List<FutureMatch> futureMatches;
    private List<Match> matches;

    public Statistics() {
        this.futureMatches = parseFutureMatchesWithOdds();
        this.matches = readMatches.read("src//main//resources//matches.json");
    }

    public List<Double> getListOfWonOdds() {
        Map<Match, FutureMatch> matches = getMapOfMatches();
        List<Double> wonOdds = new ArrayList<>();

        for (Map.Entry<Match, FutureMatch> entry : matches.entrySet()) {
            int homeTeamScore = entry.getKey().getHomeTeamScore();
            int awayTeamScore = entry.getKey().getAwayTeamScore();

            if (homeTeamScore > awayTeamScore) {
                wonOdds.add(entry.getValue().getHomeTeamOdd());
            } else if (homeTeamScore == awayTeamScore) {
                wonOdds.add(entry.getValue().getDrawOdd());
            } else {
                wonOdds.add(entry.getValue().getAwayTeamOdd());
            }
        }

        return wonOdds;
    }

    private Map<Match, FutureMatch> getMapOfMatches() {
        Map<Match, FutureMatch> output = new HashMap<>();
        for (FutureMatch futureMatch : futureMatches) {
            for (Match match : matches) {
                if (futureMatch.equals(match)) {
                    output.put(match, futureMatch);
                    break;
                }
            }
        }
        return output;
    }

    private List<FutureMatch> parseFutureMatchesWithOdds() {
        List<FutureMatch> output = new ArrayList<>();
        List<String> filesNames = getNamesOfFutureMatchesFiles();
        String path = "src//main//resources//";

        for (String fileName : filesNames) {
            List<FutureMatch> futureMatches = readFutureMatches.read(path + fileName);
            for (FutureMatch futureMatch : futureMatches) {
                if (!output.contains(futureMatch)) {
                    output.add(futureMatch);
                }
            }
        }

        return output;
    }

    private List<String> getNamesOfFutureMatchesFiles() {
        List<String> results = new ArrayList<>();
        File[] files = new File("src//main//resources").listFiles();

        if (files == null) return results;

        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName();
                if (name.contains("futureMatches")) {
                    results.add(name);
                }
            }
        }

        return results;
    }

    public List<FutureMatch> getFutureMatches() {
        return futureMatches;
    }
}
