package co.com.client.desktopProject.test.helpers;

import co.com.sofka.test.utils.Time;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Templates {
    public static String getRequestMultiplicacion(final String numero1, final String numero2) {
        final String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <tem:Multiply>\n" +
                "         <tem:intA>%s</tem:intA>\n" +
                "         <tem:intB>%s</tem:intB>\n" +
                "      </tem:Multiply>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return String.format(request, numero1, numero2);
    }

    public static String getQueryPrecioProducto(final String productName) {
        final String query = "SELECT product_price FROM PRODUCTS WHERE product_name='%s'";
        return String.format(query, productName);

    }

}

