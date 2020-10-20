package objects.webtestsbase;

import objects.configuration.Configuration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Base class for web tests. It contains web driver {@link WebDriver} instance, used in all tests.
 * All communications with driver should be done through this class
 */
public class WebDriverFactory {
    private static final long IMPLICIT_WAIT_TIMEOUT = 5;
    private static WebDriver driver;

    /**
     * Getting of pre-configured {@link WebDriver} instance.
     * Please use this method only after call {@link #startBrowser(boolean) startBrowser} method
     *
     * @return webdriver object, or throw IllegalStateException, if driver has not been initialized
     */
    public static WebDriver getDriver() {
        if (driver != null) {
            return driver;
        } else {
            throw new IllegalStateException("Driver has not been initialized. " +
                    "Please call WebDriverFactory.startBrowser() before use this method");
        }
    }

    /**
     * Main method of class - it initialize driver and starts browser.
     *
     * @param isLocal - is tests will be started local or not
     */
    public static void startBrowser(boolean isLocal) {
        if (driver == null) {
            Browser browser = Configuration.getConfig().getBrowser();
            if (!isLocal) {
                driver = new RemoteWebDriver(CapabilitiesGenerator.getDefaultCapabilities(browser));
            } else {
                switch (browser) {
                    case FIREFOX:
                        System.setProperty("webdriver.gecko.driver", Configuration.getConfig().getWebdriver());
                        System.setProperty("webdriver.firefox.bin", Configuration.getConfig().getFirefoxBinary());
                        driver = new FirefoxDriver(CapabilitiesGenerator.getDefaultCapabilities(Browser.FIREFOX));
                        break;
                    case CHROME:
                        System.setProperty("webdriver.chrome.driver", Configuration.getConfig().getChromedriver());
                        ChromeOptions options = new ChromeOptions();
                        // Proxy proxy = new Proxy();
                        // proxy.setHttpProxy("myhttpproxy:3337");
                        // options.setCapability("proxy", proxy);
                        // options.addArguments("--headless");
                        // options.addArguments("--disable-gpu");
                        // options.setAcceptInsecureCerts(true);
                        // options.addArguments("--allow-insecure-localhost");
                        // options.addArguments("--lang=ro-RO");
                        options.addArguments("--start-maximized");

                        // another way to set up download directory
                        Map<String, Object> prefs = new HashMap<>();
                        prefs.put("download.default_directory", Configuration.getConfig().getDownloadDirectory());
                        options.setExperimentalOption("prefs", prefs);

                        driver = new ChromeDriver(options);
                        break;
                    case IE10:
                        driver = new InternetExplorerDriver(CapabilitiesGenerator.getDefaultCapabilities(Browser.IE10));
                        break;
                    case SAFARI:
                        driver = new SafariDriver(CapabilitiesGenerator.getDefaultCapabilities(Browser.SAFARI));
                        break;
                    default:
                        throw new IllegalStateException("Unsupported browser type");
                }
            }
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
        } else {
            throw new IllegalStateException("Driver has already been initialized. Quit it before using this method");
        }
    }

    /**
     * Finishes browser
     */
    public static void finishBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * This method calls in tests listeners on test fail
     */
    public static void takeScreenShot(String failingTestName) {
        System.out.println("ScreenShot method called");
        try {
            String screenshotDirectory = Configuration.getConfig().getScreenshotsDirectory();
            String screenshotAbsolutePath = screenshotDirectory + File.separator + System.currentTimeMillis() + "_" + failingTestName + ".png";
            File screenshot = new File(screenshotAbsolutePath);
            if (createFile(screenshot)) {
                try {
                    writeScreenshotToFile(driver, screenshot);
                } catch (ClassCastException weNeedToAugmentOurDriverObject) {
                    writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
                }
                System.out.println("Written screenshot to " + screenshotAbsolutePath);
            } else {
                System.err.println("Unable to create " + screenshotAbsolutePath);
            }
        } catch (Exception ex) {
            System.err.println("Unable to capture screenshot...");
            ex.printStackTrace();
        }
    }

    private static boolean createFile(File screenshot) throws IOException {
        boolean fileCreated = false;

        if (screenshot.exists()) {
            fileCreated = true;
        } else {
            File parentDirectory = new File(screenshot.getParent());
            if (parentDirectory.exists() || parentDirectory.mkdirs()) {
                fileCreated = screenshot.createNewFile();
            }
        }

        return fileCreated;
    }

    private static void writeScreenshotToFile(WebDriver driver, File screenshot) throws IOException {
        FileOutputStream screenshotStream = new FileOutputStream(screenshot);
        screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        screenshotStream.close();
    }
}
