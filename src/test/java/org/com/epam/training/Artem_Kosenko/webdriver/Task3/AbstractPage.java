package org.com.epam.training.Artem_Kosenko.webdriver.Task3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class AbstractPage {

    protected WebDriver driver;
    protected final int WAIT_TIMEOUT_SECONDS = 10;
    protected By acceptCoookies = By.xpath("//button[@class='pvUife']");
    protected By acceptCoookiesYopmail = By.xpath("//button[@id='accept']");
    protected AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected WebDriverWait getNewWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
    }
    protected void acceptCookies() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(acceptCoookies))
                .click();
    }
    protected void acceptCoookiesYopmail () {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(acceptCoookiesYopmail))
                .click();
    }
    protected AbstractPage switchToFrame (By frame) {
        driver.switchTo().frame(waitPresenceOfElementLocated(frame));
        return this;
    }

    protected WebElement waitPresenceOfElementLocated(By locator) {
        return getNewWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected List<WebElement> waitPresenceOfAllElementsLocatedBy(By locator) {
        return getNewWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected void waitInvisibilityOf(WebElement element) {
        getNewWait().until(ExpectedConditions.invisibilityOf(element));
    }

    protected WebElement waitElementToBeClickable(WebElement element) {
        return getNewWait()
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void selectDropdownOption(String dropdownOptionsXpath, String option) {
        WebElement dropdownOption =
                waitPresenceOfElementLocated(By.xpath(String.format(dropdownOptionsXpath, option)));
        dropdownOption.click();
        waitInvisibilityOf(dropdownOption);
    }
}