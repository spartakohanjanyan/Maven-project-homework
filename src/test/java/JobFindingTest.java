import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.JobSearchPage;

public class JobFindingTest {
    private WebDriver driver;
    private JobSearchPage jobSearchPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://staff.am/jobs");
        ((JavascriptExecutor) driver).executeScript(
                "var elem = document.querySelector('.Toastify') || document.querySelector('[class*=\"cookie\"]');" +
                        "if(elem) { elem.remove(); }"
        );

        jobSearchPage = new JobSearchPage(driver);
    }

    @Test
    public void jobsSearchPageTest() {
        jobSearchPage.scrollToSearchInput();
        jobSearchPage.searchForJob("qa");
        jobSearchPage.clickToSearchButton();
        Assert.assertTrue(jobSearchPage.isClearFiltersVisible());
        Assert.assertTrue(jobSearchPage.isDataLoaded());
        jobSearchPage.searchForJob("developer");
        jobSearchPage.pressEnter();
        Assert.assertTrue(jobSearchPage.isClearFiltersVisible());
        Assert.assertTrue(jobSearchPage.isDataLoaded());
        jobSearchPage.searchForJob("tegersef");
        jobSearchPage.scrollToTop();
        jobSearchPage.clickToSearchButton();
        Assert.assertTrue(jobSearchPage.isNoJobsMessageVisible());
        jobSearchPage.searchForJob("");
        jobSearchPage.pressEnter();
        Assert.assertTrue(jobSearchPage.isClearFiltersInvisible());
    }

    @AfterMethod
    public void closeWebPage(){
        driver.quit();
        driver = null;
    }
}
