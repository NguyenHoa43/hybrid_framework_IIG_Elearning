package pageObjects.IIGElearning;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class HomePageObject extends BasePage {

    private WebDriver driver;

    public HomePageObject(WebDriver driver){
        this.driver = driver;
    }
}
