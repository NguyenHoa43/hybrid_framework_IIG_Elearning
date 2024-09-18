package com.IIGElearning;

import commons.BaseTest;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import pageObjects.IIGElearning.HomePageObject;
import pageObjects.IIGElearning.UserHomePageObject;

public class Register extends BaseTest {

    private WebDriver driver;
    private String email, firstName, middleName, userName, passWord, confirmPassword ;
    private HomePageObject homePage;
    private UserHomePageObject userPage;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName){
        driver = getBrowserDriver(browserName);
        homePage = PageGeneratorManager.getHomePage(driver);
        userPage = PageGeneratorManager.getUserHomePage(driver);

    }
}
