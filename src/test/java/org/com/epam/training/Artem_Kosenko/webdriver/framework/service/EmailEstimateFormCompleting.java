package org.com.epam.training.Artem_Kosenko.webdriver.framework.service;



import org.com.epam.training.Artem_Kosenko.webdriver.framework.models.EmailEstimateForm;

public class EmailEstimateFormCompleting {

    public static EmailEstimateForm completeEmailEstimateForm (String emailAddress) {
        EmailEstimateForm form = EmailEstimateForm.create()
                .withEmailAddress(emailAddress)
                .build();
        return form;
    }
}