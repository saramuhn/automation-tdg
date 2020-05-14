package co.com.client.desktopProject.test.page;

import co.com.client.desktopProject.test.helpers.Dictionary.*;
import co.com.sofka.test.exceptions.DesktopActionsException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

import static co.com.client.desktopProject.test.stepdefinition.StepsDefinition.internalActionDesk;

public class PageCalculator {

    @FindBy(id = "num1Button")
    private WebElement btnUno;
    @FindBy(id = "num2Button")
    private WebElement btnDos;
    @FindBy(id = "num3Button")
    private WebElement btnTres;
    @FindBy(id = "num4Button")
    private WebElement btnCuatro;
    @FindBy(id = "num5Button")
    private WebElement btnCinco;
    @FindBy(id = "num6Button")
    private WebElement btnSeis;
    @FindBy(id = "num7Button")
    private WebElement btnSiete;
    @FindBy(id = "num8Button")
    private WebElement btnOcho;
    @FindBy(id = "num9Button")
    private WebElement btnNueve;
    @FindBy(id = "num0Button")
    private WebElement btnCero;
    @FindBy(id = "plusButton")
    private WebElement btnMas;
    @FindBy(id = "minusButton")
    private WebElement btnMenos;
    @FindBy(id = "multiplyButton")
    private WebElement btnMultip;
    @FindBy(id = "divideButton")
    private WebElement btnDiv;
    @FindBy(id = "equalButton")
    private WebElement btnIgual;
    @FindBy(id = "CalculatorResults")
    private WebElement lblResults;

    private Map<String,WebElement> selectorNumero;
    private Map<String,WebElement> selectorOperacion;

    public PageCalculator(){
        PageFactory.initElements(internalActionDesk.getDriver(), this);
        //La instanciacion de selectores debe ir despues del initElements siempre
        initSelectors();
    }

    public void ingresarNumero1(String numero) throws DesktopActionsException {
        internalActionDesk.click(selectorNumero.get(numero),false);
    }
    public void ingresarNumero2(String numero) throws DesktopActionsException {
        internalActionDesk.click(selectorNumero.get(numero),false);
    }
    public void ingresarOperacion(String operacion) throws DesktopActionsException {
        internalActionDesk.click(selectorOperacion.get(operacion),false);
    }
    public String obtenerResultado() throws DesktopActionsException {
        internalActionDesk.click(btnIgual,false);
        return internalActionDesk.getName(lblResults,false);
    }

    public void initSelectors(){
        selectorNumero = new HashMap<>();
        selectorNumero.put(Numero.UNO,btnUno);
        selectorNumero.put(Numero.DOS,btnDos);
        selectorNumero.put(Numero.TRES,btnTres);
        selectorNumero.put(Numero.CUATRO,btnCuatro);
        selectorNumero.put(Numero.CINCO,btnCinco);
        selectorNumero.put(Numero.SEIS,btnSeis);
        selectorNumero.put(Numero.SIETE,btnSiete);
        selectorNumero.put(Numero.OCHO,btnOcho);
        selectorNumero.put(Numero.NUEVE,btnNueve);
        selectorNumero.put(Numero.CERO,btnCero);
        selectorOperacion = new HashMap<>();
        selectorOperacion.put(Operacion.SUMA,btnMas);
        selectorOperacion.put(Operacion.RESTA,btnMenos);
        selectorOperacion.put(Operacion.MULTIPLICACION,btnMultip);
        selectorOperacion.put(Operacion.DIVISION,btnDiv);
    }
}
