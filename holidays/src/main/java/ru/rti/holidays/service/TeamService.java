package ru.rti.holidays.service;

import ru.rti.holidays.entity.Team;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public interface TeamService {
    Team saveTeam(Team team);
    void delete(Long id);
    List<Team> getByTeamName(String teamName);
    List<Team> getAllTeamsSortedByTeamNameAsc();
    boolean deleteTeams(Iterable<Team> teams);
}
