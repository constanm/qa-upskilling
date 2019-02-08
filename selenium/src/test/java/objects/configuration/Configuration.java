package objects.configuration;

import objects.configuration.properties.PropertiesLoader;
import objects.configuration.properties.Property;
import objects.configuration.properties.PropertyFile;
import objects.webtestsbase.Browser;

@PropertyFile("config.properties")
public class Configuration {
    private static Configuration config;

    public static Configuration getConfig() {
        if (config == null) {
            config = new Configuration();
        }
        return config;
    }

    public Configuration() {
        PropertiesLoader.populate(this);
    }

    @Property("firefox.binary")
    private String firefoxBinary;

    @Property("browser.name")
    private String browser = "firefox";

    @Property("browser.version")
    private String version = "";

    @Property("webdriver")
    private String webdriver = "";

    @Property("screenshot.directory")
    private String screenshotsDirectory = "";

    @Property("download.directory")
    private String downloadDirectory = "";


    /**
     * getting browser object
     *
     * @return browser object
     */
    public Browser getBrowser() {
        Browser browserForTests = Browser.getByName(browser);
        if (browserForTests != null) {
            return browserForTests;
        } else {
            throw new IllegalStateException("Browser name '" + browser + "' is not valid");
        }
    }

    /**
     * getting browser version
     *
     * @return browser version
     */
    public String getBrowserVersion() {
        return version;
    }


    /**
     * getting path to webdriver
     *
     * @return path to webdriver
     */
    public String getWebdriver() {
        return webdriver;
    }


    /**
     * getting path to screenshots directory
     *
     * @return path to screenshots directory
     */
    public String getScreenshotsDirectory() {
        return screenshotsDirectory;
    }

    /**
     * getting path to download directory
     *
     * @return path to download directory
     */
    public String getDownloadDirectory() {
        return downloadDirectory;
    }

    public String getFirefoxBinary() {
        return firefoxBinary;
    }

    public void setFirefoxBinary(String firefoxBinary) {
        this.firefoxBinary = firefoxBinary;
    }
}
