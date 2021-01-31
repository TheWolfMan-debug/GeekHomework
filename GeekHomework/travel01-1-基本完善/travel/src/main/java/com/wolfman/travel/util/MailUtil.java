package com.wolfman.travel.util;

import com.wolfman.travel.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@Component
public class MailUtil {


    //邮件发送者
    public static final String SENDER = "2370032534@qq.com" ;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public void sendMail(String accepter,String content ) throws MessagingException {
        //创建消息
        MimeMessage message = javaMailSender.createMimeMessage();
        //开启对内联元素和附件的支持//multipart=true
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //设置主题
        helper.setSubject("黑马旅游网激活邮件");
        //设置content type=text/html，默认为text/plain//html=ture
        helper.setText(content, true);
        //发送者
        helper.setFrom(SENDER);
        //接收者
        helper.setTo(accepter);
        //设置附件
        //helper.addAttachment("2.png", new File("C:\\Users\\wolfMan\\Pictures\\Saved Pictures\\01.png"));
        //发送邮件
        javaMailSender.send(message);
    }

}
