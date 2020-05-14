package co.com.sofka.webProject.test.controllers;

import co.com.client.webProject.test.page.PageMyAccount;
import co.com.sofka.test.evidence.reports.Assert;
import co.com.sofka.webProject.test.internalaction.InternalActionWeb;
import co.com.sofka.test.automationtools.selenium.Browser;
import co.com.sofka.test.evidence.reports.Report;
import co.com.sofka.test.exceptions.WebActionsException;
import co.com.sofka.webProject.test.page.HomePage;
import co.com.sofka.webProject.test.page.PageCategory;

import java.util.ArrayList;
import java.util.List;


public class DisplaycomputersController {
    private final String MSG_SUCCESS = "Se ha realizado exitosamente %s en Nop-Commerce.";
    private final String MSG_ERROR = "Ocurrió un error realizando %s  en Nop-Commerce.";
    private final String VISTA_CUADROS = "Grid";
    private final String VISTA_LISTAS = "List";
    private static final int FILTRO_STANDAR_LOW = 1000;
    private static final int FILTRO_STANDAR_HIGH = 1200;
    private static final String TEXT_FILTRO_STANDAR_LOW = "$1,000.00";
    private static final String TEXT_FILTRO_STANDAR_HIGH = "$1,200.00";
    private static final String WISHLIST = "wishlist";
    private static final String ADD_TO_CART = "add to cart";
    private static final String TEXT_WISHLIST = "Wishlist";
    private static final String TEXT_ADD_TO_CART = "Shopping cart";
    private InternalActionWeb internalActionWeb;
    private HomePage homePage;
    private PageCategory pageCategory;
    private String titleProduct;

    public DisplaycomputersController(InternalActionWeb internalActionWeb) {
        this.internalActionWeb = internalActionWeb;
    }

