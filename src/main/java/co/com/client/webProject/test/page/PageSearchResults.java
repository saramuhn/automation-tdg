package co.com.client.webProject.test.page;

import co.com.client.webProject.test.internalaction.InternalActionWeb;
import co.com.sofka.test.exceptions.WebActionsException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class PageSearchResults {

    @FindBy(xpath = "//*[contains(@class,'product_list grid row')]//*[contains(@class,'product-name')]")
    private List<WebElement> lblProductNames;

    private static final int DEFAULT_TIMEOUT = 30;
    private InternalActionWeb internalActionWeb;

    public PageSearchResults(InternalActionWeb internalActionWeb) {
        PageFactory.initElements(internalActionWeb.getDriver(), this);
        this.internalActionWeb = internalActionWeb;
    }

    public boolean isPresente(String nombreProducto) throws WebActionsException {
        for (WebElement lblProductName : lblProductNames) {
            String textoProducto = internalActionWeb.getText(lblProductName, DEFAULT_TIMEOUT, true);
            if (nombreProducto.equals(textoProducto)) {
                return true;
            }
        }
        return false;
    }

    public void elegirProducto(String nombreProducto) throws WebActionsException {
        for (WebElement lblProductName : lblProductNames) {
            String textoProducto = internalActionWeb.getText(lblProductName, DEFAULT_TIMEOUT, false);
            if (nombreProducto.equals(textoProducto)) {
                internalActionWeb.click(lblProductName, DEFAULT_TIMEOUT, true);
                return;
            }
        }
        throw new WebActionsException("Elemento no encontrado.");
    }
}
