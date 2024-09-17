package commons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;

public class BaseTest {

    private WebDriver driverBaseTest;
    protected final Log log;

    @BeforeSuite
    public void initBeforeSuite() {
        deleteAllureReport();
    }
    protected BaseTest() {
        log = LogFactory.getLog(getClass());
    }

    protected WebDriver getBrowserDriver(String browserName) {
        if (browserName.equals("firefox")) {

            driverBaseTest = new FirefoxDriver();
        } else if (browserName.equals("headlessfirefox")) {

            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            options.addArguments("window-size=1920x1080");
            driverBaseTest = new FirefoxDriver(options);
        } else if (browserName.equals("coccoc")) {

            ChromeOptions options = new ChromeOptions();
            if (GlobalConstants.OS_NAME.startsWith("Windows")) {
                options.setBinary("C:\\Program Files\\CocCoc\\Browser\\Application\\browser.exe");
            } else {
                options.setBinary(".....");
            }
            driverBaseTest = new ChromeDriver(options);
        } else if (browserName.equals("chrome")) {

            driverBaseTest = new ChromeDriver();
        } else if (browserName.equals("headlesschrome")) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("window-size=1920x1080");
            driverBaseTest = new ChromeDriver(options);

        } else if (browserName.equals("edge")) {

            driverBaseTest = new EdgeDriver();
        } else {
            throw new RuntimeException("Browser name invalid !");
        }
        driverBaseTest.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIME_OUT));
        driverBaseTest.get(GlobalConstants.PAGE_URL);
        return driverBaseTest;
    }

    protected WebDriver getBrowserDriver(String browserName, String appURL) {
        if (browserName.equals("firefox")) {

            driverBaseTest = new FirefoxDriver();
        } else if (browserName.equals("headlessfirefox")) {

            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            options.addArguments("window-size=1920x1080");
            driverBaseTest = new FirefoxDriver(options);
        } else if (browserName.equals("coccoc")) {

            ChromeOptions options = new ChromeOptions();
            if (GlobalConstants.OS_NAME.startsWith("Windows")) {
                options.setBinary("C:\\Program Files\\CocCoc\\Browser\\Application\\browser.exe");
            } else {
                options.setBinary(".....");
            }
            driverBaseTest = new ChromeDriver(options);
        } else if (browserName.equals("chrome")) {

            driverBaseTest = new ChromeDriver();
        } else if (browserName.equals("headlesschrome")) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("window-size=1920x1080");
            driverBaseTest = new ChromeDriver(options);

        } else if (browserName.equals("edge")) {

            driverBaseTest = new EdgeDriver();
        } else {
            throw new RuntimeException("Browser name invalid !");
        }
        driverBaseTest.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIME_OUT));
        driverBaseTest.get(appURL);
        return driverBaseTest;
    }

    protected int fadeNumber() {
        Random rand = new Random();
        return rand.nextInt(99999);
    }

    public WebDriver getDriverInstance() {
        return this.driverBaseTest;
    }

    protected boolean verifyTrue(boolean condition) {
        boolean status = true;
        try {
            Assert.assertTrue(condition);
            log.info("---------------------- Passed -----------------------");
        } catch (Throwable e) {
            status = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
            log.info("---------------------- Failed -----------------------");
        }
        return status;
    }

    protected boolean verifyFalse(boolean condition) {
        boolean status = true;
        try {
            Assert.assertFalse(condition);
            log.info("---------------------- Passed -----------------------");
        } catch (Throwable e) {
            status = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
            log.info("---------------------- Failed -----------------------");
        }
        return status;
    }

    protected boolean verifyEquals(Object actual, Object expected) {
        boolean status = true;
        try {
            Assert.assertEquals(actual, expected);
            log.info("---------------------- Passed -----------------------");
        } catch (Throwable e) {
            status = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
            log.info("---------------------- Failed -----------------------");
        }
        return status;
    }
    public void deleteAllureReport() {
        try {
            String pathFolderDownload = GlobalConstants.PROJECT_PATH +  "/allure-json";
            File file = new File(pathFolderDownload);
            File[] listOfFiles = file.listFiles();
            if (listOfFiles.length != 0) {
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile() && !listOfFiles[i].getName().equals("environment.properties")) {
                        new File(listOfFiles[i].toString()).delete();
                    }
                }
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    protected void closeBrowserDriver() {
        String cmd = null;
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            log.info("OS name = " + osName);

            String driverInstanceName = driverBaseTest.toString().toLowerCase();
            log.info("Driver instance name = " + driverInstanceName);

            String browserDriverName = null;

            if (driverInstanceName.contains("chrome")) {
                browserDriverName = "chromedriver";
            } else if (driverInstanceName.contains("internetexplorer")) {
                browserDriverName = "IEDriverServer";
            } else if (driverInstanceName.contains("firefox")) {
                browserDriverName = "geckodriver";
            } else if (driverInstanceName.contains("edge")) {
                browserDriverName = "msedgedriver";
            } else if (driverInstanceName.contains("opera")) {
                browserDriverName = "operadriver";
            } else {
                browserDriverName = "safaridriver";
            }

            if (osName.contains("window")) {
                cmd = "taskkill /F /FI \"IMAGENAME eq " + browserDriverName + "*\"";
            } else {
                cmd = "pkill " + browserDriverName;
            }

            if (driverBaseTest != null) {
                driverBaseTest.manage().deleteAllCookies();
                driverBaseTest.quit();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            try {
                Process process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
