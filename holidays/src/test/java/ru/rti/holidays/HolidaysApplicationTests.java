package ru.rti.holidays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.entity.Team;
import ru.rti.holidays.service.EmployeeService;
import ru.rti.holidays.utility.CommonUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;
import ru.rti.holidays.utility.GlobalConstants;
import ru.rti.holidays.utility.TeamUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
/*@SqlGroup({
		@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
		@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})*/
public class HolidaysApplicationTests {
/*	@Autowired
	private JdbcTemplate jdbcTemplate;*/
	//@Autowired
	//EmployeeService employeeServiceImpl;

	@Test
	public void contextLoads() {
/*		String selectQuery = "SELECT * from EMPLOYEE WHERE ID = 1";

		List<Map<String, Object>> resultSet = jdbcTemplate
				.queryForList(selectQuery);

		System.out.println(resultSet);*/
	}

	@Test
	public void testCommonUtils_checkIfEmpty() {
		assertThat(CommonUtils.checkIfEmpty("")).isEqualTo(true);
		assertThat(CommonUtils.checkIfEmpty(null)).isEqualTo(true);
	}

	@Test
	public void testCommonUtils_checkIfAnyIsNull() {
		assertThat(CommonUtils.checkIfAnyIsNull("non-null", null, 9)).isEqualTo(true);
		assertThat(CommonUtils.checkIfAnyIsNull("non-null", new Object(), -23)).isEqualTo(false);
	}

	@Test
	public void testCommonUtils_getValueOrEmptyString() {
		assertThat(CommonUtils.getValueOrEmptyString(null)).isEqualTo(GlobalConstants.EMPTY_STRING);
		assertThat(CommonUtils.getValueOrEmptyString("non-null value")).isEqualTo("non-null value");
	}

	@Test
	public void testTeamUtils_getDelimitedTeamsString() {
		List<Team> teams = new ArrayList<>();
		Team team1 = new Team();
		team1.setTeamName("Team1");
		Team team2 = new Team();
		team2.setTeamName("Team2");
		teams.add(team1);
		teams.add(team2);
		assertThat(TeamUtils.getDelimitedTeamsString(teams, ", ")).isEqualTo("Team1, Team2");
		assertThat(TeamUtils.getDelimitedTeamsString(teams, "; ")).isEqualTo("Team1; Team2");
	}
}
