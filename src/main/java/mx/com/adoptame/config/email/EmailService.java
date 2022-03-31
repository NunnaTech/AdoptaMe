package mx.com.adoptame.config.email;

import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendRecoverPasswordTemplate(String email, String link) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> map = new HashMap<String, Object>();
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


}
