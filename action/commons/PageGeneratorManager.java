package commons;

import org.openqa.selenium.WebDriver;
import pageObjects.NopCommerce.admin.AdminDashboardPageObject;
import pageObjects.NopCommerce.admin.AdminLoginPageObject;
import pageObjects.NopCommerce.user.*;

public class PageGeneratorManager {
    public static UserHomePageObject getUserHomePage(WebDriver driver) {
        return new UserHomePageObject(driver);
    }

    public static UserLoginPageObject getUserLoginPage(WebDriver driver) {
        return new UserLoginPageObject(driver);
    }

    public static UserRegisterPageObject getUserRegisterPage(WebDriver driver) {
        return new UserRegisterPageObject(driver);
    }

    public static UserCustomerInforPageObject getUserAccountPage(WebDriver driver) {
        return new UserCustomerInforPageObject(driver);
    }

    public static UserMyProductReviewPageObject getUserMyProductReviewPage(WebDriver driver) {
        return new UserMyProductReviewPageObject(driver);
    }

    public static UserRewardPoinPageObject getUserRewardPoinPage(WebDriver driver) {
        return new UserRewardPoinPageObject(driver);
    }

    public static UserAddressPageObject getUserAddressPage(WebDriver driver) {
        return new UserAddressPageObject(driver);
    }

    public static AdminLoginPageObject getAdminLoginPage(WebDriver driver) {
        return new AdminLoginPageObject(driver);
    }

    public static AdminDashboardPageObject getAdminDashboardPage(WebDriver driver) {
        return new AdminDashboardPageObject(driver);
    }



}
