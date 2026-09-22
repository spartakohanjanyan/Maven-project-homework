package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected By cookieAcceptButton = (By.xpath("//div[contains(text(), 'We use cookies')]"));


    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    protected void clickWithScroll(By locator) {

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        scrollToElement(element);

        Actions actions = new Actions(driver);

        actions
                .moveToElement(element)
                .click()
                .perform();
    }

    protected void scrollToElement(WebElement element) {

        Actions actions = new Actions(driver);

        actions
                .scrollToElement(element)
                .perform();
    }

    protected String getText(By locator) {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getAttribute("textContent").trim();
    }

    protected void clickIfPresent(By locator) {

        WebDriverWait shortWait =
                new WebDriverWait(driver, Duration.ofSeconds(2));

        try {

            WebElement element = shortWait.until(
                    ExpectedConditions.elementToBeClickable(locator)
            );

            scrollToElement(element);

            new Actions(driver)
                    .moveToElement(element)
                    .click()
                    .perform();

        } catch (Exception ignored){
        }
    }

    public void acceptCookies() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButton)).click();
        } catch (Exception e) {
        }
    }
}