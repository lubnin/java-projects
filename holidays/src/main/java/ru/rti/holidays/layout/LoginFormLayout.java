package ru.rti.holidays.layout;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


/**
 * LoginFormLayout is used to display the LoginForm for Holidays Application
 */
@SpringComponent
public class LoginFormLayout extends VerticalLayout {
    // The caption of the form
    private String formCaption;

    // The caption for 'Name' field
    private String nameFieldCaption;

    // The caption for 'Password' field
    private String passwordFieldCaption;

    private FormLayout formPanel = new FormLayout();
    private HorizontalLayout buttonsPanel = new HorizontalLayout();

    private Button.ClickListener btnRegisterClickListener = null;
    private Button.ClickListener btnLoginClickListener = null;

    private Button btnLogin;
    private Button btnRegister;

    /**
     * Parametrized constructor
     * @param formCaption the caption of the form
     * @param nameFieldCaption the caption of 'Name' label field
     * @param passwordFieldCaption the caption of 'Password' label field
     */
    public LoginFormLayout(String formCaption, String nameFieldCaption, String passwordFieldCaption) {
        setFormCaption(formCaption);
        setNameFieldCaption(nameFieldCaption);
        setPasswordFieldCaption(passwordFieldCaption);

        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setSpacing(true);

        addFormCaption();
        addForm();
        addButtons();
    }

    public void addRegisterButtonClickListener(Button.ClickListener registerButtonClickListener) {
        if (btnRegister == null) {
            throw new NullPointerException("Button 'btnRegister' is not instantiated yet.");
        }
        btnRegister.addClickListener(registerButtonClickListener);
    }
    public void addLoginButtonClickListener(Button.ClickListener loginButtonClickListener) {
        if (btnLogin == null) {
            throw new NullPointerException("Button 'btnLogin' is not instantiated yet.");
        }
        btnLogin.addClickListener(loginButtonClickListener);
    }
    private void addFormCaption() {
        Label lblFormName = new Label(formCaption);
        lblFormName.addStyleName(ValoTheme.LABEL_H1);
        addComponents(lblFormName);
    }

    private void addButtons() {
        btnLogin = new Button("Войти");
        btnLogin.setIcon(VaadinIcons.SIGN_IN);
        btnLogin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        //btnLogin.addClickListener(btnLoginClickListener);
        buttonsPanel.addComponent(btnLogin);

        btnRegister = new Button("Зарегистрироваться");
        btnRegister.setIcon(VaadinIcons.USER_STAR);
        btnRegister.addStyleName(ValoTheme.BUTTON_PRIMARY);
        //btnRegister.addClickListener(btnRegisterClickListener);
        buttonsPanel.addComponent(btnRegister);

        buttonsPanel.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        buttonsPanel.setSpacing(true);

        Link linkForgotPassword = new Link("Забыли пароль?", new ExternalResource("http://ya.ru"));
        linkForgotPassword.setIcon(VaadinIcons.KEY);
        addComponent(buttonsPanel);
        addComponent(linkForgotPassword);
    }
    private void addForm() {
        formPanel.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        formPanel.setSpacing(true);

        TextField txtName = new TextField(getNameFieldCaption());
        txtName.setIcon(VaadinIcons.USER);
        txtName.setRequiredIndicatorVisible(true);

        formPanel.addComponent(txtName);

        PasswordField txtPassword = new PasswordField(getPasswordFieldCaption());
        txtPassword.setIcon(VaadinIcons.PASSWORD);
        txtPassword.setRequiredIndicatorVisible(true);
        formPanel.addComponent(txtPassword);

        formPanel.setWidth("30%");
        formPanel.setHeight("30%");

        addComponent(formPanel);
        setComponentAlignment(formPanel, Alignment.MIDDLE_CENTER);
    }

    public void addButton(Button buttonBack) {
        addComponent(buttonBack);
    }
    /**
     * Default constructor
     */
    public LoginFormLayout() {
        this("Вход в Систему отпусков РТИ",
                "Имя пользователя",
                "Пароль");
    }

    public String getNameFieldCaption() {
        return this.nameFieldCaption;
    }

    public void setNameFieldCaption(String nameFieldCaption) {
        this.nameFieldCaption = nameFieldCaption;
    }

    public String getPasswordFieldCaption() {
        return this.passwordFieldCaption;
    }

    public void setPasswordFieldCaption(String passwordFieldCaption) {
        this.passwordFieldCaption = passwordFieldCaption;
    }

    public void setFormCaption(String formCaption) {
        this.formCaption = formCaption;
    }

    public String getFormCaption() {
        return this.formCaption;
    }
}
