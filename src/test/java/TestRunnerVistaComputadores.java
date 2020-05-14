import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features/Nop-Commerce/VisualizacionComputadores.feature"
        , glue = {"co/com/sofka/webProject/test/stepdefinition"}
        , plugin = {"pretty", "html:target/cucumber", "json:target/cucumberProyectoBase.json"}
)



public class TestRunnerVistaComputadores {

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void teardown(){
    }
}
