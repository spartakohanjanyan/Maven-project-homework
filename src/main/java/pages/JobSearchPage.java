package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class JobSearchPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By searchPlaceholderLoc = By.xpath("//input[@placeholder='Enter keywords...']");
    private By searchBtnLoс = By.xpath("//div[text()='Search']");
    private By clearFiltersLoc = By.xpath("//div[text()='Clear filters']");
    private By noJobsMessageLoc =
            By.xpath("//*[contains(text(), 'Your search returned no results. Please try using different keywords.')]");
    private By jobTitleText = By.xpath("//img[@alt='left-icon']/following::div[3]");

    public JobSearchPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    public void searchForJob(String key) {
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(searchPlaceholderLoc));
        searchInput.clear();
        searchInput.sendKeys(key);
    }
    public void pressEnter() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchPlaceholderLoc));
        element.sendKeys(Keys.ENTER);
    }
    public void clickToSearchButton() {
        scrollToElement(searchBtnLoс);
        wait.until(ExpectedConditions.elementToBeClickable(searchBtnLoс)).click();
    }
    public void scrollToSearchInput() {
        scrollToElement(searchPlaceholderLoc);
    }
    public boolean isClearFiltersVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(clearFiltersLoc));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    public boolean isClearFiltersInvisible() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(clearFiltersLoc));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    public void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo({top: 0, behavior: 'instant'});");
    }
    private void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
    }
    public String getText(){
        return (wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitleText))
                .getText());
    }
    public boolean isDataLoaded() {
        return wait.until(ExpectedConditions.invisibilityOfElementWithText(jobTitleText, getText()));
    }
    public boolean isNoJobsMessageVisible() {
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(noJobsMessageLoc));
            return true;
        } catch (TimeoutException e){
            return false;
        }
    }
    public void clearFilters(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(clearFiltersLoc)).click();
    }
}
