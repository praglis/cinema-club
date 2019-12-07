package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.CustomProperties;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;
import com.misernandfriends.cinemaclub.serviceInterface.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.VerificationTokenService;
import com.sun.net.ssl.internal.ssl.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    private CustomProperties customProperties;

    @Autowired
    private VerificationTokenService verificationTokenService;

    private Session session;

    @Autowired
    MailServiceImpl(CustomProperties customProperties) {
        this.customProperties = customProperties;

        initMailSession(customProperties);
    }

    private void initMailSession(CustomProperties customProperties) {
        final CustomProperties.MailOptions mailOptions = customProperties.getMailOptions();
        if (!mailOptions.getEnable()) {
            return;
        }

        Security.addProvider(new Provider());
        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailOptions.getHost());
        props.put("mail.smtp.port", mailOptions.getHostPort());
        props.put("mail.smtp.socketFactory.port", mailOptions.getSocketFactoryPort());
        props.put("mail.smtp.socketFactory.class", mailOptions.getSocketFactoryClass());

        this.session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailOptions.getEmail(), mailOptions.getPassword());
                    }
                });
    }

    @Override
    public void sendConfirmationEmail(UserDTO user) {
        try {
            if (!customProperties.getMailOptions().getEnable()) {
                return;
            }
            VerificationTokenDTO verfToken = verificationTokenService.generateRegistrationToken(user);

            String verificationLink = customProperties.getVerificationTokenLink() +
                    "?token=" + verfToken.getToken() + "&username=" + user.getUsername();

            String bodyText = "Welcome in Cinema Club!<br/>Please active your account by clicking the following link:<br/>" + verificationLink;
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(customProperties.getMailOptions().getEmail()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Confirm your email");
            message.setContent(bodyText, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
