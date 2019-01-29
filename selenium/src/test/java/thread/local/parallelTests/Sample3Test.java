package thread.local.parallelTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import thread.local.utils.DriverBase;

public class Sample3Test extends DriverBase {
    static WebDriver wd;

    @Test
    public void RunChromeBrowser() {
        wd = DriverBase.getDriver();
        wd.get("http://www.google.com");
    }
}
