package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.CustomProperties;
import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.cache.LazyCache;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;
import com.misernandfriends.cinemaclub.pojo.BugReport;
import com.misernandfriends.cinemaclub.serviceInterface.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import com.misernandfriends.cinemaclub.serviceInterface.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    private CustomProperties customProperties;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private UserService userService;

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

            String bodyText = LazyCache.getValue(CacheValue._EMAIL_CONFIGURATION.ACTIVATE_MESSAGE) + verificationLink;
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(customProperties.getMailOptions().getEmail()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject(LazyCache.getValue(CacheValue._EMAIL_CONFIGURATION.ACTIVATE_TITLE));
            message.setContent(bodyText, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sendChangePasswordEmail(UserDTO user) {
        try {
            if (!customProperties.getMailOptions().getEnable()) {
                return;
            }
            VerificationTokenDTO verfToken = verificationTokenService.generatePasswordResetToken(user);

            String verificationLink = customProperties.getPasswordTokenLink() +
                    "?token=" + verfToken.getToken() + "&username=" + user.getUsername();

            String bodyText = LazyCache.getValue(CacheValue._EMAIL_CONFIGURATION.PWD_RESET_MESSAGE) + verificationLink;
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(customProperties.getMailOptions().getEmail()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject(LazyCache.getValue(CacheValue._EMAIL_CONFIGURATION.PWD_RESET_TITLE));
            message.setContent(bodyText, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendBugReport(BugReport bugReport) {
        try {
            if (!customProperties.getMailOptions().getEnable()) {
                return;
            }

            List<String> emailList = userService.getAllAdminEmails();
            String bodyText = "Bug description:" + bugReport.getBugDescription() + "Reported by " + bugReport.getReporterUsername() + " on " + bugReport.getReportDate();
            System.out.println("BugReport:" + bodyText);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(customProperties.getMailOptions().getEmail()));
            message.setSubject("Bug report ");
            message.setContent(bodyText, "text/html; charset=utf-8");
            for (String email : emailList) {
                message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(email));
                System.out.println("recipent:" + email);
            }
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
