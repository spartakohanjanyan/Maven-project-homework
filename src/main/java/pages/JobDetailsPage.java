package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class JobDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By expectedLocation = By.xpath("//img[@alt='eyeIcon']/following::div[@dir='auto']");
    private By expectedJobTitle = By.xpath("//h1[@role='heading']");
    private By expectedCompanyName = By.xpath("//a[contains(@href, '/company/')]" +
            "//div[@dir='auto' and contains(@style, 'font-weight: bold')] ");
    private By expectedDate = By.xpath("//img[@alt='calendarGreen']/following::div[1]");

    public JobDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    public String getJobTitle(){
        return (wait.until(ExpectedConditions.visibilityOfElementLocated(expectedJobTitle))
                .getAttribute("textContent").trim());
    }
    public String getCompanyName(){
        return (wait.until(ExpectedConditions.visibilityOfElementLocated(expectedCompanyName))
                .getAttribute("textContent").trim());
    }
    public String getLocation(){
        return (wait.until(ExpectedConditions.visibilityOfElementLocated(expectedLocation))
                .getAttribute("textContent").trim());
    }
    public String getDate(){
        return (wait.until(ExpectedConditions.visibilityOfElementLocated(expectedDate))
                .getAttribute("textContent").trim());
    }
}
