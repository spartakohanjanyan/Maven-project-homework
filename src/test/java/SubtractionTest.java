import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SubtractionTest {

    private Calculator calculator;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        calculator = new Calculator();
    }

    @Test(
            groups = {"smoke", "regression"},
            priority = 2
    )
    public void testSubtraction() {

        int result = calculator.subtract(10, 5);

        Assert.assertEquals(
                result,
                5,
                "Subtraction result is incorrect"
        );
    }

    @Test(
            groups = {"regression"},
            priority = 3
    )
    public void testNegativeResult() {

        int result = calculator.subtract(5, 10);

        Assert.assertEquals(
                result,
                -5,
                "Negative subtraction result is incorrect"
        );
    }
}