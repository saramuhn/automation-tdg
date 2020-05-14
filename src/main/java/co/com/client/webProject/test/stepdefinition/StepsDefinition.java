package co.com.client.webProject.test.stepdefinition;

import co.com.client.desktopProject.test.helpers.TestInfo;
import co.com.client.webProject.test.controllers.DataController;
import co.com.client.webProject.test.controllers.SearchController;
import co.com.client.webProject.test.controllers.ShoppingController;
import co.com.client.webProject.test.helpers.ExtentReport;
import co.com.client.webProject.test.internalaction.InternalActionWS;
import co.com.client.webProject.test.internalaction.InternalActionWeb;
import co.com.sofka.test.actions.Action;
import co.com.sofka.test.evidence.reports.Assert;
import co.com.sofka.test.evidence.reports.Report;
import co.com.sofka.test.utils.files.PropertiesFile;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StepsDefinition {

    // Utilidades Globales
    // archivo propiedades por defecto
    private PropertiesFile globalProps = Action.getPropertiesFiles();

        /* archivo propiedades personalizado
    private Path propertiesFolder = Paths.get("src/main/resources/properties");
    private PropertiesFile customProps = new PropertiesFile("custom", propertiesFolder);*/

    //Instancias de controladores
    private ShoppingController shoppingController;
    private SearchController searchController;
    private DataController dataController;

    //Instancias de Internal Actions
    private InternalActionWeb internalActionWeb;
    private InternalActionWS internalActionWS;

    // Datos del flujo
    private TestInfo testInfo;
    private float unitPriceWeb = 0;
    private float unitPriceDB = 0;
    private float totalPriceWeb = 0;
    private float totalPriceWS = 0;

    @Before
    public void setUp(Scenario scenario) {
        /*
        Importante si se usarán aserciones suaves, se situa en el lugar donde iniciaremos las aserciones,
         en este caso, previo a la iniciación de cada escenario pero se puede ubicar donde sea necesario
         */
        Assert.Soft.init();

        /*Se instancia el elemento que nos permitirá obtener información durante ejecución del ambiente,
        escenario actual, y parametros desde el archivo .feature
        */
        testInfo = new TestInfo(scenario);

        //Se inician las instancias de los drivers que tengamos que iniciar con los nombres de las carpetas de evidencia
        final String PROJECT_NAME = globalProps.getFieldValue("projectWeb.name")
                .replace(StringUtils.SPACE, "_");
        internalActionWeb = new InternalActionWeb(PROJECT_NAME);
        internalActionWS = new InternalActionWS(PROJECT_NAME);

        //Se imprime en los logs un diferenciador cada vez que inicia un escenario.Debe ir después del internalAction
        // ya que solo después de su instanciación la evidencia existe
        printInitLogs(testInfo);
        /*
        En caso de contar con alguna interfaz de reportería se debe agregar al reporteador con una clase
         creada por nosotros con esta interfaz implementada. Actualmente el proyecto base no cuenta con
         ninguna interfaz de reportería
         */
        ExtentReport extentReport = new ExtentReport();
        Report.setReporter(extentReport);
        searchController = new SearchController(internalActionWeb);
        shoppingController = new ShoppingController(internalActionWeb);
        dataController = new DataController(globalProps);
    }

    @After
    public void tearDown() {
        Assert.Soft.finish();
        //Se imprime en los logs un diferenciador cada vez que inicia un escenario
        printEndingLogs(testInfo);

        /*
        Se realizan todas las operaciones de cierre necesario sin importar el resultado del test
        (ej: finalizar los drivers instanciados)
         */
        internalActionWeb.closeBrowser();
        internalActionWeb.clearDriver();

    }


    @Given("^que quiero autenticarme en el portal de compras$")
    public void queQuieroAutenticarmeEnElPortalDeCompras() {
        String url = globalProps.getFieldValue("app.url");
        shoppingController.iniciarAplicacion(url, testInfo.getFeatureName());
    }

    @When("^ingreso mis credenciales de acceso$")
    public void ingresoMisCredencialesDeAcceso() {
        String user = globalProps.getFieldValue("web.user");
        String password = globalProps.getFieldValue("web.pass");
        shoppingController.autenticacion(user, password);
    }

    @Then("^ingreso a mi cuenta$")
    public void ingresoAMiCuenta() {
        shoppingController.verifySigIn();
    }

    @Given("^ingreso al portal para comprar en la e-shop para (.*?)$")
    public void ingresoAlPortalParaComprarEnLaEShopParaCasoDePrueba(String casoPrueba) {
        String url = globalProps.getFieldValue("app.url");
        searchController.iniciarAplicacion(url, testInfo.getFeatureName(), testInfo.getScenarioName());
    }


    @When("^ingreso el (.*?) para buscar por (.*?)$")
    public void ingresoElProductoParaBuscarPorCasoDePrueba(String producto, String casoPrueba) {
        searchController.buscarProducto(casoPrueba, producto);
    }

    @And("^verifico que el (.*?) exista para su eleccion$")
    public void verificoQueElProductoExistaParaSuEleccion(String producto) {
        searchController.verificarProducto(producto);
        searchController.elegirProducto(producto);
    }

    @And("^el (.*?) cuente con el precio unitario que registra en la base de datos$")
    public void elProductoCuenteConElPrecioUnitarioQueRegistraEnLaBaseDeDatos(String producto) {
        // Se sube el valor del precio unitario de la web como variable de clase para poder compartirlo con los otros steps
        unitPriceWeb = searchController.getPrecioUnitarioWeb();
        unitPriceDB = dataController.getUnitPriceDB(producto);
        dataController.compareUnitPrices(unitPriceWeb, unitPriceDB);
    }

    @And("^agrego (.*?) de unidades por (.*?) al carrito$")
    public void agregoCantidadDeVecesElProducto(int cantidad, String metodoAumento) {
        searchController.aumentarCantidad(cantidad, metodoAumento);
        searchController.agregarAlCarrito();
    }

    @Then("^entonces el detalle de la compra muestra el total calculado$")
    public void entoncesElDetalleDeLaCompraMuestraElTotalCalculado() {
        // Se sube el valor del precio total de la web como variable de clase para poder compartirlo con los otros steps
        totalPriceWeb = Float.parseFloat(searchController.getTotalPriceFromWeb());
    }

    @And("^es aproximado al mostrado por el (.*?) para la (.*?)$")
    public void esElMostradoPorElWebServiceCalculadora(String webServiceName, int cantidad) {
        // Se sube el valor del precio total del web service como variable de clase para poder compartirlo con los otros steps
        totalPriceWS = dataController.getTotalPriceWS(unitPriceWeb, cantidad, internalActionWS);
        dataController.compareTotalPrices(totalPriceWS, totalPriceWeb);

    }


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