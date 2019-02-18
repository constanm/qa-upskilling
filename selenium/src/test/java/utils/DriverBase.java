package utils;

import objects.configuration.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

public class DriverBase {
    private static List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<>());
    private static ThreadLocal<DriverFactory> driverThread;

    @BeforeAll
    public static void instantiateDriverObject() {
        driverThread = ThreadLocal.withInitial(() -> {
            DriverFactory webDriverThread = new DriverFactory();
            webDriverThreadPool.add(webDriverThread);
            return webDriverThread;
        });
    }

    public static void takesScreenShot(String section, String details) {
        String screenshotDirectory = Configuration.getConfig().getScreenshotsDirectory();
        String screenshotAbsolutePath = format("%s%s%s_%s_%d.png",
                screenshotDirectory,
                File.separator,
                section, details,
                System.currentTimeMillis());
        File screenShotFile = new File(screenshotAbsolutePath);
        try {
            FileOutputStream screenshotStream = new FileOutputStream(screenShotFile);

            screenshotStream.write(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES));
            screenshotStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file " + screenshotAbsolutePath);
        } catch (IOException e) {
            System.out.println("Cannot write to file " + screenshotAbsolutePath);
        }
    }

    public static WebDriver getDriver() {
        return driverThread.get().getDriver();
    }

    @AfterEach
    public void clearCookies() {
        getDriver().manage().deleteAllCookies();
    }

    @AfterAll
    public static void closeDriverObjects() {
        webDriverThreadPool.forEach(DriverFactory::quitDriver);
    }
}
