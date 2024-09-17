package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;



public class BasePage {

    protected static BasePage getBasePageObject() {
        return new BasePage();

    }

    public void openPageUrl(WebDriver driver, String pageUrl) {
        driver.get(pageUrl);
    }

    protected String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    protected String getPageUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    protected String getPageSourceCode(WebDriver driver) {
        return driver.getPageSource();
    }

    protected void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    protected void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshToPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    protected Alert waitForAlertPresence(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    protected void acceptAlert(WebDriver driver) {
        waitForAlertPresence(driver).accept();
    }

    protected void cancelAlert(WebDriver driver) {
        waitForAlertPresence(driver).dismiss();
    }

    public Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    public void setCookies(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie :cookies) {
            driver.manage().addCookie(cookie);
        }
        sleep(3);
    }
    protected String getAlertText(WebDriver driver) {
        return waitForAlertPresence(driver).getText();
    }

    protected void sendKeysToAlert(WebDriver driver, String textValue) {
        waitForAlertPresence(driver).sendKeys(textValue);
    }

    protected void switchToWindowbyId(WebDriver driver, String IDmain) {
        Set<String> AllWindowIDs = driver.getWindowHandles();
        for (String id : AllWindowIDs) {
            if (!id.equals(IDmain)) {
                driver.switchTo().window(id);
            }
        }
    }

    protected void switchToWindowByPageTitle(WebDriver driver, String PageTitle) {
        Set<String> allWindowIds = driver.getWindowHandles();
        for (String ID : allWindowIds) {
            driver.switchTo().window(ID);
            String actualPageTitle = driver.getTitle();
            if (actualPageTitle.equals(PageTitle)) {
                break;
            }
        }
    }

    protected void closeAllTabWithoutParent(WebDriver driver, String parentID) {

        Set<String> allTabIDs = driver.getWindowHandles();
        for (String id : allTabIDs) {
            if (!id.equals(parentID)) {
                driver.switchTo().window(id);
                driver.close();
            }
            driver.switchTo().window(parentID);
        }
    }

    public List<WebElement> getListWebElement(WebDriver driver, String locatorType) {
        return driver.findElements(getByLocator(locatorType));
    }

    private By getByLocator(String locatorType) {
        By by = null;
        // System.out.println("Locator type = " + locatorType);
        if (locatorType.startsWith("id=") || locatorType.startsWith("ID=") || locatorType.startsWith("Id=")) {
            by = By.id(locatorType.substring(3));
        } else if (locatorType.startsWith("class=") || locatorType.startsWith("CLASS=")
                || locatorType.startsWith("Class=")) {
            by = By.className(locatorType.substring(6));
        } else if (locatorType.startsWith("name=") || locatorType.startsWith("NAME=")
                || locatorType.startsWith("Name=")) {
            by = By.name(locatorType.substring(5));
        } else if (locatorType.startsWith("css=") || locatorType.startsWith("CSS=") || locatorType.startsWith("Css=")) {
            by = By.cssSelector(locatorType.substring(4));
        } else if (locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=")
                || locatorType.startsWith("Xpath=")) {
            by = By.xpath(locatorType.substring(6));
        } else {
            throw new RuntimeException("Locator type is not supported!");
        }
        return by;
    }

    private String getDynamicXpath(String locatorType, String... dynamicValues) {
        if (locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPATH=")) {
            locatorType = String.format(locatorType, (Object[]) dynamicValues);
        }
        return locatorType;
    }

    private WebElement getWebElement(WebDriver driver, String locatorType) {
        return driver.findElement(getByLocator(locatorType));
    }

    protected void clickToElement(WebDriver driver, String locatorType) {
        getWebElement(driver, locatorType).click();
    }
    protected void clickToElement(WebDriver driver, WebElement element) {
        element.click();
    }

