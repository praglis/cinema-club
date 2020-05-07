package com.misernandfriends.cinemaclub.service.config;

import com.misernandfriends.cinemaclub.config.CustomProperties;
import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.cache.LazyCache;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;
import com.misernandfriends.cinemaclub.pojo.config.BugReport;
import com.misernandfriends.cinemaclub.pojo.user.UserReport;
import com.misernandfriends.cinemaclub.serviceInterface.config.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.user.UserService;
import com.misernandfriends.cinemaclub.serviceInterface.user.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    private final CustomProperties customProperties;
    private final VerificationTokenService verificationTokenService;
    private final UserService userService;

    private Session session;

    @Autowired
    MailServiceImpl(CustomProperties customProperties, UserService userService, @Lazy VerificationTokenService verificationTokenService) {
        this.customProperties = customProperties;
        this.userService = userService;
        initMailSession(customProperties);
        this.verificationTokenService = verificationTokenService;
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
            VerificationTokenDTO verificationToken = verificationTokenService.generateRegistrationToken(user);

            String verificationLink = customProperties.getVerificationTokenLink() +
                    "?token=" + verificationToken.getToken() + "&username=" + user.getUsername();

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
            VerificationTokenDTO verificationToken = verificationTokenService.generatePasswordResetToken(user);

            String verificationLink = customProperties.getPasswordTokenLink() +
                    "?token=" + verificationToken.getToken() + "&username=" + user.getUsername();

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
            String bodyText = "Bug description: <<" + bugReport.getBugDescription() + ">>\nReported by " + bugReport.getReporter() + " on " + bugReport.getReportDate();
            log.info("BugReport:" + bodyText);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(customProperties.getMailOptions().getEmail()));
            message.setSubject("Bug report ");
            message.setContent(bodyText, "text/html; charset=utf-8");
            for (String email : emailList) {
                message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(email));
                log.info("recipient:" + email);
            }
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendUserReport(UserReport userReport) {
        try {
            if (!customProperties.getMailOptions().getEnable()) {
                return;
            }

            List<String> emailList = userService.getAllAdminEmails();

            String bodyText = "Reported comment: <<" + userReport.getReportedComment() + ">>\n" +
                    "Comment author: " + userReport.getReportedUsername() + "\n" +
                    "Report reason: <<" + userReport.getReportReason() + ">>\n" +
                    "Reported by " + userReport.getReportingUsername() + " on " + userReport.getReportDate();
            log.info("UserReport:\n" + bodyText);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(customProperties.getMailOptions().getEmail()));
            message.setSubject("User report");
            message.setContent(bodyText, "text/html; charset=utf-8");
            for (String email : emailList) {
                message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(email));
                log.info("recipient:" + email);
            }
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
