package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    @FindBy(xpath = "//div[text()='Jobs']")
    private WebElement jobsButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public JobsPage clickJobsButton() {
        acceptCookies();

        wait.until(
                ExpectedConditions.elementToBeClickable(jobsButton)
        );

        new Actions(driver)
                .moveToElement(jobsButton)
                .click()
                .perform();

        return new JobsPage(driver);
    }
}