package commons;

import org.openqa.selenium.WebDriver;
import pageObjects.IIGElearning.HomePageObject;
import pageObjects.IIGElearning.UserHomePageObject;

public class PageGeneratorManager {


    public static HomePageObject getHomePage(WebDriver driver){
        return new HomePageObject(driver);
    }

    public static UserHomePageObject getUserHomePage(WebDriver driver){
        return new UserHomePageObject(driver);
    }


}

