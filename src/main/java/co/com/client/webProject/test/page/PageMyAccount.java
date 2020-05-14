package co.com.client.webProject.test.page;

import co.com.client.webProject.test.internalaction.InternalActionWeb;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageMyAccount {

    @FindBy(className = "page-heading")
    private WebElement lblMyAccount;

    private static final int DEFAULT_TIMEOUT = 30;
    private InternalActionWeb internalActionWeb;

    public PageMyAccount(InternalActionWeb internalActionWeb) {
        PageFactory.initElements(internalActionWeb.getDriver(), this);
        this.internalActionWeb = internalActionWeb;
    }

    public boolean verifyElementMyAccount() {
        return internalActionWeb.isVisible(lblMyAccount, DEFAULT_TIMEOUT, true);
    }
}
