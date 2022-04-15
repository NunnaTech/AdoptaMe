package mx.com.adoptame.config.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
    @Value("${email-address}")
    private String emailAddress;
    @Value("${email-password}")
    private String emailPassword;

    @Bean
    public JavaMailSenderImpl getJavaMailSender(){
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(emailAddress);
        mailSender.setPassword(emailPassword);
        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        return mailSender;
    }
}
