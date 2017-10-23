package ru.rti.holidays.service.localization;

public interface LocalizationService {
    String getPropertyValue(String property);
    String getMessageAdminUserMenuSettings();
    String getMessageAdminUserMenuExit();
    String getMessageControlsCommonButtonSaveSettings();

    /*
    Common Validation Error Messages
     */
    String getMessageCommonValidationErrorPasswordFieldsAreRequired();
    String getMessageCommonValidationErrorPasswordPasswordsMustBeEqual();

    /*
     EmployeeSettingsLayout messages
     */
    String getMessageEmployeeSettingsLayoutPageTitle();
    String getMessageEmployeeSettingsLayoutPasswordFieldNew();
    String getMessageEmployeeSettingsLayoutPasswordFieldNewRequired();
    String getMessageEmployeeSettingsLayoutPasswordFieldRepeat();
}
