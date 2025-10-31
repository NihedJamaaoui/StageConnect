package projet.StageConnect.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.StageConnect.entites.Postuler;
import projet.StageConnect.services.MailService;
import projet.StageConnect.services.PostulerService;


@RestController
@RequestMapping("/email")
public class EmailController {

    private final MailService mailService;
    private final PostulerService postulerService;

    @Autowired
    public EmailController(MailService mailService, PostulerService postulerService) {
        this.mailService = mailService;
        this.postulerService = postulerService;
    }


    /*
    @PostMapping("/send/{email}")
    public String sendMail(@PathVariable String email) {
        mailService.sendMail(email);
        return "Successfully sent the mail";
    }

    */
 
    @PostMapping("/send/{email}")
    public String sendMail(@PathVariable String email) {
        Postuler postuler = postulerService.getPostulerByEmail(email);

        if (postuler != null) {

            postuler.setDecision(true);


            postulerService.save(postuler);

            mailService.sendMail(email);
            return "Successfully sent the mail with a positive decision";
        } else {
            return "Postuler not found for email: " + email;
        }
    }

    }