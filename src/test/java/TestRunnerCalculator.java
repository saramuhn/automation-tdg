import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features/Calculator"
        , glue = {"co/com/client/desktopProject/test/stepdefinition"}
        , plugin = {"pretty", "html:target/cucumber", "json:target/cucumberProyectoBase.json"}
)


public class TestRunnerCalculator {


    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void teardown(){
    }
}
