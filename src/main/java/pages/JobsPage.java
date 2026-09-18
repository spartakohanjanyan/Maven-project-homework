package pages;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class JobsPage extends BasePage {

    public JobsPage(WebDriver driver) {
        super(driver);
    }

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

    private By getFilterCheckmarkLoc(String category, String filterName) {
        String xpath = String.format(
                "//div[text()='%s']/following-sibling::div[not(@tabindex='0')]//span[text()='%s']/ancestor::div[3]//img",
                category,
                filterName
        );
        return By.xpath(xpath);
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

    public boolean isFilterChecked(String category, String filterName) {
        By checkmarkLoc = getFilterCheckmarkLoc(category, filterName);

        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(checkmarkLoc));

            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}