package co.com.client.desktopProject.test.controllers;

import co.com.client.desktopProject.test.page.PageCalculator;
import co.com.client.desktopProject.test.stepdefinition.StepsDefinition;
import co.com.sofka.test.automationtools.selenium.Browser;
import co.com.sofka.test.automationtools.winnium.Desktop;
import co.com.sofka.test.evidence.reports.Assert;
import co.com.sofka.test.evidence.reports.Report;
import co.com.sofka.test.exceptions.DesktopActionsException;
import co.com.sofka.test.exceptions.WebActionsException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static co.com.client.desktopProject.test.stepdefinition.StepsDefinition.internalActionDesk;

public class CalculatorController {
    private final String MSG_SUCCESS = "Se ha realizado exitosamente %s en la calculadora.";
    private final String MSG_ERROR = "Ocurrió un error realizando %s  en la calculadora.";

    public void iniciarAplicacion(String feature) {
        final String ACCION = "la INICIACIÓN DE LA APP";
        Desktop desktopConfig = new Desktop();
        Path path = Paths.get(System.getenv("WINDIR")).resolve("System32");
        desktopConfig.setAppFolder(path.toString());
        desktopConfig.setAppName("calc.exe");
        try {
            internalActionDesk.startDesktopApp(desktopConfig, feature);
            Report.reportScreenshot(internalActionDesk);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (DesktopActionsException e) {
            Report.reportScreenshot(internalActionDesk);
            Report.reportFailure(String.format(MSG_ERROR, ACCION), e);
        }

    }

    public void ingresarDatos(String operacion, String numero1, String numero2) {
        final String ACCION = "el INGRESO DE DATOS";
        try {
            PageCalculator pageCalculator = new PageCalculator();
            pageCalculator.ingresarNumero1(numero1);
            pageCalculator.ingresarOperacion(operacion);
            pageCalculator.ingresarNumero2(numero2);
            Report.reportScreenshot(internalActionDesk);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
        } catch (DesktopActionsException e) {
            Report.reportScreenshot(internalActionDesk);
            Report.reportFailure(String.format(MSG_ERROR, ACCION), e);
        }

    }

    public String obtenerResultado() {
        final String ACCION = "la OBTENCIÓN DE RESULTADOS";
        try {
            final String RESULT_REGEX = "^Se muestra (\\d*)$";
            PageCalculator pageCalculator = new PageCalculator();
            String textBoxResult = pageCalculator.obtenerResultado();
            Matcher resultMatcher = Pattern.compile(RESULT_REGEX).matcher(textBoxResult);
            if (resultMatcher.find()) {
                String result = resultMatcher.group(1);
                Report.Evidence.log("El resultado de la operación es: ".concat(result));
                Report.reportScreenshot(internalActionDesk);
                Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
                return result;
            } else {
                throw new NoSuchElementException("No se pudo extraer el resultado del elemento.");
            }
        } catch (DesktopActionsException e) {
            Report.reportScreenshot(internalActionDesk);
            Report.reportFailure(String.format(MSG_ERROR, ACCION), e);
            return null;
        }
    }

    public void compararResultados(String resultadoEsperado, String resultadoObtenido) {
        final String ACCION = "la COMPARACION DE LOS RESULTADOS";
        final String ERROR_MESSAGE = "Los resultados no son iguales. " +
                "Resultado esperado: ".concat(String.valueOf(resultadoEsperado))
                        .concat(", ") +
                "Resultado obtenido: ".concat(String.valueOf(resultadoObtenido));
        Assert.Hard.thatIsEqual(ERROR_MESSAGE, resultadoEsperado, resultadoObtenido);
        Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
    }
}
