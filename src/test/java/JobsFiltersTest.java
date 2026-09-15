import business.JobsBusiness;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.JobsPage;

public class JobsFiltersTest {

    private WebDriver driver;
    private JobsPage jobsPage;
    private JobsBusiness jobsBusiness;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        jobsPage = new JobsPage(driver);
        jobsBusiness = new JobsBusiness(jobsPage);

        jobsPage.open();
        ((JavascriptExecutor) driver).executeScript(
                "var elem = document.querySelector('.Toastify') || document.querySelector('[class*=\"cookie\"]');" +
                        "if(elem) { elem.remove(); }"
        );
    }

    @Test
    public void verifyMultipleFiltersCombination() {

        jobsBusiness.applyFilter("Specialist level", "Junior");
        jobsBusiness.applyFilter("Job salary", "Mentioned");
        String expectedCount = jobsBusiness.getExpectedCount("Job salary", "Mentioned");
        String actualCount = jobsBusiness.getActualCount();
        Assert.assertEquals(
                actualCount,
                expectedCount,
                "The actual jobs count on page doesn't equal the count in filter"
        );
    }

    @AfterMethod
    public void closeWebPage() {
        if (driver != null) {
            driver.quit();
        }
    }
}