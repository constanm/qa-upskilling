package basics;

import objects.configuration.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;

public class DownloadTest {
    private static String myDownloadFolder;

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("webdriver.chrome.driver", "D:\\WORK\\SELENIUM\\chromedriver.exe");

        System.setProperty("webdriver.gecko.driver", "D:\\WORK\\SELENIUM\\geckodriver.exe");
        System.setProperty("webdriver.firefox.bin", "D:\\PROGRAME\\ff5010\\firefox.exe");

        myDownloadFolder = Configuration.getConfig().getDownloadDirectory();
    }

    //todo: failing..
    @Test
    public void testVerifyDownloadForFirefox() throws InterruptedException {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        firefoxProfile.setPreference("browser.download.manager.useWindow", true);
        firefoxProfile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf");
        firefoxProfile.setPreference("browser.download.dir", myDownloadFolder);
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;text/plain;text/csv");
        firefoxProfile.setPreference("pdfjs.disabled", true);
        firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        firefoxProfile.setPreference("plugin.scan.plid.all", false);
        firefoxProfile.setPreference("plugin.scan.Acrobat", "99.0");
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(firefoxProfile);
        WebDriver driver = new FirefoxDriver(options);
        driver.navigate().to("http://www.orimi.com/pdf-test.pdf");
        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    public void testVerifyDownloadForChrome() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromeOptionsMap = new HashMap<>();
        chromeOptionsMap.put("plugins.plugins_disabled", new String[]{"Chrome PDF Viewer"});
        chromeOptionsMap.put("plugins.always_open_pdf_externally", true);
        options.setExperimentalOption("prefs", chromeOptionsMap);
        chromeOptionsMap.put("download.default_directory", myDownloadFolder);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        ChromeDriver driver = new ChromeDriver(cap);
        driver.manage().window().maximize();
        driver.navigate().to("http://www.orimi.com/pdf-test.pdf");
        Thread.sleep(3000);
        driver.quit();
    }
}
