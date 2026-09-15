package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.Random;
import java.util.List;

public class JobsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By jobsLoc = By.xpath(".//img[@alt='left-icon']/ancestor::div[3]");
    private By expectedLocationLoc = By.xpath(".//img[contains(@src, 'location')]/following::div[@dir='auto']" +
            "[contains(@style, 'color: rgb(115')][1]");
    private By expectedJobTitleLoc = By.xpath(".//img[@alt='left-icon']/following::div[3]");
    private By expectedCompanyNameLoc = By.xpath(".//a[contains(@href, '/company/')]//div[@dir='auto']");
    private By expectedDateLoc = By.xpath(".//img[@alt='calendar-icon']/following::div[1]");
    private By viewMoreBtnLoc = By.xpath(".//img[@alt='calendar-icon']/following::div[contains(text(),'View more')]");

    public JobsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public WebElement getRandomJobCard() {
        List<WebElement> jobs = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(jobsLoc));
        int index = new Random().nextInt(jobs.size());
        return jobs.get(index);
    }

    public String getJobTitle(WebElement jobCard) {
        return jobCard.findElement(expectedJobTitleLoc).getText().trim();
    }

    public String getCompanyName(WebElement jobCard) {
        return jobCard.findElement(expectedCompanyNameLoc).getText().trim();
    }

    public String getLocation(WebElement jobCard) {
        return jobCard.findElement(expectedLocationLoc).getText().trim();
    }
    public String getDate(WebElement jobCard) {
        return jobCard.findElement(expectedDateLoc).getText().trim();
    }

    public void clickToJobDetails(WebElement jobCard) {
        wait.until(ExpectedConditions.visibilityOf(jobCard));
        WebElement viewMoreBtn = jobCard.findElement(viewMoreBtnLoc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", viewMoreBtn);
        new Actions(driver).moveToElement(viewMoreBtn).perform();
        wait.until(ExpectedConditions.elementToBeClickable(viewMoreBtn)).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("https://staff.am/jobs")));
    }
}