package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class JobResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public JobResultsPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    private By clearFiltersButton = By.xpath(
            "//div[normalize-space(text())='Clear filters']/ancestor::div[@tabindex='0'][1]"
    );

    public boolean isClearFiltersButtonDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            clearFiltersButton
                    )
            ).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public void clickClearFilters() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        clearFiltersButton
                )
        ).click();
    }

    public boolean isClearFiltersButtonInvisible() {
        try {
            return wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            clearFiltersButton
                    )
            );
        } catch (TimeoutException e) {
            return false;
        }
    }
}