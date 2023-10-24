package org.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.backend.service.FeedbackMailServiceImpl;
import org.backend.viewmodel.FeedbackViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@Slf4j
@RestController
@RequestMapping(FeedbackController.PATH)
public class FeedbackController {

    public static final String PATH = "/api/v1/feedback";

    private final FeedbackMailServiceImpl feedbackMailService;

    @Autowired
    public FeedbackController(FeedbackMailServiceImpl feedbackMailService) {
        this.feedbackMailService = feedbackMailService;
    }

    @PostMapping
    public void sendFeedbackMailSender(@RequestBody FeedbackViewModel feedbackViewModel, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            log.error("unable to send feedback{} errors were found.", feedbackViewModel);
            throw new ValidationException("Feedback has errors, cannot send feedback");
        }
        log.info("sending feedback : {}", feedbackViewModel);
        this.feedbackMailService.sendFeedback(
                feedbackViewModel.getEmail(),
                feedbackViewModel.getName(),
                feedbackViewModel.getFeedback());
    }
}
