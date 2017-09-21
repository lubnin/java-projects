package ru.rti.holidays.service.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Configuration
@PropertySource("classpath:messages.properties")
public class LocalizationServiceImpl implements LocalizationService {
    @Autowired
    Environment env;

    @Override
    public String getMessageAdminUserMenuSettings() {
        return env.getProperty("messages.admin.usermenu.settings");
    }

    @Override
    public String getMessageAdminUserMenuExit() {
        return env.getProperty("messages.admin.usermenu.exit");
    }
}
