package fall24.swp391.KoiOrderingSystem.component;

import fall24.swp391.KoiOrderingSystem.model.EmailDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class Email {
    //convert giao dien
    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    JavaMailSender javaMailSender;
    public void sendEmail(EmailDetail emailDetail) {
       try {
           Context context = new Context();
            context.setVariable("name",emailDetail.getReceiver().getEmail());
           context.setVariable("button","Go to homepage");
           context.setVariable("link",emailDetail.getLink());
           String template = templateEngine.process("welcome-template",context);
            // creating a simple mail message
           MimeMessage mimeMessage = javaMailSender.createMimeMessage();
           MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            //setting up necessary details
           mimeMessageHelper.setFrom("minhvqse181556@fpt.edu.vn");
           mimeMessageHelper.setTo(emailDetail.getReceiver().getEmail());
           mimeMessageHelper.setText(template,true);
           mimeMessageHelper.setSubject(emailDetail.getSubject());
            //send mail
           javaMailSender.send(mimeMessage);

       } catch (MessagingException e){
           System.out.println("ERROR SENT MAIL!!!");
       }
    }
}
