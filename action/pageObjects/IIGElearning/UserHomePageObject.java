package pageObjects.IIGElearning;

import commons.BasePage;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;

public class UserHomePageObject extends BasePage {

    private WebDriver driver;

    public UserHomePageObject(WebDriver driver){
        this.driver = driver;
    }

}
