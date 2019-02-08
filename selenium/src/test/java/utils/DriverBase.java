package utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
