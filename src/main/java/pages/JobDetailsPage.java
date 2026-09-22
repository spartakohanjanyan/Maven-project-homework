package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JobDetailsPage extends BasePage {

    private final By categoryLoc =
            By.xpath(
                    "//div[contains(text(),'Category')]/following::div[1]"
            );

    private final By candidateLevelLoc =
            By.xpath(
                    "//div[contains(text(),'Required candidate level')]/following::div[1]"
            );

    private final By salaryLoc =
            By.xpath(
                    "//div[contains(text(),'Salary')]/following::div[1]"
            );

    public JobDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getCategory() {
        return getText(categoryLoc);
    }

    public String getCandidateLevel() {
        return getText(candidateLevelLoc);
    }

    public String getSalary() {
        return getText(salaryLoc);
    }
}