    protected void clickToElement(WebDriver driver, String locatorType, String... dynamicValues) {
        getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).click();
    }

    protected void sendkeysToElement(WebDriver driver, String locatorType, String textValue) {
        WebElement element = getWebElement(driver, locatorType);
        element.clear();
        element.sendKeys(textValue);
    }

    protected void sendkeysToElement(WebDriver driver, String locatorType, String textValue, String... dynamicValues) {
        WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
        element.clear();
        element.sendKeys(textValue);
    }

    protected void selectItemInDefaultDropdown(WebDriver driver, String locatorType, String textItem) {
        Select select = new Select(getWebElement(driver, locatorType));
        select.selectByVisibleText(textItem);
    }

    protected void selectItemInDefaultDropdown(WebDriver driver, String locatorType, String textItem,
                                               String... dynamicValues) {
        Select select = new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
        select.selectByVisibleText(textItem);
    }

    protected String getFirstSelectedItemDefaultDropdown(WebDriver driver, String locatorType) {
        Select select = new Select(getWebElement(driver, locatorType));
        return select.getFirstSelectedOption().getText();
    }

    protected boolean isDropdownMultiple(WebDriver driver, String locatorType) {
        Select select = new Select(getWebElement(driver, locatorType));
        return select.isMultiple();
    }

    protected void selectItemInCustomDropdown(WebDriver driver, String locatorType, String parentXpath,
                                              String childXpath, String expectedTextItem) {
        getWebElement(driver, locatorType).click();
        sleep(3);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        List<WebElement> allItems = explicitWait
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(childXpath)));
        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedTextItem)) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scollIntoView(true);", item);
                sleep(1);
                item.click();
                break;
            }
        }
    }

    public void sleep(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected String getElementAttribute(WebDriver driver, String locatorType, String attributeValue) {
        return getWebElement(driver, locatorType).getAttribute(attributeValue);
    }

    protected String getElementAttribute(WebDriver driver, String locatorType, String attributeValue,
                                         String... dynamicValues) {
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getAttribute(attributeValue);
    }

    protected String getElementText(WebDriver driver, String locatorType) {
        return getWebElement(driver, locatorType).getText();
    }

    protected String getElementText(WebDriver driver, String locatorType, String... dynamicValues) {
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getText();
    }

    protected String getElementCssValue(WebDriver driver, String locatorType, String propertiesName) {
        return getWebElement(driver, locatorType).getCssValue(propertiesName);
    }

    protected String getHexaColorFromRGBA(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex();
    }

    protected int getElementSize(WebDriver driver, String locatorType) {
        return getListWebElement(driver, locatorType).size();
    }

    protected int getElementSize(WebDriver driver, String locatorType, String... dynamicValues) {
        return getListWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).size();
    }

    public void checkToDefaultCheckboxRadio(WebDriver driver, String locatorType) {
        WebElement element = getWebElement(driver, locatorType);
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void checkToDefaultCheckboxRadio(WebDriver driver, String locatorType, String...dynamicValues) {
        WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void unCheckToDefaultCheckboxRadio(WebDriver driver, String locatorType) {
        WebElement element = getWebElement(driver, locatorType);
        if (element.isSelected()) {
            element.click();
        }
    }

    public void unCheckToDefaultCheckboxRadio(WebDriver driver, String locatorType, String...dynamicValues) {
        WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
        if (element.isSelected()) {
            element.click();
        }
    }

    protected boolean isElementDisplay(WebDriver driver, String locatorType) {
        try {
            return getWebElement(driver, locatorType).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void overrideGlobalTimeout(WebDriver driver, long timeOut) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
    }

    public boolean isElementUndisplayed(WebDriver driver, String locator) {
        overrideGlobalTimeout(driver,shortTimeout);
        List<WebElement>elements = getListWebElement(driver,locator);
        overrideGlobalTimeout(driver,longTimeout);

        if(elements.size() == 0) {
            return true;
        }else if(elements.size() > 0 && !elements.get(0).isDisplayed()) {
            return true;
        }else {
            return false;
        }
    }

    protected boolean isElementDisplay(WebDriver driver, String locatorType, String... dynamicValues) {
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isDisplayed();
    }

    protected boolean isElementEnabled(WebDriver driver, String locatorType) {
        return getWebElement(driver, locatorType).isEnabled();
    }

    protected boolean isElementSelected(WebDriver driver, String locatorType) {
        return getWebElement(driver, locatorType).isSelected();
    }

    protected void switchToFrameIframe(WebDriver driver, String locatorType) {
        driver.switchTo().frame(getWebElement(driver, locatorType));
    }

    protected void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    protected void hoverMouseToElement(WebDriver driver, String locatorType) {
        Actions action = new Actions(driver);
        action.moveToElement(getWebElement(driver, locatorType)).perform();
    }

    public void pressKeyToElement(WebDriver driver, String locatorType, Keys key) {
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(driver, locatorType), key).perform();
    }



    public void pressKeyToElement(WebDriver driver, String locatorType, Keys key, String... dynamicValues) {
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)), key).perform();
    }

    protected void scrollToBottomPage(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    protected void highlightElement(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getWebElement(driver, locatorType);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
                "border: 2px solid red; border-style: dashed;");
        sleep(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
                originalStyle);
    }

    protected void clickToElementByJS(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, locatorType));
    }

    protected void scrollToElement(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locatorType));
    }

    protected void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
                getWebElement(driver, locatorType));
    }

    protected boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }

    protected String getElementValidationMessage(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;",
                getWebElement(driver, locatorType));
    }

    protected boolean isImageLoaded(WebDriver driver, String locatorType) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        boolean status = (boolean) jsExecutor.executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                getWebElement(driver, locatorType));
        if (status) {
            return true;
        } else {
            return false;
        }
    }
    protected boolean isImageLoaded(WebDriver driver, String locatorType, String... dynamicValues) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        boolean status = (boolean) jsExecutor.executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    protected void waitForElementVisible(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locatorType)));
    }

    protected void waitForElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        explicitWait.until(ExpectedConditions
                .visibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitAllElementVisible(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorType)));
    }

    protected void waitAllElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        explicitWait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForElementInVisible(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
    }
    protected void waitForElementUndisplayed(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(shortTimeout));
        overrideGlobalTimeout(driver, shortTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
        overrideGlobalTimeout(driver, longTimeout);
    }

    protected void waitForElementInVisible(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions
                .invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitAllElementInVisible(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver, locatorType)));
    }

    protected void waitAllElementInVisible(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        explicitWait.until(ExpectedConditions
                .invisibilityOfAllElements(getListWebElement(driver, getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForElementClickable(WebDriver driver, String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locatorType)));
    }
    protected void waitForElementClickable(WebDriver driver, WebElement element) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        explicitWait.until(ExpectedConditions.elementToBeClickable(element));
    }


    protected void waitForElementClickable(WebDriver driver, String locatorType, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        explicitWait.until(
                ExpectedConditions.elementToBeClickable(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
    }
//	@Step("upload Multiple File")
//	public void uploadMultipleFile(WebDriver driver, String... fileNames) {
//		String filePath = GlobalConstants.UPLOAD_FLIE;
//		String fullFileName = "";
//		for (String file : fileNames) {
//			fullFileName = fullFileName + filePath + file + "\n";
//		}
//		fullFileName = fullFileName.trim();
//		getWebElement(driver, BasePagejQueryUI.UPLOADFILE).sendKeys(fullFileName);
//	}
//
//
//
//
//	public BasePage openPagesAtMyAccountByName(WebDriver driver, String pageName) {
//		waitForElementClickable(driver, BasePageNopCommerceUI.DYNAMIC_PAGE_AT_MY_ACCOUNT_AREA, pageName);
//		clickToElement(driver, BasePageNopCommerceUI.DYNAMIC_PAGE_AT_MY_ACCOUNT_AREA, pageName);
//		switch (pageName) {
//		case "Addresses":
//			return PageGeneratorManager.getUserAddressPage(driver);
//		case "My product reviews":
//			return PageGeneratorManager.getUserMyProductReviewPage(driver);
//		case "Reward points":
//			return PageGeneratorManager.getUserRewardPoinPage(driver);
//		case "Customer info":
//			return PageGeneratorManager.getUserAccountPage(driver);
//		default:
//			throw new RuntimeException("Invalid page name at My Account area.");
//		}
//	}
//






    private long longTimeout = GlobalConstants.LONG_TIME_OUT;
    private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;
}
