package ru.rti.holidays.layout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.repository.EmployeeRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
public class EmployeeListLayout extends VerticalLayout {

    /*private static final Logger log = LoggerFactory.getLogger(EmployeeListLayout.class);

    @Autowired
    private final EmployeeRepository repository;

    private Grid<Employee> grid;


    public EmployeeListLayout(EmployeeRepository repository) {
        log.info("I'm in EmployeeList constructor now");
        this.repository = repository;

        if (repository != null) {
            //repository.save(new Employee("Jack", "Bauer"));
            //repository.save(new Employee("Chloe", "O'Brian"));
            //repository.save(new Employee("Kim", "Bauer"));
            //repository.save(new Employee("David", "Palmer"));
            //repository.save(new Employee("Michelle", "Dessler"));
        } else {
            log.warn("Warning! repository object is null!");
        }
    }
    @PostConstruct
    void init() {
        log.info("I'm in EmployeeList init() method now.");
        this.grid = new Grid<>(Employee.class);
        setSpacing(true);
        if (repository != null) {
            setEmployees(repository.findAll());
        } else {
            log.warn("Warning! repository object is null!");
        }
    }

    private void setEmployees(List<Employee> employees) {
        removeAllComponents();
        grid.setItems(employees);
        addComponent(grid);*/
/*
        employees.forEach(todo -> {
            addComponent(new TodoLayout(todo));
        });
*/
    //}
}
