package helpers;

import com.codeborne.selenide.WebDriverRunner;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.screenshot;

/**
 * Created by Plugatarenko Kate on 07-Apr-17.
 */
public class ScreenShotHelper {

    private Logger LOGGER = Logger.getLogger(this.getClass());

    @Attachment(type = "image/png")
    public static byte[] allureScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Screenshot: {0}", type = "image/png")
    public static byte[] allureScreenshot(String name) {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public String textAttachement(String attach) {
        return attach;
    }

    public static byte[] makeScreenShotIfTestFailed(String testName){
        String timeFormat = new SimpleDateFormat("hh.mm.ss").format(new Date());
        //String testName = testResult.getMethod().getTestClass().getRealClass().getSimpleName();
        return screenshot(testName + "_" + timeFormat).getBytes();
    }



}