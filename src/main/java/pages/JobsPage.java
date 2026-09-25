package pages;

import base.BasePage;
import enums.FilterGroupName;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JobsPage extends BasePage {
    private final By firstJobViewMoreLoc = By.xpath(
            "(//img[@alt='calendar-icon'])[1]/following::div[contains(text(),'View more')][1]");

    private final By firstJobTitleLoc = By.xpath(
            "(//img[@alt='calendar-icon'])[1]/following::div[contains(text(),'View more')][1]");

    public JobsPage(WebDriver driver) {
        super(driver);
    }

    private By getViewMoreLoc(FilterGroupName category) {
        String xpath = String.format("//div[text()='%s']/following-sibling::div[@tabindex='0']",
                category.getNameInJobsPage());
        return By.xpath(xpath);
    }

    private By getFilterOptionLoc(FilterGroupName category, String filterName) {
        String xpath = String.format("//div[text()='%s']/following-sibling::div[not(@tabindex='0')]//span[text()='%s']//span",
                category.getNameInJobsPage(), filterName);
        return By.xpath(xpath);
    }

    private By getFilterCheckmarkLoc(FilterGroupName category, String filterName) {
        String xpath = String.format("//div[text()='%s']/following-sibling::div[not(@tabindex='0')]//span[text()='%s']/ancestor::div[3]//img",
                category.getNameInJobsPage(), filterName);
        return By.xpath(xpath);
    }

    public void filter(FilterGroupName category, String filterName) {
        clickIfPresent(getViewMoreLoc(category));

        By filterLocator = getFilterOptionLoc(category, filterName);
        clickWithScroll(filterLocator);

        By checkmarkLoc = getFilterCheckmarkLoc(category, filterName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkmarkLoc));
    }

    public boolean isFilterChecked(FilterGroupName category, String filterName) {
        By checkmarkLoc = getFilterCheckmarkLoc(category, filterName);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(checkmarkLoc));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public JobDetailsPage openFirstJob() {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(firstJobViewMoreLoc)));
        clickWithScroll(firstJobViewMoreLoc);

        return new JobDetailsPage(driver);
    }
}