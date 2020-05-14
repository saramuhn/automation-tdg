package co.com.client.webProject.test.page;

import co.com.client.webProject.test.internalaction.InternalActionWeb;
import co.com.sofka.test.exceptions.WebActionsException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageIndex {

    @FindBy(className = "login")
    private WebElement lnkSingIn;

    @FindBy(id = "search_query_top")
    private WebElement txtSearch;

    @FindBy(name = "submit_search")
    private WebElement btnSubmitSearch;

    private static final int DEFAULT_TIMEOUT = 30;
    private InternalActionWeb internalActionWeb;

    public PageIndex(InternalActionWeb internalActionWeb) {
        PageFactory.initElements(internalActionWeb.getDriver(), this);
        this.internalActionWeb = internalActionWeb;
    }

    public void ingresoAutenticacion() throws WebActionsException {
        internalActionWeb.click(lnkSingIn,DEFAULT_TIMEOUT, true);
    }

    public void ingresoProductoPorBuscador(String producto) throws WebActionsException {
        internalActionWeb.sendText(txtSearch, producto, DEFAULT_TIMEOUT,true);
        internalActionWeb.click(btnSubmitSearch,DEFAULT_TIMEOUT, true);
    }
}
