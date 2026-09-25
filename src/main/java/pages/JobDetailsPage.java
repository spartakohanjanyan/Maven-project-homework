package pages;

import base.BasePage;
import enums.FilterGroupName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JobDetailsPage extends BasePage {

    public JobDetailsPage(WebDriver driver) {
        super(driver);
    }

    private By getDetailFieldLoc(FilterGroupName group) {
        String xpath = String.format("//div[contains(text(),'%s')]/following::div[1]",
                group.getNameInJobsDetailsPage());
        return By.xpath(xpath);
    }

    public String getFieldValue(FilterGroupName group) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(getDetailFieldLoc(group)));
        return element.getText();
    }

    public String getCategory() {
        return getFieldValue(FilterGroupName.JOB_CATEGORY);
    }
    public String getCandidateLevel() {
        return getFieldValue(FilterGroupName.SPECIALIST_LEVEL);
    }
    public String getSalary() {
        return getFieldValue(FilterGroupName.JOB_SALARY);
    }
}
