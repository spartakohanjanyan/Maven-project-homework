import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class CartCheckoutTest {

    private WebDriver driver;


    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordLeakDetection,PasswordCheck,PasswordManagerRedesign");


        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");
    }


    @Test
    public void addTShirtToCartAndCheckout() {
        driver.findElement(
                By.xpath("//input[@id='user-name']")
        ).sendKeys("standard_user");

        driver.findElement(
                By.xpath("//input[@id='password']")
        ).sendKeys("secret_sauce");

        driver.findElement(
                By.xpath("//input[@id='login-button']")
        ).click();

        driver.findElement(
                By.xpath("//button[@id='add-to-cart-sauce-labs-bolt-t-shirt']")
        ).click();

        String cartCount = driver.findElement(
                By.xpath("//span[@class='shopping_cart_badge']")
        ).getText();


        Assert.assertEquals(
                cartCount,
                "1",
                "Cart count was not updated correctly"
        );

        driver.findElement(
                By.xpath("//a[@class='shopping_cart_link']")
        ).click();

        String productName = driver.findElement(
                By.xpath("//div[@class='inventory_item_name']")
        ).getText();


        Assert.assertEquals(
                productName,
                "Sauce Labs Bolt T-Shirt",
                "Wrong product was added to cart"
        );

        driver.findElement(
                By.xpath("//button[@id='checkout']")
        ).click();

        driver.findElement(
                By.xpath("//input[@id='first-name']")
        ).sendKeys("Spartak");

        driver.findElement(
                By.xpath("//input[@id='last-name']")
        ).sendKeys("Test");

        driver.findElement(
                By.xpath("//input[@id='postal-code']")
        ).sendKeys("0010");

        driver.findElement(
                By.xpath("//input[@id='continue']")
        ).click();


        String subtotalText = driver.findElement(
                By.xpath("//div[@class='summary_subtotal_label']")
        ).getText();

        String taxText = driver.findElement(
                By.xpath("//div[@class='summary_tax_label']")
        ).getText();

        String totalText = driver.findElement(
                By.xpath("//div[@class='summary_total_label']")
        ).getText();


        double subtotal = Double.parseDouble(
                subtotalText.replaceAll("[^0-9.]", "")
        );

        double tax = Double.parseDouble(
                taxText.replaceAll("[^0-9.]", "")
        );

        double total = Double.parseDouble(
                totalText.replaceAll("[^0-9.]", "")
        );


        Assert.assertEquals(
                total,
                subtotal + tax,
                0.01,
                "Final price is incorrect"
        );
    }
}
