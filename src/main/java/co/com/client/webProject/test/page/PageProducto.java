package co.com.client.webProject.test.page;

import co.com.client.webProject.test.internalaction.InternalActionWeb;
import co.com.sofka.test.exceptions.WebActionsException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static co.com.client.webProject.test.helpers.Dictionary.*;

public class PageProducto {

    @FindBy(xpath = "//*[contains(@class,'product_quantity_up')]")
    private WebElement btnQuantityUp;

    @FindBy(name = "Submit")
    private WebElement btnAddToCart;

    @FindBy(id = "quantity_wanted")
    private WebElement txtQuantity;

    @FindBy(className = "ajax_block_products_total")
    private WebElement lblProductsTotal;

    @FindBy(id = "our_price_display")
    private WebElement lblProductUnit;

    @FindBy(id = "reduction_percent")
    private WebElement lbltest;

    private static final int DEFAULT_TIMEOUT = 30;
    private InternalActionWeb internalActionWeb;

    public PageProducto(InternalActionWeb internalActionWeb) {
        PageFactory.initElements(internalActionWeb.getDriver(), this);
        this.internalActionWeb = internalActionWeb;
    }

    public void aumentarProductos(int cantidad, String metodoAumento) throws WebActionsException {
        internalActionWeb.waitUntilExist(lbltest,DEFAULT_TIMEOUT,true);
        if (MetodosAumento.BOTON.equals(metodoAumento)) {
                for (int i = 1; i < cantidad; i++)
                    internalActionWeb.click(btnQuantityUp,DEFAULT_TIMEOUT, true);
        }else if(MetodosAumento.TEXTO.equals(metodoAumento)){
            internalActionWeb.clearText(txtQuantity,DEFAULT_TIMEOUT,false);
            internalActionWeb.sendText(txtQuantity, String.valueOf(cantidad),DEFAULT_TIMEOUT, true);
        }else{
            throw new WebActionsException("Metodo de ingreso de cantidad no válido.");
        }
    }

    public void agregarAlCarrito() throws WebActionsException {
        internalActionWeb.click(btnAddToCart,DEFAULT_TIMEOUT, true);
    }


    public String getUnitPrice() throws WebActionsException {
        return internalActionWeb.getText(lblProductUnit,DEFAULT_TIMEOUT, true);
    }

    public String getTotalPrice() throws WebActionsException {
        return internalActionWeb.getText(lblProductsTotal,DEFAULT_TIMEOUT, true);
    }
}
