package com.famlynk.util;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.famlynk.model.Register;
import com.famlynk.repository.IRegisterRepository;

/**
 * 
 * @author Josephine
 * @Date 18-May-2023
 *
 */

@Component

public class EmailSender {
    @Autowired
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public IRegisterRepository regRepo;

    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String name, String otpString) {
        Thread thread = new Thread(() -> {
            String text = "Dear " + name + ",\n";
            text += "OTP :" + otpString + "\n\n";
            text += "Thank you\nThe Famlynk Team";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(to);
            message.setSubject("Please verify your registration");
            message.setText(text);
            mailSender.send(message);
        });
        thread.start();
    }

    public String createOtpString() {
        Random randomotp = new Random();
        int otp = 100000 + randomotp.nextInt(900000);
        return Integer.toString(otp);
    }

    public void expireOTP(Register register) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println("timer handled");
                Register reg = regRepo.findById(register.getId()).get();
                reg.setOtp(null);
                regRepo.save(reg);
                System.out.println("null value saved on otp");
            }
        }, 120000);
    }

    /**
     * sending email to FamilyMembers
     * 
     * @param to
     * @param userName
     * @param name
     * @param relation
     * @return
     */
    public void sendEmailForAddFamily(String to, String userName, String name, String relation) {
        Thread thread = new Thread(() -> {
            String text = "Dear " + name + ",\n";
            text += userName + " " + "added you as a" + " " + relation + "\n\n";
            text +="http://famlynk.com"+"\n\n";
            text += "Thank you\nThe Famlynk Team";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(to);
            message.setSubject("Family member invitation from FAMLYNK");
            message.setText(text);
            mailSender.send(message);
        });
        thread.start();

    }

    public String verifyOtp(String otp) {
        Register register = regRepo.findByOtp(otp);

        if (register != null && otp != null) {
            register.setEnabled(true);
            regRepo.save(register);
            return "OTP is correct";
        } else {
            return "OTP is invalid";
        }

    }
}

