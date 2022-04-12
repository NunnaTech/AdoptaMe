package mx.com.adoptame.entities.donation;

import com.lowagie.text.DocumentException;
import mx.com.adoptame.config.pdf.GeneratorThymeleafService;
import mx.com.adoptame.entities.user.User;
import mx.com.adoptame.entities.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

@Controller
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    @Autowired
    private GeneratorThymeleafService generatorThymeleafService;

    private Logger logger = LoggerFactory.getLogger(DonationController.class);

    @GetMapping("/admin")
    @Secured("ROLE_ADMINISTRATOR")
    public String donation(Model model) {
        model.addAttribute("list", donationService.findAll());
        return "views/donations";
    }

    @GetMapping("/")
    public String owns(Model model, Authentication authentication) {
        try {
            String username = authentication.getName();
            Optional<User> user = userService.findByEmail(username);
            if (user.isPresent()) {
                model.addAttribute("list", user.get().getDonations());
            } else {
                model.addAttribute("list", new ArrayList<>());
            }
        } catch (Exception e) {
            model.addAttribute("list", new ArrayList<>());
            logger.error(e.getMessage());
        }
        return "views/donations";
    }

    @GetMapping(value = { "/payment", "/payment/{id}" })
    public ResponseEntity<ByteArrayResource> generatePayment(@PathVariable(required = false, value = "id") Integer id,
            final HttpServletRequest request,
            final HttpServletResponse response, RedirectAttributes redirectAttributes,
            Authentication authentication) throws DocumentException, IllegalAccessException {
        String fileName = "";
        ByteArrayOutputStream byteArrayOutputStreamPDF = new ByteArrayOutputStream();
        Map<String, Object> userPayload = new HashMap<>();
        String username = authentication.getName();
        Optional<User> user = userService.findByEmail(username);
        if (id != null && user.isPresent()) {
            Optional<Donation> donation = donationService.findOne(id);
            user.get().setPassword(null);
            if (donation.isPresent()) {
                user.get().getDonations().clear();
                user.get().setDonations(Arrays.asList(donation.get()));
            }
        }
        if (user.isPresent()) {
            Field[] allFields = user.get().getClass().getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                Object value = field.get(user.get());
                userPayload.put(field.getName(), value);
            }
            fileName = "recibo_pago_de_" + user.get().getProfile().getPartialName() + ".pdf";
            byteArrayOutputStreamPDF = generatorThymeleafService.createPdf(
                    "/components/payment.html",
                    userPayload, request, response);
        }
        ByteArrayResource inputStreamResourcePDF = new ByteArrayResource(byteArrayOutputStreamPDF.toByteArray());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(inputStreamResourcePDF.contentLength()).body(inputStreamResourcePDF);
    }
}