    public void iniciarAplicacion(String url, String feature, String scenario) {
        final String ACCION = "La iniciación del Portal";
        String validScenario = scenario.replace(" ", "_");
        String validFeature = feature.replace(" ", "_");
        Browser browser = new Browser();
        browser.setBrowser(Browser.Browsers.CHROME);
        browser.setMaximized(true);
        browser.setIncognito(true);
        browser.setDriverVersion("72.0.3626.69");
        browser.setAutoDriverDownload(true);

        try {
            internalActionWeb.startWebApp(browser, url, validFeature, validScenario);
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION), e);
        }
    }

    public void mostrarCategorias() throws WebActionsException {
        homePage = new HomePage(internalActionWeb);
        final String ACCION = "Mostrar categorias de Computers ";
        try {
            homePage.moveOnHeaderComputers();
            homePage.clickOnHeaderComputers();
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION), e);
        }
    }

    public void buscarCategoriaSubMenu(String categoria) {
        final String ACCION = "Busqueda de categoria ".concat(categoria).concat(" en SubMenu ");
        homePage = new HomePage(internalActionWeb);
        try {
            homePage.moveOnHeaderComputers();
            homePage.selectCategory(categoria);
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION), e);
        }
    }

    public void buscarCategoriaInFrame(String categoria) {
        final String ACCION = "Busqueda y validacion de ".concat(categoria).concat("en la pantalla");
        homePage = new HomePage(internalActionWeb);
        boolean result;
        try {
            for (int i = 0; i < homePage.getPositionAllCategorys(); i++) {
                if (homePage.searchDistinctCategorys(i).equals(categoria))
                    homePage.clickOnCategory(categoria);
            }
            result = homePage.getTextCategory(categoria);
            Assert.Hard.thatIsTrue(result);
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION), e);
        }
    }

    public void validarOpcionMuestraCategorias() throws WebActionsException {
        final String ACCION = "Verificacion de mostrar la categoria por Cuadros y Listas";
        homePage = new HomePage(internalActionWeb);
        boolean result = homePage.getViewModeGrid(VISTA_CUADROS) && homePage.getViewModeList(VISTA_LISTAS);
        Assert.Hard.thatIsTrue(result);
        Report.reportScreenshot(internalActionWeb);
        Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
    }

    public void obtenerPrecios(String filtro, String categoria) throws WebActionsException {
        final String ACCION = "la Obtencion de precios de los productos de la ".concat(categoria).concat("con el filtro ".concat(filtro));
        String priceProduct;
        double price;
        int contadorProductos = 0;
        boolean result = true;
        boolean resultCategory = false;
        homePage = new HomePage(internalActionWeb);
        resultCategory = homePage.getTextCategoryInFilter(categoria);
        for (int i = 0; i < homePage.getPositionAllPrices(); i++) {
            priceProduct = homePage.getPrices(i);
            priceProduct = priceProduct.substring(1);
            priceProduct = priceProduct.replaceAll(",", "");
            price = Double.parseDouble(priceProduct);
            if (price < FILTRO_STANDAR_LOW) {
                contadorProductos++;
                result = result && true;
            } else if (price >= FILTRO_STANDAR_LOW && price < FILTRO_STANDAR_HIGH) {
                contadorProductos++;
                result = result && true;
            } else if (price >= FILTRO_STANDAR_HIGH) {
                contadorProductos++;
                result = result && true;
            } else result = result && false;
        }
        if (contadorProductos == homePage.getPositionAllPrices() && result == true)
            result = true;
        else
            result = false;
        result = result && resultCategory;
        Assert.Hard.thatIsTrue(result);
        Report.reportScreenshot(internalActionWeb);
        Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
    }

    public void seleccionarFiltro(String filtro) throws WebActionsException {
        final String ACCION = "la seleccion del filtro: ".concat(filtro);
        homePage = new HomePage(internalActionWeb);
        String price = homePage.getTextPriceRange();
        boolean result = false;
        try {
            homePage.clickOnFilter(filtro);

            if (price.equals(TEXT_FILTRO_STANDAR_LOW) || price.equals(TEXT_FILTRO_STANDAR_HIGH))
                result = true;
            Assert.Hard.thatIsTrue(result);
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION), e);
        }
    }

    public void resumenDelProducto() {
        final String ACCION = "el resumen del producto";
        homePage = new HomePage(internalActionWeb);
        boolean result = homePage.getInformationProduct();
        Assert.Hard.thatIsTrue(result);
        Report.reportScreenshot(internalActionWeb);
        Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
    }

    public void buscarProductoCategoria(String categoria) {
        final String ACCION = "la busqueda de un producto en la categoria: ".concat(categoria);
        pageCategory = new PageCategory(internalActionWeb);
        try {
            pageCategory.selectCategoryFromMain(categoria);
            titleProduct = pageCategory.getTitleProduct();
            pageCategory.selectProduct();
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION), e);
        }
    }

    public void cambiarCantidad(String cantidad) throws WebActionsException {
        final String ACCION = "cambiar cantidad del producto";
        try {
            pageCategory.changeQuantity(cantidad);
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION), e);
        }
    }

    public void agregarMedio(String medio) throws WebActionsException {
        final String ACCION = "agregar medio el producto";
        try {
            if (medio.equals(WISHLIST))
                pageCategory.chooseButtonWishList();
            else if (medio.equals(ADD_TO_CART))
                pageCategory.chooseButtonAddToCart();
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION), e);
        }
    }

    public void validarTituloMedio(String medio) throws WebActionsException {
        final String ACCION = "la validacion del titulo del medio";
        String tituloMedio = pageCategory.getTextMedio();
        boolean result = false;
        if (medio.equals(WISHLIST) && tituloMedio.equals(TEXT_WISHLIST))
            result = true;
        else if (medio.equals(ADD_TO_CART) && tituloMedio.equals(TEXT_ADD_TO_CART))
            result = true;
        Assert.Hard.thatIsTrue(result);
        Report.reportScreenshot(internalActionWeb);
        Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
    }

    public void validarDetalleProducto(String medio, String cantidad) throws WebActionsException {
        final String ACCION = "la validacion del detalle del producto";
        List<String> tituloProductoMedio = new ArrayList<>();
        tituloProductoMedio = pageCategory.getTextProductMedio();
        boolean result = false;
        int cantidadMedio = pageCategory.getQuantity();
        for (int i = 0; i < tituloProductoMedio.size(); i++) {
            if (tituloProductoMedio.get(i).equals(titleProduct) && (Integer.parseInt(cantidad)) == cantidadMedio)
                result = true;
        }
        Assert.Hard.thatIsTrue(result);
        Report.reportScreenshot(internalActionWeb);
        Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
    }
}


