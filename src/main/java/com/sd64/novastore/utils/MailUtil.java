package com.sd64.novastore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

    @Autowired
    private JavaMailSender emailSender;

    @Value("{$spring.mail.username}")
    private String fromMail;

    public String confirmMailTemplate(String recipientName, String confirmationCode, String companyName, String contactInfo) {
        String emailTemplate =
                "Kính gửi " + recipientName + ",\n\n" +
                        "Chúng tôi xin gửi bạn mã xác nhận để hoàn tất quá trình xác minh tài khoản của bạn. Mã này sẽ giúp bảo" +
                        " mật tài khoản của bạn và đảm bảo rằng chỉ có bạn mới có quyền truy cập.\n\n" +
                        "Mã xác nhận của bạn là: " + confirmationCode + "\n\n" +
                        "Vui lòng sử dụng mã này trong 10 phút để hoàn tất quá trình xác thực hoặc đăng nhập vào tài khoản của bạn.\n\n" +
                        "Nếu bạn không yêu cầu mã này, vui lòng liên hệ với chúng tôi ngay lập tức để chúng tôi có thể hỗ trợ bạn.\n\n" +
                        "Xin cảm ơn.\n\n" +
                        "Trân trọng,\n" +
                        companyName + "\n" +
                        contactInfo;
        return emailTemplate;
    }

    public String resetPassMailTemplate(String recipientName, String confirmationCode, String companyName, String contactInfo) {
        String emailTemplate =
                "Kính gửi " + recipientName + ",\n\n" +
                        "Chúng tôi xin gửi bạn mã xác nhận để hoàn tất quá trình đặt lại mật khẩu cho tài khoản của bạn. Mã này sẽ giúp bảo" +
                        " mật tài khoản của bạn và đảm bảo rằng chỉ có bạn mới có quyền truy cập.\n\n" +
                        "Mã xác nhận của bạn là: " + confirmationCode + "\n\n" +
                        "Vui lòng sử dụng mã này trong 10 phút để hoàn tất quá trình đặt lại mật khẩu cho tài khoản của bạn.\n\n" +
                        "Nếu bạn không yêu cầu mã này, vui lòng liên hệ với chúng tôi ngay lập tức để chúng tôi có thể hỗ trợ bạn.\n\n" +
                        "Xin cảm ơn.\n\n" +
                        "Trân trọng,\n" +
                        companyName + "\n" +
                        contactInfo;
        return emailTemplate;
    }

    public String changePassMailTemplate(String recipientName, String confirmationCode, String companyName, String contactInfo) {
        String emailTemplate =
                "Kính gửi " + recipientName + ",\n\n" +
                        "Chúng tôi xin gửi bạn mã xác nhận để hoàn tất quá trình thay đổi mật khẩu cho tài khoản của bạn. Mã này sẽ giúp bảo" +
                        " mật tài khoản của bạn và đảm bảo rằng chỉ có bạn mới có quyền truy cập.\n\n" +
                        "Mã xác nhận của bạn là: " + confirmationCode + "\n\n" +
                        "Vui lòng sử dụng mã này trong 10 phút để hoàn tất quá trình đổi mật khẩu cho tài khoản của bạn.\n\n" +
                        "Nếu bạn không yêu cầu mã này, vui lòng liên hệ với chúng tôi ngay lập tức để chúng tôi có thể hỗ trợ bạn.\n\n" +
                        "Xin cảm ơn.\n\n" +
                        "Trân trọng,\n" +
                        companyName + "\n" +
                        contactInfo;
        return emailTemplate;
    }

    public void sendEmail(
            String to, String subject, String body) {
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromMail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            emailSender.send(message);
        }).start();
    }

}
