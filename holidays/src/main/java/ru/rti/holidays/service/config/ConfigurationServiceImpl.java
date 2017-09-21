package ru.rti.holidays.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    @Override
    public String getMessageAdminUserMenuSettings() {
        return null;
    }

    //@Value("${messages.admin.usermenu.settings}")
    //private String messageAdminUserMenuSettings;

    //public String getMessageAdminUserMenuSettings() {
    //    return messageAdminUserMenuSettings;
    //}
}
