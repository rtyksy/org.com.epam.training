package org.com.epam.training.Artem_Kosenko.webdriver.Task3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleCloudPricingCalculatorPage extends AbstractPage{

    private static final String DEFAULT_DROPDOWN_OPTION_XPATH = "//div[contains(@class, 'md-active')]" +
            "//md-option/div[contains(text(), '%s')]";
    private static final String PRODUCT_NAME_XPATH = "//div[contains(@title, '%s')]";

    private final By mainFrame = By.xpath("//*[@id='cloud-site']/devsite-iframe/iframe");
    private final By innerFrame = By.id("myFrame");
    private final By GPUTypeDropdown = By.xpath("//md-select[contains(@id, 'select_505')]");
    private final By resultBlock = By.xpath("//div[contains(@class, 'md-list-item-text')]");
    private final By totalMonthlyPrice = By.xpath("//*[@id='resultBlock']/md-card/md-card-content//div[@class='cpc-cart-total']/h2/b");
    private final By emailInput = By.cssSelector("form[name='emailForm'] input[type='email']");

    @FindBy(xpath = "//input[@id='input_98']")
    private WebElement numberOfInstancesInput;

    @FindBy(xpath = "//md-select[contains(@id, 'select_111')]")
    private WebElement operatingSystemDropdown;

    @FindBy(xpath = "//md-select[contains(@id, 'select_115')]")
    private WebElement VMClassDropdown;

    @FindBy(xpath = "//md-select[contains(@id, 'select_123')]")
    private WebElement instanceSeriesDropdown;

    @FindBy(xpath = "//md-select[contains(@id, 'select_125')]")
    private WebElement instanceTypeDropdown;

    @FindBy(xpath = "//md-checkbox[@ng-model='listingCtrl.computeServer.addGPUs']/div[contains(@class, 'md-container')]")
    private WebElement addGPUsCheckBox;

    @FindBy(xpath = "//md-select[contains(@id, 'select_507')]")
    private WebElement numberOfGPUsDropdown;

    @FindBy(xpath = "//form[@name='ComputeEngineForm']//md-select[contains(@id, 'select_464')]")
    private WebElement localSSDDropdown;

    @FindBy(xpath = "//md-select[contains(@id, 'select_131')]")
    private WebElement datacenterLocationDropdown;

    @FindBy(xpath = "//md-select[contains(@id, 'select_138')]")
    private WebElement committedUsageDropdown;

    @FindBy(xpath = "//form[@name='ComputeEngineForm']/div/button")
    private WebElement addToEstimateButton;

    @FindBy(id = "Email Estimate")
    private WebElement emailButton;

    @FindBy(xpath = "//*[@id='dialogContent_621']/form/md-dialog-actions/button[2]")
    private WebElement sendEmailButton;

    @FindBy(xpath = "//div[@id='select_container_132']")
    private WebElement dataLocationOption;
    @FindBy(xpath = "//div[@id='select_container_465']")
    private WebElement localSSDOption;
    @FindBy(xpath = "//div[@id='select_container_508']")
    private WebElement numberOfGpuOption;
    @FindBy(xpath = "//div[@id='select_container_506']")
    private WebElement  selectGpuTypeOption;
    @FindBy(xpath = "//div[@id='select_container_126']")
    private WebElement instanceTypeOption;
    @FindBy(xpath = "//div[@id='select_container_124']")
    private WebElement instanceSeriesOption;

    public GoogleCloudPricingCalculatorPage(WebDriver driver) {
        super(driver);
    }

    public GoogleCloudPricingCalculatorPage calculatePrice(FormData formData) {

        goToTheMainForm();
        selectProduct(formData.getProductName());
        numberOfInstancesInput.sendKeys(formData.getNumberOfInstances());
        selectOperatingSystem(formData.getOperationSystem());
        selectVMClass(formData.getVMClass());
        selectInstanceSeries(formData.getInstanceSeries());
        selectInstanceType(formData.getInstanceType());
        addGPUsCheckBox.click();
        selectGPUType(formData.getGPUType());
        selectNumberOfGPUs(formData.getNumberOfGPUs());
        selectLocalSSD(formData.getLocalSSD());
        selectDatacenterLocation(formData.getDatacenterLocation());
        selectCommittedUsageTime(formData.getCommittedUsage());
        addToEstimateButton.click();
        return this;
    }

    public GoogleCloudPricingCalculatorPage goToTheMainForm() {
        switchToFrame(mainFrame);
        switchToFrame(innerFrame);
        return this;
    }

    public GoogleCloudPricingCalculatorPage selectProduct (String productName) {
        waitPresenceOfElementLocated(By.xpath(String.format(PRODUCT_NAME_XPATH, productName)))
                .click();
        return this;
    }

    public GoogleCloudPricingCalculatorPage pressTheEMAILButton() {
        goToTheMainForm();
        emailButton.click();
        return this;
    }

    public GoogleCloudPricingCalculatorPage sendEstimatedFormToEmail() {
        try {
            String copiedText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            waitPresenceOfElementLocated(emailInput).sendKeys(copiedText);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        sendEmailButton.click();
        return this;
    }

    public List<String> getCalculatedForm() {
        List<String> calculatedFormText = new ArrayList<>();
        List<WebElement> calculatedForm = waitPresenceOfAllElementsLocatedBy(resultBlock);
        for (WebElement element : calculatedForm) {
            calculatedFormText.add(element.getText());
        }
        return calculatedFormText;
    }

    public String getTotalMonthlyPrice() {
        return waitPresenceOfElementLocated(totalMonthlyPrice).getText();
    }

    public GoogleCloudPricingCalculatorPage selectOperatingSystem(String operatingSystem) {
        operatingSystemDropdown.click();
        selectDropdownOption(DEFAULT_DROPDOWN_OPTION_XPATH, operatingSystem);
        return this;
    }

    public GoogleCloudPricingCalculatorPage selectVMClass(String VMClass) {
        VMClassDropdown.click();
        selectDropdownOption(DEFAULT_DROPDOWN_OPTION_XPATH, VMClass);
        return this;
    }

    public GoogleCloudPricingCalculatorPage selectInstanceSeries(String series) {
        instanceSeriesDropdown.click();
        waitElementToBeClickable(instanceSeriesOption);
        selectDropdownOption(DEFAULT_DROPDOWN_OPTION_XPATH, series);
        return this;
    }

    public GoogleCloudPricingCalculatorPage selectInstanceType(String instanceType) {
        instanceTypeDropdown.click();
        waitElementToBeClickable(instanceTypeOption);
        selectDropdownOption(DEFAULT_DROPDOWN_OPTION_XPATH, instanceType);
        return this;
    }

    public GoogleCloudPricingCalculatorPage selectGPUType(String GPUType) {
        waitPresenceOfElementLocated(GPUTypeDropdown).click();
        waitElementToBeClickable(selectGpuTypeOption);
        selectDropdownOption(DEFAULT_DROPDOWN_OPTION_XPATH, GPUType);
        return this;
    }

    public GoogleCloudPricingCalculatorPage selectNumberOfGPUs(String numberOfGPUs) {
        numberOfGPUsDropdown.click();
        waitElementToBeClickable(numberOfGpuOption);
        selectDropdownOption(DEFAULT_DROPDOWN_OPTION_XPATH, numberOfGPUs);
        return this;
    }

    public GoogleCloudPricingCalculatorPage selectLocalSSD(String localSSD) {
        localSSDDropdown.click();
        waitElementToBeClickable(localSSDOption);
        selectDropdownOption(DEFAULT_DROPDOWN_OPTION_XPATH, localSSD);
        return this;
    }

    public GoogleCloudPricingCalculatorPage selectDatacenterLocation(String datacenterLocation) {
        datacenterLocationDropdown.click();
        waitElementToBeClickable(dataLocationOption);
        selectDropdownOption(DEFAULT_DROPDOWN_OPTION_XPATH, datacenterLocation);
        return this;
    }

    public GoogleCloudPricingCalculatorPage selectCommittedUsageTime(String committedUsage) {
        committedUsageDropdown.click();
        selectDropdownOption(DEFAULT_DROPDOWN_OPTION_XPATH, committedUsage);
        return this;
    }
}
