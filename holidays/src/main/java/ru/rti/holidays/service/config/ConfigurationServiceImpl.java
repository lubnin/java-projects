package ru.rti.holidays.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Configuration
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class ConfigurationServiceImpl implements ConfigurationService {
    @Autowired
    Environment env;

    @Override
    public String getPropertyValue(String property) {
        return env.getProperty(property);
    }

    @Override
    public String getServerPort() {
        return getPropertyValue("server.port");
    }

    @Override
    public String getServerAddress() {
        return getPropertyValue("server.address");
    }

    @Override
    public String getApplicationRunningMode() {
        return getPropertyValue("application.running.mode");
    }

    @Override
    public String getStringMessagesEncoding() {
        return getPropertyValue("spring.messages.encoding");
    }

    @Override
    public String getSpringMailDefaultEncoding() {
        return getPropertyValue("spring.mail.default-encoding");
    }

    @Override
    public String getSpringMailHost() {
        return getPropertyValue("spring.mail.host");
    }

    @Override
    public String getSpringMailPassword() {
        return getPropertyValue("spring.mail.password");
    }

    @Override
    public String getSpringMailPort() {
        return getPropertyValue("spring.mail.port");
    }

    @Override
    public String getSpringMailProtocol() {
        return getPropertyValue("spring.mail.protocol");
    }

    @Override
    public String getSpringMailTestConnection() {
        return getPropertyValue("spring.mail.test-connection");
    }

    @Override
    public String getSpringMailUsername() {
        return getPropertyValue("spring.mail.username");
    }

    @Override
    public String getApplicationEmailMessagesSendingEnabled() {
        return getPropertyValue("application.email.messages.sending.enabled");
    }
}
