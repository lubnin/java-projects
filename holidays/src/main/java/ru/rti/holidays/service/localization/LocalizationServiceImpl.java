package ru.rti.holidays.service.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Configuration
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@SuppressWarnings("unused")
public class LocalizationServiceImpl implements LocalizationService {
    @Autowired
    Environment env;

    @Override
    public String getPropertyValue(String property) {
        return env.getProperty(property);
    }

    @Override
    public String getMessageAdminUserMenuSettings() {
        return getPropertyValue("messages.admin.usermenu.settings");
    }

    @Override
    public String getMessageAdminUserMenuExit() {
        return getPropertyValue("messages.admin.usermenu.exit");
    }

    @Override
    public String getMessageControlsCommonButtonSaveSettings() {
        return getPropertyValue("messages.controls.common.buttonsavesettings");
    }

    public String getMessageCommonValidationErrorPasswordFieldsAreRequired() {
        return getPropertyValue("messages.common.validation.error.password.fields.are.required");
    }

    public String getMessageCommonValidationErrorPasswordPasswordsMustBeEqual() {
        return getPropertyValue("messages.common.validation.error.password.passwords.must.be.equal");
    }

    @Override
    public String getMessageEmployeeSettingsLayoutPageTitle() {
        return getPropertyValue("messages.employeesettingslayout.pagetitle");
    }

    @Override
    public String getMessageEmployeeSettingsLayoutPasswordFieldNew() {
        return getPropertyValue("messages.employeesettingslayout.passwordfield.new");
    }

    @Override
    public String getMessageEmployeeSettingsLayoutPasswordFieldNewRequired() {
        return getPropertyValue("messages.employeesettingslayout.passwordfield.new.required");
    }

    @Override
    public String getMessageEmployeeSettingsLayoutPasswordFieldRepeat() {
        return getPropertyValue("messages.employeesettingslayout.passwordfield.repeat");
    }
}
