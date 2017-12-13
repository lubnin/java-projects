package ru.rti.holidays.utility;

import ru.rti.holidays.entity.Team;

public class TeamUtils {
    public static String getDelimitedTeamsString(Iterable<Team> teams, String separator) {
        if (teams == null || separator == null) {
            return GlobalConstants.EMPTY_STRING;
        }

        StringBuilder sbTeams = new StringBuilder();
        teams.forEach(team -> {
            if (sbTeams.length() > 0) {
                sbTeams.append(separator);
            }
            sbTeams.append(team.getTeamName());
        });

        return sbTeams.toString();
    }
}
