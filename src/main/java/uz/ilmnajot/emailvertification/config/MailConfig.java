package uz.ilmnajot.emailvertification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("ilmnajot2021@gmail.com");
        mailSender.setPassword("llrlrhgadbccjrsw");


        Properties pros = mailSender.getJavaMailProperties();
        pros.setProperty("mail.transport.protocol", "smtp");
        pros.setProperty("mail.smtp.auth", "true");
        pros.setProperty("mail.smtp.starttls.enable", "true");
        pros.setProperty("mail.debug", "true");
        return mailSender;
    }
}
