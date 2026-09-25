package base;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.JobsPage;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected By cookieAcceptButton = (By.xpath("//div[contains(text(), 'We use cookies')]"));
    @FindBy(xpath = "//div[text()='Jobs']")
    private WebElement jobsButton;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    public JobsPage clickJobsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(jobsButton));

        actions.moveToElement(jobsButton)
                .click()
                .perform();

        return new JobsPage(driver);
    }

    protected void clickWithScroll(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollToElement(element);
        actions.moveToElement(element).click().perform();
    }

    protected void scrollToElement(WebElement element) {
        actions.scrollToElement(element).perform();
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText().trim();
    }

    protected void clickIfPresent(By locator) {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            WebElement element = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
            scrollToElement(element);
            actions.moveToElement(element).click().perform();
        } catch (Exception ignored) {
            return;
        }
    }

    public void acceptCookies() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButton)).click();
        } catch (Exception e) {
            return;
        }
    }
}