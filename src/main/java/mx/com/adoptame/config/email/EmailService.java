package mx.com.adoptame.config.email;

import mx.com.adoptame.entities.request.Request;
import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;



    public void sendRecoverPasswordTemplate(String email, String link) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = helper(message);
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("link", link);
        Context context = new Context();
        context.setVariables(map);
        String html = templateEngine.process("email/recoveryPasswordEmail", context);
        helper.setTo(email);
        helper.setSubject("Pulgas de Adoptame: Restablecimiento de contraseña");
        helper.setText(html, true);
        javaMailSender.send(message);
    }
    public void sendRequestAceptedTemplate(User user, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = helper(message);
        Map<String, Object> map = new HashMap<>();
        String username =  user.getUsername();
        map.put("link",token);
        map.put("email",username);
        map.put("name", user.getProfile().getPartialName());

        Context context = new Context();
        context.setVariables(map);
        String html = templateEngine.process("email/voluntarioConfirm", context);
        helper.setTo(username);
        helper.setSubject("Pulgas de Adoptame: Restablecimiento de contraseña");
        helper.setText(html, true);
        javaMailSender.send(message);
    }
    private MimeMessageHelper helper (MimeMessage mimeMessage) throws MessagingException {
            return new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
    }

}
