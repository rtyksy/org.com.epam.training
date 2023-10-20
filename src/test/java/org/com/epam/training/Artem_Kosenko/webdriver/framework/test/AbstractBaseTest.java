package org.com.epam.training.Artem_Kosenko.webdriver.framework.test;

import org.com.epam.training.Artem_Kosenko.webdriver.framework.driver.DriverSingletone;
import org.com.epam.training.Artem_Kosenko.webdriver.framework.models.CalculatedForm;
import org.com.epam.training.Artem_Kosenko.webdriver.framework.models.EmailEstimateForm;
import org.com.epam.training.Artem_Kosenko.webdriver.framework.models.GoogleCloudMainForm;
import org.com.epam.training.Artem_Kosenko.webdriver.framework.service.GoogleCloudMainFormCompleting;
import org.com.epam.training.Artem_Kosenko.webdriver.framework.util.TestListener;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Log4j2
@Listeners({TestListener.class})
public abstract class AbstractBaseTest {

    protected WebDriver driver;
    protected CalculatedForm calculatedForm;
    protected EmailEstimateForm emailEstimateForm;
    protected GoogleCloudMainForm googleCloudMainForm = GoogleCloudMainFormCompleting.completeForm();
    protected String searchTerm = "Google Cloud Pricing Calculator";

    @BeforeMethod()
    public void browserSetup() {
        driver = DriverSingletone.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown() {
        log.info("Closing browser");
        DriverSingletone.closeDriver();
    }
}