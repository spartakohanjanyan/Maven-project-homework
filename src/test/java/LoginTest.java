import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");
    }

    @DataProvider(name = "users")
    public Object[][] users() {

        return new Object[][]{
                {"standard_user"},
                {"problem_user"},
                {"performance_glitch_user"},
                {"error_user"},
                {"visual_user"}
        };
    }


    @Test(dataProvider = "users")
    public void loginWithAllUsers(String username) {

        driver.findElement(
                By.xpath("//input[@id='user-name']")
        ).sendKeys(username);

        driver.findElement(
                By.xpath("//input[@id='password']")
        ).sendKeys("secret_sauce");

        driver.findElement(
                By.xpath("//input[@id='login-button']")
        ).click();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Login failed for user: " + username
        );
    }


    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}