package org.com.epam.training.Artem_Kosenko.webdriver.framework.test;

import org.com.epam.training.Artem_Kosenko.webdriver.framework.pages.GoogleCloudHomePage;
import org.com.epam.training.Artem_Kosenko.webdriver.framework.pages.GoogleCloudPricingCalculatorPage;
import org.com.epam.training.Artem_Kosenko.webdriver.framework.pages.YopmailEmailGeneratorPage;
import org.com.epam.training.Artem_Kosenko.webdriver.framework.pages.YopmailHomePage;
import org.com.epam.training.Artem_Kosenko.webdriver.framework.service.CreateCalculatedForm;
import org.com.epam.training.Artem_Kosenko.webdriver.framework.service.EmailEstimateFormCompleting;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class EmailTotalCostTest extends AbstractBaseTest {

    @Test(description = "Check equals between total price from site and from email")
    public void checkTotalPricePerMonthTest() {

        Pattern pricePattern = Pattern.compile("USD (\\d{1,3}(,\\d{3})*(\\.\\d{2}))");
        String googleCloudCalculatorWindow;
        String YopmailPageWindow;
        String totalMonthlyPriceFromEmail;

        GoogleCloudPricingCalculatorPage actualPricingResults = new GoogleCloudHomePage(driver)
                .openPage()
                .searchForTerms(searchTerm)
                .openGoogleCloudPricingCalculatorPage()
                .goToTheMainForm()
                .selectProduct(googleCloudMainForm.getProductName())
                .setNumberOfInstances(googleCloudMainForm.getNumberOfInstances())
                .selectOperatingSystem(googleCloudMainForm.getOperationSystem())
                .selectVMClass(googleCloudMainForm.getVMClass())
                .selectInstanceSeries(googleCloudMainForm.getInstanceSeries())
                .selectInstanceType(googleCloudMainForm.getInstanceType())
                .selectAddGPUsCheckBox()
                .selectGPUType(googleCloudMainForm.getGPUType())
                .selectNumberOfGPUs(googleCloudMainForm.getNumberOfGPUs())
                .selectLocalSSD(googleCloudMainForm.getLocalSSD())
                .selectDatacenterLocation(googleCloudMainForm.getDatacenterLocation())
                .selectCommittedUsageTime(googleCloudMainForm.getCommittedUsage())
                .clickAddToEstimateButton();

        calculatedForm = CreateCalculatedForm.completeCalculatedForm(actualPricingResults.getCalculatedForm(),
                actualPricingResults.getTotalMonthlyPrice());

        googleCloudCalculatorWindow = driver.getWindowHandle();
        log.info("Copied window handle for Calculator is: " + googleCloudCalculatorWindow);
        driver.switchTo().newWindow(WindowType.TAB);


        YopmailEmailGeneratorPage actualEmailResult = new YopmailHomePage(driver)
                .openPage()
                .generateEmailAddress()
                .copyEmailAddress();
        emailEstimateForm = EmailEstimateFormCompleting.completeEmailEstimateForm(actualEmailResult.getCopiedEmailAddress());
        log.info("Copied Email: " + emailEstimateForm.getEmailAddress());

        YopmailPageWindow = driver.getWindowHandle();
        log.info("Copied window handle for yopmail is: " + YopmailPageWindow);
        driver.switchTo().window(googleCloudCalculatorWindow);
        log.info("Switch to Calculator tab");

        actualPricingResults
                .clickTheEMAILButton()
                .sendEstimatedFormToEmail(emailEstimateForm.getEmailAddress());

        driver.switchTo().window(YopmailPageWindow);

        totalMonthlyPriceFromEmail = actualEmailResult
                .checkInboxMail()
                .getTotalMonthlyPrice();

        Matcher formMatcher = pricePattern.matcher(calculatedForm.getTotalEstimatedCostPerMonth());

        if (formMatcher.find()) {
            String actualCostValue = formMatcher.group(0);

            Assert.assertTrue(actualCostValue.equals(totalMonthlyPriceFromEmail),
                    "In Form: " + calculatedForm.getTotalEstimatedCostPerMonth() +
                            "\nIn Email: " + totalMonthlyPriceFromEmail);
        }
    }
}