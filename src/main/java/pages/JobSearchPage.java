package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JobSearchPage extends BasePage {

    private static final String JOBS_URL = "https://staff.am/jobs";

    private final By searchPlaceholderLoc = By.xpath("//input[@placeholder='Enter keywords...']");
    private final By searchBtnLoc = By.xpath("//div[text()='Search']");
    private final By clearFiltersLoc = By.xpath("//div[text()='Clear filters']");
    private final By noJobsMessageLoc = By.xpath("//*[contains(text(), 'Your search returned no results. Please try using different keywords.')]");
    private final By jobTitleText = By.xpath("//img[@alt='left-icon']/following::div[3]");

    public JobSearchPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(JOBS_URL);
    }

    public void enterSearchKeyword(String key) {
        type(searchPlaceholderLoc, key);
    }

    public void submitSearchWithEnter() {
        pressEnter(searchPlaceholderLoc);
    }

    public void clickSearchButton() {
        click(searchBtnLoc);
    }

    public void scrollToSearchInput() {
        scrollToElement(searchPlaceholderLoc);
    }

    public void clearFilters() {
        click(clearFiltersLoc);
    }

    public boolean isClearFiltersVisible() {
        return isVisible(clearFiltersLoc);
    }

    public boolean isClearFiltersInvisible() {
        return isInvisible(clearFiltersLoc);
    }

    public boolean isNoJobsMessageVisible() {
        return isVisible(noJobsMessageLoc);
    }

    public String getFirstJobTitleText() {
        return getText(jobTitleText);
    }

    public boolean isDataLoaded() {
        String currentText = getFirstJobTitleText();
        return wait.until(ExpectedConditions.invisibilityOfElementWithText(jobTitleText, currentText));
    }
}