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
/*
	@Bean
	public CommandLineRunner loadData(EmployeeRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Employee("Jack", "Bauer"));
			repository.save(new Employee("Chloe", "O'Brian"));
			repository.save(new Employee("Kim", "Bauer"));
			repository.save(new Employee("David", "Palmer"));
			repository.save(new Employee("Michelle", "Dessler"));

			// fetch all customers
			log.info("Employees found with findAll():");
			log.info("-------------------------------");
			for (Employee empl : repository.findAll()) {
				log.info(empl.toString());
			}
			log.info("");

			// fetch an individual employee by ID
			Employee customer = repository.findOne(1L);
			log.info("Employee found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
			log.info("--------------------------------------------");
			for (Employee bauer : repository
					.findByLastNameStartsWithIgnoreCase("Bauer")) {
				log.info(bauer.toString());
			}
			log.info("");
		};
	}
	*/
}
