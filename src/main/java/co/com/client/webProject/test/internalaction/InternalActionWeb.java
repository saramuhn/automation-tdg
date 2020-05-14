package co.com.client.webProject.test.internalaction;

import co.com.sofka.test.actions.WebAction;
import co.com.sofka.test.automationtools.selenium.Browser;
import co.com.sofka.test.evidence.reports.Report;
import org.apache.commons.lang3.SystemUtils;


public class InternalActionWeb extends WebAction {

    public InternalActionWeb(String projectFolderName) {
        super(projectFolderName);
    }

    /**
     * Cerrar drivers
     */
    public void clearDriver() {
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                Runtime.getRuntime().exec("taskkill /IM "
                        .concat(getBrowserConfiguration().getBrowser().getValue(Browser.OS.WINDOWS))
                        .concat(" /F"));
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            Report.TestLog.logInfo("Cant kill driver process. Skipping..");
        }
    }

}



