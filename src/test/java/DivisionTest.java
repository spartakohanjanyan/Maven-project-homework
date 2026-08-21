import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DivisionTest {

    private Calculator calculator;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        calculator = new Calculator();
    }

    @Test(
            groups = {"smoke", "regression"},
            priority = 6
    )
    public void testDivision() {

        int result = calculator.divide(20, 5);

        Assert.assertEquals(
                result,
                4,
                "Division result is incorrect"
        );
    }

    @Test(
            groups = {"regression", "negative"},
            priority = 7,
            dependsOnMethods = "testDivision"
    )
    public void testDivisionByZero() {

        try {
            calculator.divide(10, 0);
            Assert.fail("Expected ArithmeticException was not thrown");
        } catch (ArithmeticException e) {
            Assert.assertEquals(e.getMessage(), "Cannot divide by zero");
        }
    }
}