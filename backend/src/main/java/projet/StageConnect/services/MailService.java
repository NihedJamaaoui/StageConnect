package projet.StageConnect.services;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
    import org.springframework.mail.javamail.MimeMessageHelper;
    import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.MessagingException;
    import javax.mail.internet.MimeMessage;

    @Service
    public class MailService {

        @Autowired
        private JavaMailSender javaMailSender;

        @Autowired
        private JavaMailSender mailSender;
        
        @Value("$(stageconnect@gmail.com)")
        private String fromMail;
        public void sendMail(String mail) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(fromMail);
            simpleMailMessage.setSubject("mail d'acceptation");
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime futureDate = currentDate.plusDays(7);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH");
            String formattedDate = futureDate.format(formatter);
            simpleMailMessage.setText("Nous avons le plaisir de vous informer que votre candidature pour un entretien a été retenue. Nous sommes impressionnés par votre parcours académique et vos compétences, et nous sommes convaincus que votre contribution sera précieuse au sein de notre équipe.\r\n"
            		+ "\r\n"
            		+ "Nous vous convions à un entretien qui se déroulera le "+ formattedDate +"h dans nos locaux situés à notre adresse. Cet entretien sera l'occasion pour nous de discuter plus en détail de votre profil, de vos attentes et de la manière dont vous pourriez vous intégrer au sein de notre entreprise.");
            simpleMailMessage.setTo(mail);

            mailSender.send(simpleMailMessage);
        }



        private boolean isValidEmailAddress(String email) {
            return email != null && !email.isEmpty();
        }
    }