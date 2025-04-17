package Util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailUtil {

    private JavaMailSenderImpl mailSender;

    public EmailUtil() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // 或你自己的 SMTP 主機
        mailSender.setPort(587);
        mailSender.setUsername("gajimala1108@gmail.com");
        mailSender.setPassword("kdaahqulbulxjryf");

        // 額外設定
        java.util.Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
    }

    public void sendVerificationCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gajimala1108@gmail.com");
        message.setTo(toEmail);
        message.setSubject("註冊驗證碼");
        message.setText("您好，您的驗證碼是：" + code);

        mailSender.send(message);
    }

    public void sendResetPwd(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gajimala1108@gmail.com");
        message.setTo(toEmail);
        message.setSubject("重設密碼");
        message.setText("您好，您的新密碼是：" + code);

        mailSender.send(message);
    }
}
