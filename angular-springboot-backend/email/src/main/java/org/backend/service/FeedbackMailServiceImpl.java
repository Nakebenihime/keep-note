package org.backend.service;

import org.backend.configuration.EmailConfiguration;
import org.backend.model.FeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class FeedbackMailServiceImpl implements FeedBack {

    private final EmailConfiguration configuration;

    @Value("${mailtrap.inbox}")
    private String INBOX;

    @Autowired
    public FeedbackMailServiceImpl(EmailConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void sendFeedback(String from, String name, String feedback) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(INBOX);
        message.setSubject("New feedback from " + name);
        message.setText(feedback);
        message.setFrom(from);

        this.configuration.getJavaMailSender().send(message);
    }

}
