package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JobsPage extends BasePage {

    private static final String JOBS_URL = "https://staff.am/jobs";

    public JobsPage(WebDriver driver) {
        super(driver);
    }

    private final By jobsTitlesloc = By.xpath(".//img[@alt='left-icon']/following::div[3]");

    private By getViewMoreLoc(String category) {
        String xpath = String.format("//div[text()='%s']/following-sibling::div[@tabindex='0']", category);
        return By.xpath(xpath);
    }

    private By getFilterOptionLoc(String category, String filterName) {
        String xpath = String.format(
                "//div[text()='%s']/following-sibling::div[not(@tabindex='0')]//span[text()='%s']//span",
                category,
                filterName
        );
        return By.xpath(xpath);
    }

    public void open() {
        driver.get(JOBS_URL);
    }


    public void clickViewMoreIfExists(String category) {
        By viewMoreLoc = getViewMoreLoc(category);
        clickIfPresent(viewMoreLoc);
    }

    public void filter(String category, String filterName) {
        clickViewMoreIfExists(category);
        By filterLoc = getFilterOptionLoc(category, filterName);
        click(filterLoc);
    }

    public String getExpectedJobsCountText(String category, String filterName) {
        By locator = getFilterOptionLoc(category, filterName);
        String rawText = getText(locator);
        return rawText.replaceAll("[^0-9]", "");
    }

    public String getActualDisplayedJobsCountText() {
        int count = driver.findElements(jobsTitlesloc).size();
        return String.valueOf(count);
    }

    public void waitForJobsToRefresh() {
        wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(jobsTitlesloc)
        ));
    }
}