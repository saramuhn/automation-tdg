package co.com.client.desktopProject.test.stepdefinition;

import co.com.client.desktopProject.test.controllers.CalculatorController;
import co.com.client.desktopProject.test.helpers.ExtentReport;
import co.com.client.desktopProject.test.helpers.TestInfo;
import co.com.client.desktopProject.test.internalaction.InternalActionDesk;
import co.com.sofka.test.actions.Action;
import co.com.sofka.test.evidence.reports.Assert;
import co.com.sofka.test.evidence.reports.Report;
import co.com.sofka.test.utils.files.PropertiesFile;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StepsDefinition {
   
    //Scenario Data
    private  TestInfo testInfo;
    //Instancias de controladores
    private CalculatorController calculatorController = new CalculatorController();

    // Variables Globales
    public static InternalActionDesk internalActionDesk;
    public static PropertiesFile globalProps = Action.getPropertiesFiles(); // archivo propiedades por defecto
    private static Path propertiesFolder = Paths.get("src/main/resources/properties");// archivo propiedades personalizado
    public static PropertiesFile customProps = new PropertiesFile("custom", propertiesFolder);
    //Datos del flujo
    private String operacion = "";
    private String resultadoObtenido = "";


    @Before
    public void setUp(Scenario scenario) {
        /*
        Importante si se usarán aserciones suaves, se situa en el lugar donde iniciaremos las aserciones,
         en este caso, previo a la iniciación de cada escenario pero se puede ubicar donde sea necesario
         */
        Assert.Soft.init();
        testInfo = new TestInfo(scenario);
        //Se imprime en los logs un diferenciador cada vez que inicia un escenario
        printInitLogs(testInfo.getFeatureName(), testInfo.getScenarioValue());
        //Se inician las instancias de los drivers que tengamos que iniciar con los nombres de las carpetas de evidencia
        final String PROJECT_NAME = globalProps.getFieldValue("projectDesk.name")
                .replace(StringUtils.SPACE,"_");
        internalActionDesk = new InternalActionDesk(PROJECT_NAME);
        /*
        En caso de contar con alguna interfaz de reportería se debe agregar al reporteador con una clase
         creada por nosotros con esta interfaz implementada
         */
        ExtentReport extentReport = new ExtentReport();
        Report.setReporter(extentReport);
    }

    @After
    public void tearDown() {
        Assert.Soft.finish();
        //Se imprime en los logs un diferenciador cada vez que inicia un escenario
        printEndingLogs(testInfo.getFeatureName(), testInfo.getScenarioValue());
        internalActionDesk.closeApp("Calculator.exe");
        internalActionDesk.clearDriver();
        /*
        Se realizan todas las operaciones de cierre necesario sin importar el resultado del test
        (ej: finalizar los drivers instanciados)
         */

    }


    @Given("^que quiero ejecutar una (.*?) matematica$")
    public void queQuieroEjecutarUnaOperacionMatematica(String operacion) {
        this.operacion = operacion;
        calculatorController.iniciarAplicacion(testInfo.getFeatureName());
    }

    @When("^cuando ingreso el (.*?) y el (.*?)$")
    public void cuandoIngresoElNumero1YElNumero2(String numero1, String numero2) {
        calculatorController.ingresarDatos(operacion,numero1,numero2);
    }

    @Then("^la aplicacion muestra el (.*?) esperado$")
    public void laAplicacionMuestraElResultadoEsperado(String resultadoEsperado) {
        resultadoObtenido = calculatorController.obtenerResultado();
        calculatorController.compararResultados(resultadoEsperado,resultadoObtenido);
    }




    public void printInitLogs(String feature, String scenario) {
        String initMsg = StringUtils.LF
                .concat("*****************************************************************************************************************************************************")
                .concat(StringUtils.LF)
                .concat("INICIO TRANSACCIÓN: ").concat(feature)
                .concat(StringUtils.LF)
                .concat(">> ESCENARIO: ").concat(scenario)
                .concat(StringUtils.LF)
                .concat("*****************************************************************************************************************************************************");
        //Se imprime en los 3 logs para agrupar
        Report.Business.logInfo(initMsg);
        Report.TestLog.logInfo(initMsg);
        Report.Evidence.log(initMsg);
    }

    public void printEndingLogs(String feature, String scenario) {
        String initMsg = StringUtils.LF
                .concat("*****************************************************************************************************************************************************")
                .concat(StringUtils.LF)
                .concat("FIN TRANSACCIÓN: ").concat(feature)
                .concat(StringUtils.LF)
                .concat(">> ESCENARIO: ").concat(scenario)
                .concat(StringUtils.LF)
                .concat("*****************************************************************************************************************************************************");
        //Se imprime en los 3 logs para agrupar
        Report.Business.logInfo(initMsg);
        Report.TestLog.logInfo(initMsg);
        Report.Evidence.log(initMsg);
    }
}
