package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    @FindBy(xpath = "//div[text()='Jobs']")
    private WebElement jobsButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickJobsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(jobsButton)).click();
    }
}