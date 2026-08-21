import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AdditionTest {

    private Calculator calculator;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        calculator = new Calculator();
    }

    @DataProvider(name = "additionData")
    public Object[][] additionData() {
        return new Object[][]{
                {2, 3, 5},
                {10, 5, 15},
                {-2, 5, 3},
                {100, 200, 300}
        };
    }

    @Test(
            dataProvider = "additionData",
            groups = {"smoke", "regression"},
            priority = 1
    )
    public void testAddition(int a, int b, int expected) {

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(
                calculator.add(a, b),
                expected,
                "Addition result is incorrect"
        );

        softAssert.assertAll();
    }
}