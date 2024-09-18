package commons;

import java.io.File;

public class GlobalConstants {

    public static final String PAGE_URL = "https://elearningtest.route.iigvietnam.com/";
    //public static final String PAGE_URL = "https://elearningtest.route.iigvietnam.com/";  test commit
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String JAVA_VERSION = System.getProperty("java.version");

    public static final String UPLOAD_FLIE = PROJECT_PATH + File.separator + "uploadFile" + File.separator;
    public static final String DOWNLOAD_FILE = PROJECT_PATH + File.separator + "downloadFile";
    public static final String BROWSER_LOG = PROJECT_PATH + File.separator + "browserLogs";
    public static final String DRAG_DROP_HTML5 = PROJECT_PATH + File.separator + "dragDropHTML5";
    public static final String AUTO_IT_SCRIPT = PROJECT_PATH + File.separator + "autoIT";
    public static final String REPORTNG_SCREENSHOT = PROJECT_PATH + File.separator + "ReportNGImages" + File.separator;

    public static final String REPORTNG_SCREENSHOT_EXTENTV5 = PROJECT_PATH + File.separator + "extentV5" + File.separator;

    public static final String DB_DEV_URL = "192.168.1.15:9860";
    public static final String USER_NAME = "automationdhoa";
    public static final String PASS_WORD = "Hoabeo@43#";
    public static final long SHORT_TIMEOUT = 5;
    public static final long LONG_TIME_OUT = 100;
    public static final long RETRY_TEST_FAIL = 3;

}
