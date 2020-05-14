package co.com.sofka.webProject.test.page;

import co.com.sofka.test.exceptions.WebActionsException;
import co.com.sofka.webProject.test.internalaction.InternalActionWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class PageCategory {
    private static final int DEFAULT_TIMEOUT = 30;
    private InternalActionWeb internalActionWeb;

    public PageCategory(InternalActionWeb internalActionWeb) {
        PageFactory.initElements(internalActionWeb.getDriver(), this);
        this.internalActionWeb = internalActionWeb;
    }

    @FindBy(xpath = "//div[@class='item-box'][2]//h2[@class='product-title']//a")
    WebElement selectProduct;
    @FindBy(xpath = "//input[contains(@class,'qty-input')]")
            WebElement inputQuantity;
    @FindBy(xpath = "//input[contains(@class,'add-to-wishlist-button')]")
            WebElement btnWishlist;
    @FindBy(xpath = "//input[contains(@class,'add-to-cart-button')]")
            WebElement btnAddToCart;
    @FindBy(xpath = "//p//a[@href='/cart']")
            WebElement viewShoppingCart;
    @FindBy(xpath = "//p//a[@href='/wishlist']")
            WebElement viewWishlist;
    @FindAll(@FindBy(xpath = "//td[@class='product']//a"))
    List<WebElement> textProduct;

    @FindBy(xpath = "//input[@class='qty-input']")
            WebElement textQuantity;

    @FindBy(className = "product-unit-price")
            WebElement textPriceUnit;
    @FindBy(className = "product-subtotal")
            WebElement textPriceTotal;

    WebElement selectCategory;
    WebElement textMedio;


    public void selectCategoryFromMain(String category) throws WebActionsException {
        selectCategory = internalActionWeb.getDriver().findElement(By.xpath("//h2[@class='title']//a[contains(text(),'" + category + "')]"));
        internalActionWeb.click(selectCategory, true);
    }
    public void selectProduct() throws WebActionsException {
        internalActionWeb.click(selectProduct,true);
    }
    public void changeQuantity(String quantity) throws WebActionsException {
        internalActionWeb.clearText(inputQuantity,false);//div[@class='item-box'][2]//div[@class='description']
        internalActionWeb.sendText(inputQuantity,quantity,true);
    }
    public void chooseButtonWishList() throws WebActionsException {
        internalActionWeb.click(btnWishlist,true);
        internalActionWeb.click(viewWishlist,DEFAULT_TIMEOUT,true);
    }
    public void chooseButtonAddToCart() throws WebActionsException {
        internalActionWeb.click(btnAddToCart,true);
        internalActionWeb.click(viewShoppingCart,DEFAULT_TIMEOUT,true);
    }
    public String getTextMedio() throws WebActionsException {
        textMedio=internalActionWeb.getDriver().findElement(By.xpath("//h1"));
        return internalActionWeb.getText(textMedio,true);
    }
    public List<String> getTextProductMedio() throws WebActionsException {
        List<String> titleProduct=new ArrayList<>();
        for(int i=0;i<textProduct.size();i++)
            titleProduct.add(textProduct.get(i).getText());
        return titleProduct;
    }
    public String getTitleProduct() throws WebActionsException {
      return internalActionWeb.getText(selectProduct,true);
    }
    public int getQuantity(){
        return Integer.parseInt(textQuantity.getAttribute("value"));
    }
    public String getPriceUnit() throws WebActionsException {
        return internalActionWeb.getText(textPriceUnit,true);
    }
    public String getPriceTotal() throws WebActionsException {
        return internalActionWeb.getText(textPriceTotal,true);
    }



}
