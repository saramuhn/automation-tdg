package co.com.client.webProject.test.helpers;

public class Templates {
    public static String getRequestMultiplicacion(String numero1, String numero2){
        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <tem:Multiply>\n" +
                "         <tem:intA>%s</tem:intA>\n" +
                "         <tem:intB>%s</tem:intB>\n" +
                "      </tem:Multiply>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return String.format(request,numero1,numero2);
    }

    public static String getQueryPrecioProducto(String productName){
        String query = "SELECT product_price FROM PRODUCTS WHERE product_name='%s'";
        return String.format(query,productName);
    }
}
