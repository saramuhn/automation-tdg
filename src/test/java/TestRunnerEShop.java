

import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features/E-shop"
        , glue = {"co/com/client/webProject/test/stepdefinition/"}
        , plugin = {"pretty", "html:target/cucumber", "json:target/cucumberProyectoBase.json"}
)


public class TestRunnerEShop {


    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void teardown(){
    }
}
