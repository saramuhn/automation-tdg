package co.com.sofka.webProject.test.page;

import co.com.sofka.test.exceptions.WebActionsException;
import co.com.sofka.webProject.test.internalaction.InternalActionWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.DeleteSession;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    @FindBy(xpath = "//ul[@class='top-menu notmobile']//a[contains(text(),'Computers')]")
    WebElement headerComputers;
    @FindAll(@FindBy(xpath = "//h2[@class='title']//a"))
    List<WebElement> allCategorys;
    @FindBy(xpath = "//a[contains(@class,'viewmode-icon grid')]")
    WebElement iconGrid;
    @FindBy(xpath = "//a[contains(@class,'viewmode-icon list')]")
    WebElement iconList;
    @FindAll(@FindBy(xpath = "//span[@class='price actual-price']"))
    List<WebElement> allPrices;
    @FindBy(className = "PriceRange")
    WebElement textPriceRange;
    @FindBy(className = "product-rating-box")
    WebElement productRatingBox;
    @FindBy(className = "product-title")
    WebElement productTitle;
    @FindBy(className = "prices")
    WebElement priceActualProduct;

    WebElement optionCategory;
    WebElement textCategory;
    WebElement submenuCategory;
    WebElement filterPrice;


    private static final int DEFAULT_TIMEOUT = 50;
    private InternalActionWeb internalActionWeb;


    public HomePage(InternalActionWeb internalActionWeb) {
        PageFactory.initElements(internalActionWeb.getDriver(), this);
        this.internalActionWeb = internalActionWeb;
    }

    public void moveOnHeaderComputers() throws WebActionsException {
        internalActionWeb.moveTo(headerComputers, false);
    }

    public void clickOnHeaderComputers() throws WebActionsException {
        internalActionWeb.click(headerComputers, true);
    }

    public int getPositionAllCategorys() {
        return allCategorys.size();
    }

    public String searchDistinctCategorys(int position) {
        return allCategorys.get(position).getText();
    }

    public void selectCategory(String category) throws WebActionsException {
        submenuCategory = internalActionWeb.getDriver().findElement(By.xpath("//a[@href=\"/" + category + "\"]"));
        internalActionWeb.click(submenuCategory, true);
    }

    public void clickOnCategory(String category) throws WebActionsException {
        optionCategory = internalActionWeb.getDriver().findElement(By.xpath("//h2[@class='title']//a[contains(text(),'" + category + "')]"));
        internalActionWeb.click(optionCategory, DEFAULT_TIMEOUT, true);
    }

    public boolean getTextCategory(String category) throws WebActionsException {
        textCategory = internalActionWeb.getDriver().findElement(By.xpath("//h1[contains(text(),'" + category + "')]"));
        internalActionWeb.isVisible(textCategory, DEFAULT_TIMEOUT, false);
        if ((internalActionWeb.getText(textCategory, DEFAULT_TIMEOUT, true).equals(category)))
            return true;
        return false;
    }

    public boolean getViewModeGrid(String category) throws WebActionsException {
        internalActionWeb.moveTo(iconGrid, true);
        if (internalActionWeb.getText(iconGrid, DEFAULT_TIMEOUT, false).equals(category))
            return true;
        return false;
    }

    public boolean getViewModeList(String category) throws WebActionsException {
        internalActionWeb.moveTo(iconList, true);
        if (internalActionWeb.getText(iconList, DEFAULT_TIMEOUT, false).equals(category))
            return true;
        return false;
    }

    public void clickOnFilter(String filtro) throws WebActionsException {
        filterPrice = internalActionWeb.getDriver().findElement(By.xpath("//a[contains(@href,'" + filtro + "')]"));
        internalActionWeb.click(filterPrice, true);
    }

    public String getPrices(int position) {
        return allPrices.get(position).getText();
    }

    public int getPositionAllPrices() {
        return allPrices.size();
    }

    public String getTextPriceRange() throws WebActionsException {
        return internalActionWeb.getText(textPriceRange, true);
    }

    public boolean getTextCategoryInFilter(String category) throws WebActionsException {
        textCategory = internalActionWeb.getDriver().findElement(By.xpath("//h1[contains(text(),'" + category + "')]"));
        if (internalActionWeb.getText(textCategory, DEFAULT_TIMEOUT, true).equals(category))
            return true;
        return false;
    }
    public boolean getInformationProduct(){
        boolean result=internalActionWeb.isVisible(productTitle,true);
        result=result &&internalActionWeb.isVisible(priceActualProduct,true);
        result=result &&internalActionWeb.isVisible(productRatingBox,true);
        return result;

    }
}
