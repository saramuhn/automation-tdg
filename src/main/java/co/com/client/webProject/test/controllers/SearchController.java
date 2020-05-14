package co.com.client.webProject.test.controllers;

import co.com.client.webProject.test.helpers.Dictionary.*;
import co.com.client.webProject.test.internalaction.InternalActionWeb;
import co.com.client.webProject.test.page.PageIndex;
import co.com.client.webProject.test.page.PageProducto;
import co.com.client.webProject.test.page.PageSearchResults;
import co.com.sofka.test.automationtools.selenium.Browser;
import co.com.sofka.test.evidence.reports.Assert;
import co.com.sofka.test.evidence.reports.Report;
import co.com.sofka.test.exceptions.WebActionsException;
import org.apache.commons.lang3.StringUtils;

public class SearchController {

    private static final String PRICE_REGEX = "-?\\d+(\\.\\d+)?";
    private final String MSG_SUCCESS = "Se ha realizado exitosamente %s en la e-Shop.";
    private final String MSG_ERROR = "Ocurrió un error realizando %s  en la e-Shop.";
    private InternalActionWeb internalActionWeb;

    public SearchController(InternalActionWeb internalActionWeb) {
        this.internalActionWeb = internalActionWeb;
    }

    public void iniciarAplicacion(String url, String feature, String scenario) {
        final String ACCION = "la INICIACIÓN DEL PORTAL";
        String validScenario = scenario.replace(" ", "_");
        String validFeature = feature.replace(" ", "_");
        Browser browser = new Browser();
        browser.setBrowser(Browser.Browsers.CHROME);
        browser.setMaximized(true);
        browser.setIncognito(true);
        browser.setDriverVersion("72.0.3626.69");
        browser.setAutoDriverDownload(true);
    /*
        Si se requiere usar un driver en alguna ubicación se usa: (Si se desea que se descargue automático se deja como está)
            browser.setAutoDriverDownload(false);
            Path directorioDriver = Paths.get("c:/midirectoriodedriver")
            browser.setDriverPath(directorioDriver);
        Si se requiere usar grid de webdrivers remotos se agregan:
            browser.setIsRemote(true);
            browser.setHubURL("http://localhost:4444/wd/hub");
        Si se requiere usar headless usar (se debe cambiar el tamaño de la ventana si se usa este modo):
            browser.setHeadless(true);
     */
        try {
            internalActionWeb.startWebApp(browser, url, validFeature, validScenario);
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
        }
    }

    public void buscarProducto(String casoDePrueba, String producto) {
        final String ACCION = "la BUSQUEDA DEL PRODUCTO por ".concat(casoDePrueba);
        try {
            PageIndex pageIndex = new PageIndex(internalActionWeb);
            if (casoDePrueba.equals(Escenarios.POR_BUSCADOR)) {
                pageIndex.ingresoProductoPorBuscador(producto);
            }
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
        }
    }

    public void verificarProducto(String producto) {
        final String ACCION = "la VERIFICACIÓN DEL PRODUCTO";
        try {
            PageSearchResults pageSearchResults = new PageSearchResults(internalActionWeb);
            boolean isPresente = pageSearchResults.isPresente(producto);
            Assert.Hard.thatIsTrue("No se encontró el producto,", isPresente);
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
        }
    }

    public void elegirProducto(String producto) {
        final String ACCION = "la ELECCIÓN DEL PRODUCTO";
        try {
            PageSearchResults pageSearchResults = new PageSearchResults(internalActionWeb);
            pageSearchResults.elegirProducto(producto);
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
        }
    }

    public void aumentarCantidad(int cantidad, String metodoAumento) {
        final String ACCION = "el INCREMENTO EN LA CANTIDAD DEL PRODUCTO";
        try {
            PageProducto pageProducto = new PageProducto(internalActionWeb);
            pageProducto.aumentarProductos(cantidad, metodoAumento);
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
        }
    }

    public void agregarAlCarrito() {
        final String ACCION = "el AGREGADO DEL PRODUCTO AL CARRITO";
        try {
            PageProducto pageProducto = new PageProducto(internalActionWeb);
            pageProducto.agregarAlCarrito();
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
        }
    }

    public String getTotalPriceFromWeb() {
        final String ACCION = "la OBTENCION DEL PRECIO TOTAL DEL PRODUCTO";
        try {
            PageProducto pageProducto = new PageProducto(internalActionWeb);
            String totalPrice = pageProducto.getTotalPrice().replace("$", "");
            final String ERROR_MESSAGE = "El precio '".concat(totalPrice).concat("' no es un precio válido.");
            Report.Evidence.log("Precio Total en Web: ".concat(totalPrice));
            //Verifico que el precio no venga vacío
            Assert.Hard.thatIsTrue(ERROR_MESSAGE, StringUtils.isNotBlank(totalPrice));
            //Verifico que el precio si sea un número
            Assert.Hard.thatIsTrue(ERROR_MESSAGE, totalPrice.matches(PRICE_REGEX));
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
            return totalPrice;

        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
            return null;
        }
    }

    public float getPrecioUnitarioWeb() {
        final String ACCION = "la OBTENCION DEL PRECIO UNITARIO DEL PRODUCTO";
        try {
            PageProducto pageProducto = new PageProducto(internalActionWeb);
            String unitPrice = pageProducto.getUnitPrice().replace("$", "");
            final String ERROR_MESSAGE = "El precio '".concat(unitPrice).concat("' no es un precio válido.");
            Report.Evidence.log("Precio unitario en Web: ".concat(unitPrice));
            //Verifico que el precio no venga vacío
            Assert.Hard.thatIsTrue(ERROR_MESSAGE, StringUtils.isNotBlank(unitPrice));
            //Verifico que el precio si sea un número
            Assert.Hard.thatIsTrue(ERROR_MESSAGE, unitPrice.matches(PRICE_REGEX));

            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
            return Float.parseFloat(unitPrice);

        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
            return 0;
        }
    }
}
