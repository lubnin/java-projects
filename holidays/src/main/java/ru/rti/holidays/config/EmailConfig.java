package ru.rti.holidays.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ru.rti.holidays.service.config.ConfigurationService;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Autowired
    private ConfigurationService configurationServiceImpl;

    @Bean
    public JavaMailSender getJavaMailSender() {

        JavaMailSender mailSender = new JavaMailSenderImpl();

        JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl)mailSender;

        mailSenderImpl.setHost(configurationServiceImpl.getSpringMailHost());
        mailSenderImpl.setPort(Integer.parseInt(configurationServiceImpl.getSpringMailPort()));
        mailSenderImpl.setUsername(configurationServiceImpl.getSpringMailUsername());
        mailSenderImpl.setPassword(configurationServiceImpl.getSpringMailPassword());

        Properties props = mailSenderImpl.getJavaMailProperties();
        props.put("mail.transport.protocol", configurationServiceImpl.getSpringMailProtocol());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        // Yandex special:
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.checkserveridentity", "false");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return mailSender;
    }

}
