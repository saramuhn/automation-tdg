package co.com.client.desktopProject.test.internalaction;

import co.com.sofka.test.actions.WinDeskAction;
import co.com.sofka.test.evidence.logs.Log;
import co.com.sofka.test.evidence.reports.Report;
import co.com.sofka.test.exceptions.DesktopActionsException;
import co.com.sofka.test.exceptions.WebActionsException;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Optional;

import static co.com.sofka.test.actions.ActionMessages.*;


public class InternalActionDesk extends WinDeskAction {

    public InternalActionDesk(String projectFolderName) {
        super(projectFolderName);
    }

    /**
     * Este metodo obtiene la propiedad name de un elemento .
     *
     * @param element      Web Element
     * @param isScreenShot requiere screenshot
     * @return Cadena de texto con la información del objeto obtenida
     */
    public String getName(WebElement element, boolean isScreenShot) throws DesktopActionsException {
        return getName(element, DEFAULT_TIMEOUT, isScreenShot);
    }


    /**
     * Este metodo obtiene la propiedad name de un elemento .
     *
     * @param element      Element
     * @param timeout      Tiempo de espera hasta lanzar la excepción si el elemento no esta disponible
     * @param isScreenShot requiere screenshot
     * @return Cadena de texto con la información del objeto obtenida
     * @throws WebActionsException si no encuentra el elemento o tiene problemas para seleccionarlo
     */
    public String getName(WebElement element, int timeout, boolean isScreenShot) throws DesktopActionsException {
        final String ACTION = "getText";

        String params = String.format(PARAM, TEXT_TIMEOUT, timeout);
        try {
            validateInstance(driver, TEXT_DRIVER);
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
            String text = element.getAttribute("Name");
            validateScreenShot(isScreenShot);
            String msg = String.format(SUCCESS_MSG_PARAMS,
                    ACTION,
                    params.concat(COMMA)
                            .concat(String.format(PARAM, "Extracted Text", text)),
                    getEvidenceText(isScreenShot));
            Log.LOGGER.log(Log.CustomLevels.COMPLETED, msg);
            return text;
        } catch (NoSuchElementException | NullPointerException | TimeoutException e) {
            validateScreenShot(isScreenShot);
            String error = String.format(ERROR_MSG_PARAMS,
                    ACTION,
                    params,
                    getEvidenceText(isScreenShot));
            throw new DesktopActionsException(error, e);
        }
    }
    public void standardMethod(WebElement element, int timeout, boolean isScreenShot) throws DesktopActionsException {
        final String ACTION = "standardMethod";
        String params = String.format(PARAM, TEXT_TIMEOUT, timeout);
        try {
            validateInstance(driver, TEXT_DRIVER);
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
            ///////////////////////////////////////

            String text = element.getAttribute("Name");

            ///////////////////////////////////////
            validateScreenShot(isScreenShot);
            String msg = String.format(SUCCESS_MSG_PARAMS,
                    ACTION,
                    params.concat(COMMA),
                    getEvidenceText(isScreenShot));
            Log.LOGGER.log(Log.CustomLevels.COMPLETED, msg);
        } catch (NoSuchElementException | NullPointerException | TimeoutException e) {
            validateScreenShot(isScreenShot);
            String error = String.format(ERROR_MSG_PARAMS,
                    ACTION,
                    params,
                    getEvidenceText(isScreenShot));
            throw new DesktopActionsException(error, e);
        }
    }


    public void clearDriver() {
        Optional.ofNullable(getDriver()).ifPresent(winiumDriver -> {
                    try {
                        if (SystemUtils.IS_OS_WINDOWS) {
                            Runtime.getRuntime().exec("taskkill /IM "
                                    .concat("WiniumDesktopDriver.exe")
                                    .concat(" /F"));
                            Thread.sleep(3000);
                        }
                    } catch (Exception e) {
                        Report.TestLog.logInfo("Cant kill driver process. Skipping..");
                    }
                }
        );

    }

    /*
     * Actualmente no funcional por issues reportados con el driver
     * */
    public void closeApp(String appTaskName) {
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                String command = "taskkill /IM "
                        .concat(appTaskName)
                        .concat(" /F");
                Runtime.getRuntime().exec(command);
            }
        } catch (IOException e) {
            Report.TestLog.logInfo("Cant kill app process. Skipping..");
        }
    }
}



