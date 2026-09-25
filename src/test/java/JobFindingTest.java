import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.JobSearchPage;
import business.JobSearchBusiness;

public class JobFindingTest {

    private WebDriver driver;
    private JobSearchBusiness jobSearchBusiness;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        JobSearchPage jobSearchPage = new JobSearchPage(driver);
        jobSearchBusiness = new JobSearchBusiness(jobSearchPage);

        jobSearchBusiness.openJobsPage();
        ((JavascriptExecutor) driver).executeScript(
                "var elem = document.querySelector('.Toastify') || document.querySelector('[class*=\"cookie\"]');" +
                        "if(elem) { elem.remove(); }"
        );
    }

    @Test
    public void jobsSearchPageTest() {

        jobSearchBusiness.searchForJobWithButton("qa");
        Assert.assertTrue(jobSearchBusiness.isClearFiltersVisible(), "Clear filters button should be visible");
        Assert.assertTrue(jobSearchBusiness.isDataLoaded(), "Data should be updated after search");

        jobSearchBusiness.searchForJobWithEnter("developer");
        Assert.assertTrue(jobSearchBusiness.isClearFiltersVisible(), "Clear filters button should be visible");
        Assert.assertTrue(jobSearchBusiness.isDataLoaded(), "Data should be updated after search");

        jobSearchBusiness.searchWithScrollToTop("tegersef");
        Assert.assertTrue(jobSearchBusiness.isNoJobsMessageVisible(), "No jobs message should be displayed");

        jobSearchBusiness.searchForJobWithEnter("");
        Assert.assertTrue(jobSearchBusiness.isClearFiltersInvisible(), "Clear filters should be invisible for empty search");
    }

    @AfterMethod
    public void closeWebPage() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}