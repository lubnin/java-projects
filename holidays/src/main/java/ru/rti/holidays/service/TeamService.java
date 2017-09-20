package ru.rti.holidays.service;

import ru.rti.holidays.entity.Team;

import java.util.List;

public interface TeamService {
    Team addTeam(Team team);
    void delete(Long id);
    List<Team> getByTeamName(String teamName);
    List<Team> getAllTeams();
}
