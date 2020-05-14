package co.com.client.webProject.test.controllers;

import co.com.client.webProject.test.helpers.InternalData;
import co.com.client.webProject.test.internalaction.InternalActionWS;
import co.com.sofka.test.evidence.reports.Assert;
import co.com.sofka.test.evidence.reports.Report;
import co.com.sofka.test.exceptions.ServiceActionsException;
import co.com.sofka.test.utils.files.PropertiesFile;
import org.assertj.core.data.Percentage;

import java.sql.SQLException;

public class DataController {
    private static final String PRICE_REGEX = "^[0-9]*(\\.[0-9]*)?$";
    private final String MSG_SUCCESS = "Se ha realizado exitosamente %s en la e-Shop.";
    private final String MSG_ERROR = "Ocurrió un error realizando %s  en la e-Shop.";
    private PropertiesFile propertiesFile;

    public DataController(PropertiesFile propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    InternalData internalData = new InternalData(propertiesFile);

    public float getTotalPriceWS(float precioUnitario, int cantidad, InternalActionWS internalActionWS) {
        final String ACCION = "la OBTENCION DEL PRECIO TOTAL DEL WS";
        try {
            String precioTotalFromWS = internalData.multiplicarNumeros((int) precioUnitario, cantidad, internalActionWS);
            Assert.Hard.thatIsTrue("El precio obtenido no es válido.",
                    precioTotalFromWS.matches(PRICE_REGEX));
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
            return Float.parseFloat(precioTotalFromWS);
        } catch (ServiceActionsException e) {
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
            return '0';
        }
    }

    public float getUnitPriceDB(String productName) {
        final String ACCION = "la OBTENCION DEL PRECIO TOTAL DE LA DB";
        try {
            float precioTotalFromWS = internalData.consultarPrecioProducto(productName);
            Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
            return precioTotalFromWS;
        } catch (SQLException e) {
            Report.reportFailure(String.format(MSG_ERROR, ACCION),e);
            return '0';
        }
    }

    public void compareTotalPrices(float totalPriceWS, float totalPriceWeb) {
        final String ACCION = "la COMPARACION DE PRECIOS TOTALES";
        /*Se usa esta aserción porque que el dato obtenido del web service puede desfazarse en un 4%; esto debido a  que
        antes del cálculo se debe castear el precio unitario a entero (pierde los decimales y por tanto no se obtendrá el valor exacto)*/
        Assert.Hard.thatFloat(totalPriceWeb)
                .withFailMessage("Los precios totales no son cercanos. " +
                        "Precio Web: ".concat(String.valueOf(totalPriceWeb))
                                .concat(", ") +
                        "Precio WS: ".concat(String.valueOf(totalPriceWS)))
                .isCloseTo(totalPriceWS, Percentage.withPercentage(4));
        Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
    }

    public void compareUnitPrices(float unitPriceWeb, float unitPriceBD) {
        final String ACCION = "la COMPARACION DE PRECIOS UNITARIOS";
        final String ERROR_MESSAGE = "Los precios unitarios no son iguales. " +
                "Precio Web: ".concat(String.valueOf(unitPriceWeb))
                        .concat(", ") +
                "Precio BD: ".concat(String.valueOf(unitPriceBD));
        Assert.Hard.thatFloat(unitPriceWeb)
                .withFailMessage(ERROR_MESSAGE)
                .isEqualTo(unitPriceBD);
        Report.reportSuccess(String.format(MSG_SUCCESS, ACCION));
    }
}
