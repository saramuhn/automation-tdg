package co.com.client.webProject.test.helpers;

import co.com.client.webProject.test.internalaction.InternalActionWS;
import co.com.sofka.test.actions.ServiceAction.*;
import co.com.sofka.test.automationtools.databases.relational.ConnectionSQLite;
import co.com.sofka.test.automationtools.webservices.api.WebServiceClient;
import co.com.sofka.test.automationtools.webservices.api.dataTransferObject.ServiceResponse;
import co.com.sofka.test.automationtools.webservices.api.helpers.Headers;
import co.com.sofka.test.automationtools.webservices.api.helpers.UtilsWebServices;
import co.com.sofka.test.evidence.reports.Report;
import co.com.sofka.test.exceptions.ServiceActionsException;
import co.com.sofka.test.utils.files.PropertiesFile;
import org.openqa.selenium.NotFoundException;

import javax.sql.rowset.CachedRowSet;

import java.sql.SQLException;
import java.util.Properties;

public class InternalData {

    private final String MSG_SUCCESS_WS = "Se ha ejecutado exitosamente el WS '%s'.";
    private final String MSG_ERROR_WS = "Ocurrió un error ejecutando el WS '%s'.";
    private final String MSG_SUCCESS_BD = "Se ha ejecutado exitosamente el query para %s en la DB %s.";
    private final String MSG_ERROR_BD = "Ocurrió un error ejecutando el query tipo %s en la DB %s.";
    private final String INFO_QUERY_DB = "|-------------------- QUERY DB '%s' --------------------|\n %s";
    private final String INFO_END_POINT_WS = "|-------------------- ENDPOINT WS '%s' --------------------|\n %s";
    private final String INFO_REQUEST_WS = "|-------------------- REQUEST WS '%s' --------------------|\n %s";
    private final String INFO_RESPUESTA_WS = "|-------------------- RESPUESTA WS '%s' --------------------|\n %s";
    private PropertiesFile properties;

    public InternalData(PropertiesFile properties) {
        this.properties = properties;
    }

    public String multiplicarNumeros(int numero1, int numero2, InternalActionWS internalActionWS) throws ServiceActionsException {
        final String OPERATION = "OBTENER RESULTADO MULTIPLICACION";
        String request = Templates.getRequestMultiplicacion(String.valueOf(numero1), String.valueOf(numero2));
        WebServiceClient clienteSoap = internalActionWS.getWebServicesClient(ServiceType.SOAP);
        String endPoint = properties.getFieldValue("calculator.endpoint");
        Report.Evidence.log(String.format(INFO_REQUEST_WS, OPERATION, request));
        Report.Evidence.log(String.format(INFO_END_POINT_WS, OPERATION, endPoint));
        Headers managementHeaders = new Headers();
        managementHeaders.addHeader("Content-Type", "text/xml");
        ServiceResponse response = clienteSoap.callWebService(endPoint, request, managementHeaders.getAllHeaders());
        Report.Evidence.log(String.format(MSG_SUCCESS_WS, OPERATION));
        Report.Evidence.log(String.format(INFO_RESPUESTA_WS, OPERATION, response.toString()));

        return UtilsWebServices.XML.extractValueTag("MultiplyResult", response.getBody());
    }

    public float consultarPrecioProducto(String productName) throws SQLException {
        final String DB_CONNECTION = "SQLITE_PRUEBAS";
        final String ACTION = "CONSULTAR PRECIO DE PRODUCTO BD";
        String query = Templates.getQueryPrecioProducto(productName);
        Report.Evidence.log(String.format(INFO_QUERY_DB, ACTION, query));
        try (ConnectionSQLite connection = new ConnectionSQLite("sqliteProducts")) {
            CachedRowSet resultSet = connection.executeQuery(query);
            if (resultSet.next()) {
                Report.Evidence.log(String.format(MSG_SUCCESS_BD, ACTION, DB_CONNECTION));
                return resultSet.getFloat("product_price");
            }
        }
        throw new NotFoundException("No se encontraron datos para el producto: '"
                .concat(productName)
                .concat("'."));
    }



}
