package co.com.client.webProject.test.controllers;

import co.com.client.webProject.test.internalaction.InternalActionWeb;
import co.com.client.webProject.test.page.PageAuthencation;
import co.com.client.webProject.test.page.PageIndex;
import co.com.client.webProject.test.page.PageMyAccount;
import co.com.sofka.test.automationtools.selenium.Browser;
import co.com.sofka.test.evidence.reports.Assert;
import co.com.sofka.test.evidence.reports.Report;
import co.com.sofka.test.exceptions.WebActionsException;

public class ShoppingController {

    private final String MSG_SUCCESS = "Se ha realizado exitosamente %s en la e-Shop.";
    private final String MSG_ERROR = "Ocurrió un error realizando %s  en la e-Shop.";
    private InternalActionWeb internalActionWeb;

    public ShoppingController(InternalActionWeb internalActionWeb) {
        this.internalActionWeb = internalActionWeb;
    }

    public void iniciarAplicacion(String url, String feature) {
        final String ACCION = "la INICIACIÓN DEL PORTAL";
        Browser browser = new Browser();
        browser.setBrowser(Browser.Browsers.CHROME);
        browser.setMaximized(true);
        browser.setIncognito(true);
        browser.setAutoDriverDownload(true);
        try {
            internalActionWeb.startWebApp(browser, url, feature);
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
        }

    }

    public void autenticacion(String email, String password) {
        final String ACCION = "la AUTENTICACIÓN DE USUARIO";
        PageIndex pageIndex = new PageIndex(internalActionWeb);
        PageAuthencation pageAuthencation = new PageAuthencation(internalActionWeb);
        try {
            pageIndex.ingresoAutenticacion();
            pageAuthencation.singIn(email, password);
            Report.reportScreenshot(internalActionWeb);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (WebActionsException e) {
            Report.reportScreenshot(internalActionWeb);
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
        }
    }

    public void verifySigIn() {
        final String ACCION = "la VERIFICACIÓN DEL ACCESO DE CUENTA";
        PageMyAccount pageMyAccount = new PageMyAccount(internalActionWeb);
        boolean result = pageMyAccount.verifyElementMyAccount();
        Assert.Hard.thatIsTrue(result);
        Report.reportScreenshot(internalActionWeb);
        Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
    }
}
