package org.com.epam.training.Artem_Kosenko.webdriver.Task3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class YopmailHomePage extends AbstractPage{

    private static final String HOMEPAGE_URL = "https://yopmail.com/";

    private final By generateEmailButtonLocator = By.xpath("//div[@id='listeliens']/a[@href='email-generator']");

    public YopmailHomePage(WebDriver driver) {
        super(driver);
    }

    public YopmailHomePage openPage() {
        driver.get(HOMEPAGE_URL);
        acceptCoookiesYopmail();
        return this;
    }

    public YopmailEmailGeneratorPage generateEmailAddress() {
        waitPresenceOfElementLocated(generateEmailButtonLocator)
                .click();
        return new YopmailEmailGeneratorPage(driver);
    }
}