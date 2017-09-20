package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.repository.TeamRepository;

import java.util.List;

@Service
@UIScope
@SpringComponent
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team addTeam(Team team) {
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
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}
