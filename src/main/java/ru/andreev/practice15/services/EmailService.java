package ru.andreev.practice15.services;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailService {

    private Properties props;

    private String from = "klch-o@yandex.ru";
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    EmailService(){
        props = new Properties();
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    @Async
    public void send( String to, String subject, String text){
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            //от кого
            message.setFrom(new InternetAddress(from));
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            //тема сообщения
            message.setSubject(subject);
            //текст
            message.setText(text);

            //отправляем сообщение
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
