package mx.com.adoptame.config.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Bean
    public JavaMailSenderImpl getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // server data

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("adoptamenotification@gmail.com");
        mailSender.setPassword("iABu*sOT47bM4ZJd58fg");

        // server and connection properties
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        //props.put("mail.debug", "true");
        return mailSender;
    }
}
