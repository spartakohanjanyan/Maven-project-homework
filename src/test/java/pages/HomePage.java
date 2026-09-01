package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    private By switchToStandardSearchButton =
            By.xpath("//*[self::button or self::a][contains(.,'Switch to standard search')]");

    private By categoriesSelectContainer = By.xpath(
            "//div[contains(concat(' ', normalize-space(@class), ' '), ' ant-select ') " +
                    "and .//*[normalize-space(text())='All categories']]"
    );

    private By categoriesSelectorBox = By.cssSelector(".ant-select-selector");

    private By categoryOptions = By.cssSelector(
            ".ant-select-dropdown:not(.ant-select-dropdown-hidden) .ant-select-item-option"
    );

    private By searchButton = By.xpath(
            "//img[@alt='search-icon']/ancestor::div[@tabindex='0'][1]"
    );

    public void open() {
        driver.get("https://staff.am/");
    }

    public void switchToStandardSearch() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        switchToStandardSearchButton
                )
        ).click();
    }

    public void selectRandomCategory() {
        WebElement container = wait.until(
                ExpectedConditions.visibilityOfElementLocated(categoriesSelectContainer)
        );
        WebElement selectorBox = container.findElement(categoriesSelectorBox);
        wait.until(ExpectedConditions.elementToBeClickable(selectorBox)).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(categoryOptions));

        List<WebElement> visibleOptions = driver.findElements(categoryOptions)
                .stream()
                .filter(WebElement::isDisplayed)
                .collect(Collectors.toList());

        if (visibleOptions.isEmpty()) {
            throw new IllegalStateException("Category dropdown has no visible options to choose from");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(visibleOptions.size());
        visibleOptions.get(randomIndex).click();
    }

    public void clickSearch() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        searchButton
                )
        ).click();
    }
}