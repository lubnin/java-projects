package ru.rti.holidays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.repository.EmployeeRepository;

@SpringBootApplication
public class HolidaysApplication {

	private static final Logger log = LoggerFactory.getLogger(HolidaysApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HolidaysApplication.class, args);
	}
}
