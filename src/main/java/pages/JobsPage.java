package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JobsPage extends BasePage {

    public JobsPage(WebDriver driver) {
        super(driver);
    }

    private final By firstJobViewMoreLocator =
            By.xpath(
                    "(//img[@alt='calendar-icon'])" +
                            "[1]/following::div[contains(text(),'View more')][1]"
            );

    private By getViewMoreLocator(String category) {

        String xpath = String.format(
                "//div[text()='%s']" +
                        "/following-sibling::div[@tabindex='0']",
                category
        );

        return By.xpath(xpath);
    }

    private By getFilterOptionLoc(
            String category,
            String filterName
    ) {

        String xpath = String.format(
                "//div[text()='%s']" +
                        "/following-sibling::div[not(@tabindex='0')]" +
                        "//span[text()='%s']//span",
                category,
                filterName
        );

        return By.xpath(xpath);
    }

    private By getFilterCheckmarkLoc(
            String category,
            String filterName
    ) {

        String xpath = String.format(
                "//div[text()='%s']" +
                        "/following-sibling::div[not(@tabindex='0')]" +
                        "//span[text()='%s']" +
                        "/ancestor::div[3]//img",
                category,
                filterName
        );

        return By.xpath(xpath);
    }

    public void filter(String category, String filterName) {
        clickIfPresent(getViewMoreLocator(category));

        WebElement oldFirstJob = wait.until(
                ExpectedConditions.presenceOfElementLocated(firstJobViewMoreLocator)
        );

        By filterLocator = getFilterOptionLoc(category, filterName);
        clickWithScroll(filterLocator);

        try {
            wait.until(ExpectedConditions.stalenessOf(oldFirstJob));
        } catch (TimeoutException ignored) {

        }

        wait.until(ExpectedConditions.elementToBeClickable(firstJobViewMoreLocator));
    }

    public boolean isFilterChecked(String category, String filterName) {
        By checkmarkLoc = getFilterCheckmarkLoc(category, filterName);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(checkmarkLoc));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    public JobDetailsPage openFirstJob() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        firstJobViewMoreLocator
                )
        );

        clickWithScroll(firstJobViewMoreLocator);

        return new JobDetailsPage(driver);
    }
}