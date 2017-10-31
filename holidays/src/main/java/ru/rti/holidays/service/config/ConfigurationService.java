package ru.rti.holidays.service.config;

public interface ConfigurationService {
    String getPropertyValue(String property);
    String getServerPort();
    String getServerAddress();
    String getApplicationRunningMode();
    String getStringMessagesEncoding();
    String getSpringMailDefaultEncoding();
    String getSpringMailHost();
    String getSpringMailPassword();
    String getSpringMailPort();
    String getSpringMailProtocol();
    String getSpringMailTestConnection();
    String getSpringMailUsername();
}
