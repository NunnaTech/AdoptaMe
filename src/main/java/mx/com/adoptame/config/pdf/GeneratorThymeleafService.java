package mx.com.adoptame.config.pdf;

import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class GeneratorThymeleafService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ServletContext servletContext;

    private Logger logger = LoggerFactory.getLogger(GeneratorThymeleafService.class);

    private String urlBase = "http://localhost:8090";

    public ByteArrayOutputStream createPdf(String templateName, Map userPayload, HttpServletRequest request, HttpServletResponse response)
            throws DocumentException {
        IWebContext ctx = new WebContext(request, response, servletContext, LocaleContextHolder.getLocale(), userPayload);
        String processedHtml = templateEngine.process(templateName, ctx);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml, urlBase);
            renderer.layout();
            renderer.createPDF(bos, false);
            renderer.finishPDF();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    logger.error("Error creando pdf", e);
                }
            }
        }
        return bos;
    }
}
