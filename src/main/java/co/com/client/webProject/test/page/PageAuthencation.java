package co.com.client.webProject.test.page;


import co.com.client.webProject.test.internalaction.InternalActionWeb;
import co.com.sofka.test.exceptions.WebActionsException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class PageAuthencation {

    @FindBy(id = "email")
    private WebElement inputEmail;

    @FindBy(id = "passwd")
    private WebElement inputPassword;

    @FindBy(id = "SubmitLogin")
    private WebElement btnSignIn;

    private static final int DEFAULT_TIMEOUT = 30;
    private InternalActionWeb internalActionWeb;

    public PageAuthencation(InternalActionWeb internalActionWeb) {
        PageFactory.initElements(internalActionWeb.getDriver(), this);
        this.internalActionWeb = internalActionWeb;
    }

    public void singIn(String email, String password) throws WebActionsException {
        internalActionWeb.sendText(inputEmail, email, DEFAULT_TIMEOUT, true);
        internalActionWeb.sendText(inputPassword, password, DEFAULT_TIMEOUT, true);
        internalActionWeb.click(btnSignIn, DEFAULT_TIMEOUT, true);

    }
}
