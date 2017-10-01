package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.ProjectRole;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.repository.TeamRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@UIScope
@SpringComponent
@SuppressWarnings("unused")
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team saveTeam(Team team) {
        return teamRepository.saveAndFlush(team);
    }

    @Override
    public void delete(Long id) {
        teamRepository.delete(id);
    }

    @Override
    public List<Team> getByTeamName(String teamName) {
        return teamRepository.findByTeamName(teamName);
    }

    @Override
    public Set<Team> getAllTeams() {
        List<Team> allTeams = teamRepository.findAll();
        Set<Team> allTeamsSet = new HashSet<>(allTeams);
        return allTeamsSet;
    }

    @Override
    @Transactional
    public boolean deleteTeams(Iterable<Team> teams) {
        if (teams != null) {
            for (Team team : teams) {
                int size = team.getEmployees().size();
                if (size > 0) {
                    for (Employee emp : team.getEmployees()) {
                        emp.setTeam(null);
                    }
                }
            }
            teamRepository.delete(teams);
            return true;
        }
        return false;
    }
}
