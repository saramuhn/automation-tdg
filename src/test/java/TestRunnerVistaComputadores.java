import co.com.sofka.test.utils.files.PropertiesFile;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.nio.file.Path;
import java.nio.file.Paths;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features/Nop-Commerce/VisualizacionComputadores.feature"
        , glue = {"co/com/sofka/webProject/test/stepdefinition"}
        , plugin = {"pretty", "html:target/cucumber", "json:target/cucumberProyectoBase.json"}
)



public class TestRunnerVistaComputadores {

    @BeforeClass
    public static void setup() {
       Path propertiesFolder = Paths.get(System.getProperty("user.dir"), "src/main/resources/properties/");
        PropertiesFile propertiesFile = new PropertiesFile("default", propertiesFolder);
        // Seteo de la ruta en donde quedara la evidencia de acuerdo en el sistema opertivo que se este ejecutanto
        String rutaEvidencias;
        if(System.getProperty("os.name").contains("Windows")) {
            rutaEvidencias = propertiesFile.getFieldValue("files.evidence.windows");
        }else {
            rutaEvidencias = propertiesFile.getFieldValue("files.evidence.linux");
        }
        propertiesFile.updateFieldValue("files.evidence", rutaEvidencias);
    }

    @AfterClass
    public static void teardown(){
    }

}
