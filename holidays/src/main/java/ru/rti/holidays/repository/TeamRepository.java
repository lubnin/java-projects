package ru.rti.holidays.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rti.holidays.entity.Team;

import java.util.List;
import java.util.Set;

@Repository
@SuppressWarnings("unused")
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByTeamName(String teamName);
}
