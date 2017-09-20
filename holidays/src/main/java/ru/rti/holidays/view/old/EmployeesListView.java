package ru.rti.holidays.view.old;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rti.holidays.entity.Employee;
import ru.rti.holidays.repository.EmployeeRepository;
import ru.rti.holidays.view.old.BaseView;

public class EmployeesListView extends BaseView {
    private VerticalLayout layout;

    @Autowired
    EmployeeRepository repository;

    //EmployeeList employeeList = new EmployeeList(repository);
    Grid<Employee> gridEmployees = new Grid<>(Employee.class);

    @Override
    public Component getViewComponent() {
        HorizontalLayout layout = new HorizontalLayout();
        Button b1 = new Button("Test1", event -> {
            Notification.show("test1 clicked");
        });
        Button b2 = new Button("Test2", event -> {
            Notification.show("test2 clicked");
        });

        layout.addComponents(b1, b2);
        return layout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Добро пожаловать на экран сотрудников!");
    }

    public EmployeesListView(Navigator navigator) {
        super(navigator);
    }
/*    @Override
    protected void init(VaadinRequest request) {
         Set the default locale of the UI
        UI.getCurrent().setLocale(new Locale("ru"));

        setupLayout();
        addHeader();
        addForm();
        addEmployeeList();
        addLoginForm();
        addDeleteButton();
    }*/

/*    private void addLoginForm() {
        LoginFormLayout loginForm = new LoginFormLayout("Вход в Систему", "Имя пользователя", "Пароль");
        layout.addComponent(loginForm);
    }*/

    /**
     * Sets up the main Layout
     */
/*    private void setupLayout() {
        layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(layout);
    }*/

    /**
     * Adds the header to the UI
     */
/*    private void addHeader() {
        Label header = new Label("Задачи");
        header.addStyleName(ValoTheme.LABEL_H1);
        header.setSizeUndefined();
        layout.addComponent(header);
    }*/

    /**
     * Adds delete button
     */
/*    private void addDeleteButton() {
        Button deleteButton = new Button("Удаление завершено");

        //TODO: test button to show UI interaction, remove later
        final Button button = new Button("Нажми меня");
        button.addClickListener(event -> button.setCaption("Ты нажал меня!"));

        //Test date time control
        DateField date = new DateField();
        date.setValue(LocalDate.now());
        date.setDateFormat("dd-MM-yyyy");
        date.setPlaceholder("dd-MM-yyyy");

        layout.addComponent(deleteButton);
        layout.addComponent(button);
        layout.addComponent(date);

    }*/

    /**
     * Adds the list of employees to the UI
     */
/*    private void addEmployeeList() {
        //employeeList.setWidth("80%");
        //layout.addComponent(employeeList);
        gridEmployees.setWidth("100%");
        layout.addComponent(gridEmployees);

        List<Employee> employees = repository.findAll();
        gridEmployees.setItems(employees);
    }*/

    /**
     * Adds the form to the UI
     */
/*    private void addForm() {
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setSpacing(true);
        formLayout.setWidth("80%");

        TextField taskField = new TextField();
        taskField.setWidth("100%");
        Button addButton = new Button("Добавить");
        addButton.setIcon(FontAwesome.PLUS);
        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        formLayout.addComponents(taskField, addButton);
        formLayout.setExpandRatio(taskField, 1);

        layout.addComponent(formLayout);
    }*/
}
