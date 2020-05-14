package co.com.sofka.webProject.test.stepdefinition;

import co.com.sofka.test.exceptions.WebActionsException;
import co.com.sofka.webProject.test.helpers.TestInfo;
import co.com.sofka.webProject.test.helpers.ExtentReport;
import co.com.sofka.webProject.test.internalaction.InternalActionWeb;
import co.com.sofka.test.actions.Action;
import co.com.sofka.test.evidence.reports.Assert;
import co.com.sofka.test.evidence.reports.Report;
import co.com.sofka.test.utils.files.PropertiesFile;
import co.com.sofka.webProject.test.controllers.DisplaycomputersController;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;

public class VisualizacionComputadoresStep {
    private PropertiesFile globalProps = Action.getPropertiesFiles();
    private InternalActionWeb internalActionWeb;
    private DisplaycomputersController displaycomputersController;
    private TestInfo testInfo;

    @Before
    public void setUp(Scenario scenario) {
        Assert.Soft.init();
        testInfo = new TestInfo(scenario);
        //Se inician las instancias de los drivers que tengamos que iniciar con los nombres de las carpetas de evidencia
        final String PROJECT_NAME = globalProps.getFieldValue("projectWeb.name")
                .replace(StringUtils.SPACE, "_");
        internalActionWeb = new InternalActionWeb(PROJECT_NAME);
        printInitLogs(testInfo);
        ExtentReport extentReport = new ExtentReport();
        Report.setReporter(extentReport);
        displaycomputersController = new DisplaycomputersController(internalActionWeb);
    }

    @After
    public void tearDown() {
        Assert.Soft.finish();
        //Se imprime en los logs un diferenciador cada vez que inicia un escenario
        printEndingLogs(testInfo);
        internalActionWeb.closeBrowser();
        internalActionWeb.clearDriver();
    }

    @Given("que quiero comprar online en la pagina de NopCommerce")
    public void queQuieroComprarOnlineEnLaPaginaDeNopCommerce() {
        String url = globalProps.getFieldValue("app.url");
        displaycomputersController.iniciarAplicacion(url, testInfo.getFeatureName(), testInfo.getScenarioName());
    }

    @When("cliqueo la opcion Computers del menu")
    public void cliqueoLaOpcionComputersDelMenu() throws WebActionsException {
        displaycomputersController.mostrarCategorias();
    }

    @Then("muestra {string}")
    public void muestra(String categoria) {
        displaycomputersController.buscarCategoriaInFrame(categoria);
    }

    @And("muestra la informacion con los productos de la {string}")
    public void muestraLaInformacionConLosProductosDeLa(String category) throws WebActionsException {
        displaycomputersController.validarOpcionMuestraCategorias();
    }

    /*----------------------------------------------------------------------------------*/
    @Given("que quiero filtrar el precio de la {string}")
    public void queQuieroFiltrarElPrecioDeLa(String categoria) {
        String url = globalProps.getFieldValue("app.url");
        displaycomputersController.iniciarAplicacion(url, testInfo.getFeatureName(), testInfo.getScenarioName());
        displaycomputersController.buscarCategoriaSubMenu(categoria.toLowerCase());
    }

    @When("cliqueo el {string}")
    public void cliqueoEl(String filtro) throws WebActionsException {
        displaycomputersController.seleccionarFiltro(filtro);

    }

    @Then("muestra productos en el rango del {string} en la  {string}")
    public void muestraProductosEnElRangoDelEnLa(String filtro, String categoria) throws WebActionsException {
        displaycomputersController.obtenerPrecios(filtro,categoria);
    }


    @And("el resumen del producto")
    public void elResumenDelProducto() {
        displaycomputersController.resumenDelProducto();
    }
    /*----------------------------------------------------------------------------------*/
    @Given("que busque un producto en la {string}")
    public void queBusqueUnProductoEnLa(String categoria) {
        String url = globalProps.getFieldValue("app.url.category");
        displaycomputersController.iniciarAplicacion(url, testInfo.getFeatureName(), testInfo.getScenarioName());
        displaycomputersController.buscarProductoCategoria(categoria);
    }

    @When("cliqueo el producto agregando una {string} para agregarlo a {string}")
    public void cliqueoElProductoAgregandoUnaParaAgregarloA(String cantidad, String medio) throws WebActionsException {
        displaycomputersController.cambiarCantidad(cantidad);
        displaycomputersController.agregarMedio(medio);
    }

    @Then("muestra el detalle del producto con el precio de la {string} con la informacion del {string}")
    public void muestraElDetalleDelProductoConElPrecioDeLaConLaInformacionDel(String cantidad, String medio) throws WebActionsException {
        displaycomputersController.validarTituloMedio(medio);
        displaycomputersController.validarDetalleProducto(medio,cantidad);
    }
    /*-----------------------------------------------------------------------------------------------*/

    public void printInitLogs(TestInfo testInfo) {
        String initMsg = StringUtils.LF
                .concat("*****************************************************************************************************************************************************")
                .concat(StringUtils.LF)
                .concat("INICIO TRANSACCIÓN: ").concat(testInfo.getFeatureName())
                .concat(StringUtils.LF)
                .concat(">> ESCENARIO: ").concat(testInfo.getScenarioValue())
                .concat(StringUtils.LF)
                .concat("*****************************************************************************************************************************************************");
        //Se imprime en los 3 logs para agrupar
        Report.Business.logInfo(initMsg);
        Report.TestLog.logInfo(initMsg);
        Report.Evidence.log(initMsg);
    }

    public void printEndingLogs(TestInfo testInfo) {
        String initMsg = StringUtils.LF
                .concat("*****************************************************************************************************************************************************")
                .concat(StringUtils.LF)
                .concat("FIN TRANSACCIÓN: ").concat(testInfo.getFeatureName())
                .concat(StringUtils.LF)
                .concat(">> ESCENARIO: ").concat(testInfo.getScenarioValue())
                .concat(StringUtils.LF)
                .concat(">> RESULTADO: ").concat(testInfo.getStatus())
                .concat(StringUtils.LF)
                .concat("*****************************************************************************************************************************************************");
        //Se imprime en los 3 logs para agrupar
        Report.Business.logInfo(initMsg);
        Report.TestLog.logInfo(initMsg);
        Report.Evidence.log(initMsg);
    }



}
