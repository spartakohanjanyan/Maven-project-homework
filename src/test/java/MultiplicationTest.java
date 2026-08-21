import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MultiplicationTest {

    private Calculator calculator;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        calculator = new Calculator();
    }

    @Test(
            groups = {"smoke", "regression"},
            priority = 4
    )
    public void testMultiplication() {

        int result = calculator.multiply(5, 4);

        Assert.assertEquals(
                result,
                20,
                "Multiplication result is incorrect"
        );
    }

    @Test(
            groups = {"regression"},
            priority = 5,
            dependsOnMethods = "testMultiplication"
    )
    public void testMultiplicationByZero() {

        int result = calculator.multiply(10, 0);

        Assert.assertEquals(
                result,
                0,
                "Multiplication by zero should return zero"
        );
    }
}