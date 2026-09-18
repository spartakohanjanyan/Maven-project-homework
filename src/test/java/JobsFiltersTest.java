import business.JobsBusiness;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.JobsPage;

public class JobsFiltersTest {

    private WebDriver driver;
    private JobsPage jobsPage;
    private JobsBusiness jobsBusiness;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://staff.am");

        HomePage homepage = new HomePage(driver);
        homepage.clickJobsButton();

        jobsPage = new JobsPage(driver);
        jobsBusiness = new JobsBusiness(jobsPage);

        ((JavascriptExecutor) driver).executeScript(
                "var elem = document.querySelector('.Toastify') || document.querySelector('[class*=\"cookie\"]');" +
                        "if(elem) { elem.remove(); }"
        );
    }

    @DataProvider(name = "JobsFiltersData")
    public Object[][] getCategoryFilterData() {
        return new Object[][] {
                {"Job category", "Banking/credit"},
                {"Specialist level", "Junior"},
                {"Job salary", "Mentioned"}
        };
    }

    @Test(dataProvider = "JobsFiltersData")
    public void verifyFilterSelectionAndCount(String category, String filterName) {
        jobsBusiness.applyFilter(category, filterName);

        boolean isIconDisplayed = jobsBusiness.isFilterApplied(category, filterName);
        Assert.assertTrue(
                isIconDisplayed
        );
    }

    @AfterMethod
    public void closeWebPage() {
        if (driver != null) {
            driver.quit();
        }
    }
}