package com.reversecoder.automationtemplate.pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.reversecoder.automationtemplate.test.BaseTest.ENVIRONMENT_WINDOWS;
import com.reversecoder.automationtemplate.test.BaseTest.OS;
import com.reversecoder.automationtemplate.util.DtoFactory;
import com.reversecoder.automationtemplate.util.PropertyLoader;

public abstract class AbstractPage<W extends AbstractPage> {

    protected WebDriver driverW;

    /**
     * Constructor injecting the WebDriver interface
     *
     * @param WebDriver
     */
    public AbstractPage(WebDriver driverW) {
        this.driverW = driverW;
        PageFactory.initElements(driverW, this);

        DtoFactory.initPageDto(this);
    }

    public String getTitle() {
        return driverW.getTitle();
    }

    /**
     * Checks if is element present.
     *
     * @param element The webelement.
     * @return true, if is element present, otherwise return false.
     * @throws Exception Exception Throws exception if any error occurs.
     */
    public boolean isElementPresent(WebElement element) throws Exception {
        try {
            waitForElement(element, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Click checkbox.
     *
     * @param element The webelement.
     * @throws Exception Return exceptions if any error occurs.
     * @return W The instance of this page.
     * @throws Exception Exception Throws exception if any error occurs.
     */
    public W clickCheckBox(WebElement element) throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driverW;
        js.executeScript("arguments[0].click();", element);
        Thread.sleep(1000);
        return (W) this;
    }

    /**
     * Checks if is element selected.
     *
     * @param element The webelement.
     * @return true, if is element selected, otherwise return false.
     * @throws Exception Exception Throws exception if any error occurs.
     */
    public boolean isWebElementSelected(WebElement element) throws Exception {
        try {
            if (element.isSelected()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if is element displayed.
     *
     * @param element The webelement.
     * @return true, if is element displayed, otherwise return false.
     * @throws Exception Exception Throws exception if any error occurs.
     */
    public boolean isElementDisplayed(WebElement element) throws Exception {
        try {
            if (waitForElement(element, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")))
                    .isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dynamically checks if is element displayed.
     *
     * @param element The webelement.
     * @return true, if is element displayed, otherwise return false.
     * @throws Exception Exception Throws exception if any error occurs.
     */
    public boolean dynamicWaitUntilElementFound(WebElement element) throws Exception {
        try {
            while (!element.isDisplayed()) {
                Thread.sleep(1000);
            }
            Thread.sleep(3000);
            if (element.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dynamically checks for specific time if is element displayed.
     *
     * @param element The webelement.
     * @return true, if is element displayed, otherwise return false.
     * @throws Exception Exception Throws exception if any error occurs.
     */
    public boolean dynamicWaitForSpecificTime(WebElement element) throws Exception {
        try {
            if (waitForElement(element, Integer.valueOf("40")).isDisplayed()) {
                Thread.sleep(2000);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dynamically checks for specific time if is element displayed.
     *
     * @param element The webelement.
     * @return W The generic page object.
     * @throws Exception Exception Throws exception if any error occurs.
     */
    public W dynamicWaitForSpecificWebElement(WebElement element) throws Exception {
        try {
            if (waitForElement(element, Integer.valueOf("40")).isDisplayed()) {
                Thread.sleep(2000);
                return (W) this;
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Dynamically checks for specific time if is element displayed.
     *
     * @param element The webelement.
     * @return WebElement The webelement.
     * @throws Exception Exception Throws exception if any error occurs.
     */
    public WebElement dynamicWaitForSpecificElement(WebElement element) throws Exception {
        try {
            if (waitForElement(element, Integer.valueOf("40")).isDisplayed()) {
                Thread.sleep(2000);
                return element;
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Checks if is element enabled.
     *
     * @param element The web element.
     * @return true, if is element enabled, otherwise return false.
     * @exception Throws execption if any error occurs.
     */
    public boolean isElementEnabled(WebElement element) throws Exception {
        try {
            if (waitForElement(element, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")))
                    .isEnabled()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Match element text.
     *
     * @param element The web element.
     * @param text the element text.
     * @return true, if is element text matched, otherwise return false.
     * @exception Throws execption if any error occurs.
     */
    public boolean isElementTextMatched(WebElement element, String text) throws Exception {
        try {
            if (waitForElement(element, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec"))).getText()
                    .equals(text)) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Check element text.
     *
     * @param element The web element.
     * @param text the element text.
     * @return true, if is element text contain, otherwise return false.
     * @exception Throws execption if any error occurs.
     */
    public boolean isElementTextContains(WebElement element, String text) throws Exception {
        try {
            if (waitForElement(element, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec"))).getText()
                    .contains(text)) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * waits for element to be present.
     *
     * @param element The web element.
     * @param timeOut The timeout in seconds when an expectation is called
     * @return WebElement The web element.
     * @exception Throws execption if any error occurs.
     */
    public WebElement waitForElement(WebElement element, int timeOut) throws Exception {
        WebDriverWait wait = new WebDriverWait(driverW, timeOut);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    /**
     * This method used for get a web-element existence.
     *
     * @param xPath The xpath of web element.
     * @return boolean Returns true if element exist otherwise false.
     * @exception Throws execption if any error occurs.
     */
    public boolean isElementExist(String xPath) throws Exception {
        return driverW.findElements(By.xpath(xPath)).size() > 0;
    }

    /**
     * Standard/Generic input field web element value clearing & setting
     *
     * @param field The web element.
     * @param value The value to be input.
     * @return generic page W.
     * @exception Throws execption if any error occurs.
     */
    public W fillInputField(WebElement field, String value) throws Exception {
        waitForElement(field, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));
        if (isEnvironmentType(ENVIRONMENT_WINDOWS.IEXPLORER)) {
            while (!field.getAttribute("value").equalsIgnoreCase("")) {
                field.sendKeys("\u0008");
            }
        } else {
            field.clear();
        }
        sleep(1000);
        field.sendKeys(value);
        sleep(1000);
        return (W) this;
    }

    /**
     * Before filling input value select all text from input field.
     *
     * @param element The web element.
     * @param value The value to be input.
     * @return generic page W
     * @exception Throws execption if any error occurs.
     */
    public W fillInputBySelectingAll(WebElement element, String value) throws Exception {
        dynamicWaitForSpecificTime(element);
        if (!PropertyLoader.loadProperty("browser").equals("safari")) {
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
        } else {
            element.sendKeys(Keys.chord(Keys.COMMAND, "a"), value);
        }
        sleep(1000);
        return (W) this;
    }

    /**
     * Select all text from input field.
     *
     * @param element The web element.
     * @return generic page W
     * @exception Throws execption if any error occurs.
     */
    public W selectAllTextIntoInputField(WebElement element) throws Exception {
        dynamicWaitForSpecificTime(element);
        if (!PropertyLoader.loadProperty("browser").equals("safari")) {
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        } else {
            element.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        }
        return (W) this;
    }

    /**
     * Fill input field by java script executor.
     *
     * @param field The web element.
     * @param value The input value.
     * @return generic page W.
     * @throws Exception Throws exception if any error occurs.
     */
    public W fillInputFieldByJavaScript(WebElement field, String value) throws Exception {
        waitForElement(field, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));

        clearInputFieldByJavaScript(field);

        JavascriptExecutor js = (JavascriptExecutor) driverW;
        js.executeScript("arguments[0].value = '" + value + "'", field);
        sleep(1000);

        return (W) this;
    }

    /**
     * Click any Input filed for selecting.
     *
     * @param field The webelement.
     * @return generic page W.
     * @throws Exception Throws exception if any error occurs.
     */
    public W selectInputField(WebElement field) throws Exception {
        field.click();
        Thread.sleep(3000);
        return (W) this;
    }

    /**
     * Standard/Generic select web element creation
     *
     * @param field The webelement.
     * @return Select web element.
     * @exception Throws exception if any error occurs.
     */
    public Select createSelectElement(WebElement field) throws Exception {
        waitForElement(field, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));
        return new Select(field);
    }

    /**
     * Standard/Generic button/link web element click operation
     *
     * @param field The webelement.
     * @return generic page W.
     * @exception Throws exception if any error occurs.
     */
    public W performClick(WebElement field) throws Exception {
        return performClick(field, null);
    }

    /**
     * Standard/Generic button/link web element click operation with wait for
     * additional web element
     *
     * @param clickingField The webelement.
     * @param waitForField The webelement.
     * @return generic page W.
     * @exception Throws exception if any error occurs.
     */
    public W performClick(WebElement clickingField, WebElement waitForField) throws Exception {
        waitForElement(clickingField, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));
        clickingField.click();
        if (waitForField != null) {
            waitForElement(clickingField, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));
        }
        sleep(1000);
        return (W) this;
    }

    /**
     * Standard/Generic web element finding for another web element
     *
     * @param field The webelement.
     * @param xPath The xPath.
     * @return List<WebElement> The list of the list item.
     * @exception Throws exception if any error occurs.
     */
    public WebElement findSubWebElementByXPath(WebElement field, String xPath) throws Exception {
        return field.findElement(By.xpath(xPath));
    }

    /**
     * Standard/Generic web element finding for another web element
     *
     * @param field The webelement.
     * @param tagName The tagName.
     * @return List<WebElement> The list of the list item.
     * @exception Throws exception if any error occurs.
     */
    public List<WebElement> findSubWebElementListByTagName(WebElement field, String tagName) throws Exception {
        return field.findElements(By.tagName(tagName));
    }

    /**
     * Find webelements for list item.
     *
     * @param field The webelement.
     * @param xPath The xpath.
     * @return List<WebElement> The list of the list item.
     * @exception Throws exception if any error occurs.
     */
    public List<WebElement> findSubWebElementListByXpath(WebElement field, String xPath) throws Exception {
        return driverW.findElements(By.xpath(xPath));
    }

    /**
     * Standard/Generic get text method from a web element
     *
     * @param field The webelement.
     * @return String The text of webelement.
     * @exception Throws exception if any error occurs.
     */
    public String getTextFromWebElement(WebElement field) throws Exception {
        return waitForElement(field, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec"))).getText();
    }

    /**
     * Set Thread Sleep.
     *
     * @return W The generic page.
     * @exception Throws exception if any error occurs.
     */

    public W sleep(int milisecond) throws Exception {
        Thread.sleep(milisecond);
        return (W) this;
    }

    /**
     * get current date from the system
     *
     * @param format the format.
     * @exception Throws exception if any error occurs.
     */
    public String getSysCurrentDate(String format) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String formatDate = dateFormat.format(currentDate);
        return formatDate;
    }

    /**
     * get current week from the system
     *
     * @param format1 is like thus "MMM dd".
     * @param format2 is like thus "MMM dd, yyyy".
     * @return string The current week.
     * @exception Throws exception if any error occurs.
     */
    public String getSysCurrentWeek(String format1, String fromat2) throws Exception {
        String week = "";

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        DateFormat df = new SimpleDateFormat(format1);
        week = df.format(c.getTime());
        c.add(Calendar.DATE, 6);
        df = new SimpleDateFormat(fromat2);
        week = week + "-" + df.format(c.getTime());
        return week;
    }

    /**
     * Get value of iFrame.
     *
     * @param element The webelement.
     * @return String The iFrame value.
     * @exception Throws exception if any error occurs.
     */
    public String getIframeText(WebElement element) throws Exception {
        return element.getAttribute("src");
    }

    /**
     * Get attribute value of any element
     *
     * @param element The webelement.
     * @param attrName The attribute name.
     * @return Attribute value of the webelement.
     * @exception Throws exception if any error occurs.
     */
    public String getAttribute(WebElement element, String attrName) throws Exception {
        return element.getAttribute(attrName);
    }

    /**
     * Check any attribute is present or not.
     *
     * @param element The webelement.
     * @param attrName The attribute name.
     * @return boolean Returns true if attribute is found otherwise false.
     * @exception Throws exception if any error occurs.
     */
    public boolean isAttributeExist(WebElement element, String attrName) throws Exception {
        Boolean result = false;
        try {
            String value = element.getAttribute(attrName);
            if (value != null) {
                result = true;
            }
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * Get placeholder value of input field
     *
     * @param element The webelement
     * @return Placeholder value of the input field.
     * @exception Throws exception if any error occurs.
     */
    public String getInputFieldPlaceHolderText(WebElement element) throws Exception {
        return element.getAttribute("placeholder");
    }

    /**
     * Get input value of input field
     *
     * @param element The webelement.
     * @return Input value of the input field.
     * @exception Throws exception if any error occurs.
     *
     */
    public String getInputFieldText(WebElement element) throws Exception {
        return element.getAttribute("value");
    }

    /**
     * Check if input field has placeholder
     *
     * @param element The webelement.
     * @return boolean Return true if input filed has placeholder, otherwise return false.
     * @exception Throws exception if any error occurs.
     */
    public boolean isInputFieldPlaceHolderExist(WebElement element) throws Exception {
        if (!getInputFieldPlaceHolderText(element).equalsIgnoreCase("")
                && getInputFieldText(element).equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if input field has placeholder
     *
     * @param element The webelement.
     * @param placeholder The placeholder text.
     * @return boolean Return true if input filed has placeholder, otherwise return false.
     * @exception Throws exception if any error occurs.
     */
    public boolean isInputFieldPlaceHolderExist(WebElement element, String placeholder) throws Exception {
        if (!getInputFieldPlaceHolderText(element).equalsIgnoreCase("")
                && getInputFieldText(element).equalsIgnoreCase("")
                && getInputFieldPlaceHolderText(element).equalsIgnoreCase(placeholder)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * clear any input field. Note: element.clear() is not supported for
     * Internet Explorer browser.
     *
     * @param element The webelement.
     * @return W The generic page.
     * @exception Throws exception if any error occurs.
     */
    public W clearInputField(WebElement element) throws Exception {
        while (!element.getAttribute("value").equalsIgnoreCase("")) {
            element.sendKeys("\u0008");
        }
        return (W) this;
    }

    /**
     * clear any input field.
     *
     * @param inputElement The webelement input field.
     * @param backspaceButton The webelement that clear input field.
     * @return W The generic page.
     */
    public W clearInputField(WebElement inputElement, WebElement backspaceButton) throws Exception {
        while (!inputElement.getAttribute("value").equalsIgnoreCase("")) {
            performClickByJavaScript(backspaceButton).sleep(1000);
        }
        return (W) this;
    }

    /**
     * Clear input field using java script.
     *
     * @param field The web element.
     * @return generic page W.
     * @throws Exception Throws exception if any error occurs.
     */
    public W clearInputFieldByJavaScript(WebElement field) throws Exception {
        waitForElement(field, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));
        JavascriptExecutor js = (JavascriptExecutor) driverW;
        js.executeScript("arguments[0].value = '" + "" + "'", field);
        Thread.sleep(1000);
        return (W) this;
    }

    /**
     * Check if a WebElement has the focus
     *
     * @param driver The webdriver
     * @param element The webelement
     * @return boolean Return true if the webelement has the focus, otherwisefalse
     */
    public boolean isWebElementFocused(WebDriver driver, WebElement element) {
        if (element.equals(driver.switchTo().activeElement())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if a WebElement is enable
     *
     * @param element The webelement.
     * @return boolean Return true if the webelement is enable, otherwise false.
     * @throws Exception Returns exception if any error occurs.
     */
    public boolean isWebElementEnabled(WebElement element) throws Exception {
        return element.isEnabled();
    }

    /**
     * Click at any coordinate of the screen
     *
     * @param driver The webdriver
     * @param X The X coordinate
     * @param Y The Y coordinate
     * @return W The instance of this class.
     * @throws Exception Returns exception if any error occurs.
     */
    public W clickAtCoordinate(WebDriver driver, int X, int Y) throws Exception {
        if (isEnvironmentType(ENVIRONMENT_WINDOWS.SAFARI)) {
        } else if (isOsType(OS.IOS)) {
        } else {
            Actions builder = new Actions(driver);
            builder.moveByOffset(X, Y).click().build().perform();
        }
        return (W) this;
    }

    /**
     * refresh any web page
     */
    public void refreshPage() {
        driverW.navigate().refresh();
    }

    /**
     * Get co-ordinate of webelement
     *
     * @param element The web element.
     * @return co-ordinates(as String)
     * @throws Exception Returns exception if any error occurs.
     */
    public String getCoOrdinate(WebElement element) throws Exception {
        Point point = element.getLocation();
        int xcord = point.getX();
        int ycord = point.getY();
        return "" + xcord + "," + ycord;
    }

    /**
     * Scroll page down to the specific element.
     *
     * @param element The web element.
     * @return W The instance of this class.
     * @throws Exception Returns exception if any error occurs.
     */
    public W scrollPageTopToDown(WebElement element) throws Exception {
        JavascriptExecutor jse = (JavascriptExecutor) driverW;
        jse.executeScript("window.scrollBy(0,250)", "");
        return (W) this;
    }

    /**
     * Scroll page Up to the specific element.
     *
     * @param element The web element
     * @return W The instance of this class.
     * @throws Exception Returns exception if any error occurs.
     */
    public W scrollPageDownToTop(WebElement element) throws Exception {

        JavascriptExecutor jse = (JavascriptExecutor) driverW;
        jse.executeScript("window.scrollBy(0,-250)", "");
        return (W) this;
    }

    /**
     * Scroll page right to left
     *
     * @param element The web element
     * @return W The instance of this class.
     * @throws Exception Returns exception if any error occurs.
     */
    public W scrollPageRightToLeft(WebElement element) throws Exception {

        JavascriptExecutor jse = (JavascriptExecutor) driverW;
        jse.executeScript("arguments[0].scrollIntoView()", element);
        return (W) this;
    }

    /**
     * Drag and drop
     *
     * @param dragFrom The web element.
     * @param dragTo The web element.
     * @return W The instance of the generic class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W dragAndDrop(WebElement dragFrom, WebElement dragTo) throws Exception {
        Actions builder = new Actions(driverW);
        Action dragAndDrop = builder.clickAndHold(dragFrom).moveToElement(dragTo).release(dragTo).build();
        dragAndDrop.perform();
        return (W) this;
    }

    /**
     * Drag and drop into listview
     *
     * @param dragFrom The web element.
     * @param dragTo The web element.
     * @return W The instance of the generic class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W dragAndDropIntoListView(WebElement dragFrom, WebElement dragTo) throws Exception {
        Actions builder = new Actions(driverW);
        builder.clickAndHold(dragFrom);
        builder.moveToElement(dragTo, 5, 5);
        builder.perform();
        Thread.sleep(1000);
        builder.release(dragTo);
        builder.perform();
        return (W) this;
    }

    /**
     * Drag wait and drop
     *
     * @param dragFrom The webelement of drag from.
     * @param dragTo The webelement of drag to.
     * @return W The instance of the generic class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W dragWaitAndDrop(WebElement dragFrom, WebElement dragTo) throws Exception {
        Actions builder = new Actions(driverW);
        builder.clickAndHold(dragFrom).perform();
        Thread.sleep(1000);
        builder.moveToElement(dragTo).release(dragTo).perform();
        return (W) this;
    }

    /**
     * Swipe backward
     *
     * @param swipeElement The web element.
     * @return W The instance of the generic class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W swipeBackward(WebElement swipeElement) throws Exception {
        Actions builder = new Actions(driverW);
        builder.dragAndDropBy(swipeElement, 100, 0).build().perform();
        return (W) this;
    }

    /**
     * Swipe forward
     *
     * @return W The instance of the generic class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W swipeForward() throws Exception {
        Actions builder = new Actions(driverW);
        builder.clickAndHold().moveByOffset(-100, 0).release().perform();
        return (W) this;
    }

    /**
     * Get previous date from current date. please make sure that the given date
     * and the date format are aligned.
     *
     * @param dateFormat The desire date format.
     * @param currentDate The current date.
     * @return String The previous date of current date.
     * @throws Exception Throws exception if any error occurs.
     */
    public String getPreviousDateFromCurrentDate(String dateFormat, String currentDate) throws Exception {
        SimpleDateFormat dateFomater = new SimpleDateFormat(dateFormat);
        Date myDate = dateFomater.parse(currentDate);
        Date previousDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        return dateFomater.format(previousDate);
    }

    /**
     * Get next date from current date. please make sure that the given date and
     * the date format are aligned.
     *
     * @param dateFormat The desire date format.
     * @return String The previous date of current date.
     * @throws Exception Throws exception if any error occurs.
     */
    public String getNextDateFromCurrentDate(String dateFormat) throws Exception {
        SimpleDateFormat dateFomater = new SimpleDateFormat(dateFormat);
        Date previousDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        return dateFomater.format(previousDate);
    }

    /**
     * Get previous Month.
     *
     * @param dateFormat The desire date format.
     * @param currentDate The current date.
     * @return String The previous Month.
     * @throws Exception Throws exception if any error occurs.
     */
    public String getPrevMonth(String curDate, String curdateDateFormat) throws Exception {
        Date currentDate = new Date();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat(curdateDateFormat);
        String formatDate = dateFormat.format(calendar.getTime());
        return formatDate;
    }

    /**
     * Get previous Year.
     *
     * @param dateFormat The desire date format.
     * @param currentDate The current date.
     * @return String The previous Year.
     * @throws Exception Throws exception if any error occurs.
     */
    public String getPrevYear(String curDate, String curdateDateFormat) throws Exception {
        Date currentDate = new Date();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat(curdateDateFormat);
        String formatDate = dateFormat.format(calendar.getTime());
        return formatDate;
    }

    /**
     * swipe left to right
     *
     * @param element The webelement.
     * @return W The instance of this class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W swipeLeftToRight(WebElement element) throws Exception {
        Actions builder = new Actions(driverW);
        builder.dragAndDropBy(element, 100, 0).build().perform();
        return (W) this;
    }

    /**
     * swipe right to left
     *
     * @param element The webelement.
     * @return W The instance of this class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W swipeRightToLeft(WebElement element) throws Exception {
        Actions builder = new Actions(driverW);
        builder.dragAndDropBy(element, -100, 0).build().perform();
        return (W) this;
    }

    /**
     * Click element.
     *
     * @param element The web element.
     * @return W The instance of the generic class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W performClickByAction(WebElement element) throws Exception {
        Actions actions = new Actions(driverW);
        actions.moveToElement(element).click().perform();
        sleep(1000);
        return (W) this;
    }

    /**
     * Double Click on web element.
     *
     * @param element The web element.
     * @return W The instance of the generic class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W performDoubleClickByAction(WebElement element) throws Exception {
        Actions actions = new Actions(driverW);
        actions.doubleClick(element).perform();
        Thread.sleep(1000);
        return (W) this;
    }

    /**
     * Double Click on web element.
     *
     * @param element The web element.
     * @return W The instance of the generic class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W performDoubleClick(WebElement element) throws Exception {
        for (int i = 0; i <= 1; i++) {
            performClick(element);
            sleep(1000);
        }
        return (W) this;
    }

    /**
     * Click element.
     *
     * @param element The web element.
     * @return W The instance of the generic class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W performClickByJavaScript(WebElement element) throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driverW;
        js.executeScript("arguments[0].click();", element);
        sleep(1000);
        return (W) this;
    }

    /**
     * Click element.
     *
     * @param css CSS for web element.
     * @return W The instance of the generic class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W performClickByCss(String css) throws Exception {
        while (true) {
            try {
                driverW.findElement(By.cssSelector(css)).click();
                sleep(1000);
                break;
            } catch (Throwable e) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return (W) this;
    }

    /**
     * Click element.
     *
     * @param element The web element.
     * @return W The instance of the generic class.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public W performClickUntilFound(WebElement element) throws Exception {
        while (true) {
            try {
                element.click();
                sleep(1000);
                break;
            } catch (Throwable e) {
                try {
                    sleep(1000);
                    System.out.println("Attemped to click");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return (W) this;
    }

    /**
     * Click element.
     *
     * @param element The web element.
     * @param number Click number.
     * @return boolean Return true if clickable otherwise false.
     * @exception Exception Throws exceptions if any error occurs.
     */
    public boolean performClickForSpecificTime(WebElement element, int number) throws Exception {
        for (int i = 1; i <= number; i++) {
            try {
                element.click();
                sleep(1000);
                break;
            } catch (WebDriverException driverException) {
                System.out.println("Click on element failed. Attempt: " + i + "/" + number);
                sleep(1000);
            }
            if (i == number) {
                Assert.fail("Failed to click " + number + " times");
                return false;
            }
        }
        return true;
    }

    /**
     * Click element.
     *
     * @param inputData The input string.
     * @return boolean Return true if the input string is numeric.
     */
    public boolean isNumeric(String inputData) {
        return inputData.matches("[-+]?\\d+(\\.\\d+)?");
    }

    /**
     * This method is used for getting web element by xpath.
     *
     * @param xPath The xpath of web element.
     * @return WebElement The web element.
     */
    public WebElement getWebElementByXpath(String xPath) {
        WebElement element = driverW.findElement(By.xpath(xPath));
        return element;
    }

    /**
     * This method is used for getting web element by text.
     *
     * @param text The text by which we want to search web element.
     * @return WebElement The web element.
     */
    public WebElement getWebElementByText(String text) {
        String xPath = "//div[contains(text(),'" + text + "')]";
        WebElement element = driverW.findElement(By.xpath(xPath));
        return element;
    }

    /**
     * give input from keyboard.
     *
     * @param Keys The keyboard input.
     * @param WebElement The web element.
     * @return W The instance of this class.
     * @throws Exception Throws exception if any error occurs.
     */
    public W giveInputFromKeyboard(Keys keyFromNumberPad, WebElement element) throws Exception {
        element.sendKeys(keyFromNumberPad);
        Thread.sleep(1000);
        return (W) this;
    }

    /**
     * This method is used for hiding keyboard on smartphone. Here a blank click
     * is used at an arbitary coordinate for hiding keyboard.
     *
     * @throws Exception Throws exception if any error occurs.
     */
    public void clickOutSideForHidingKeyboard() throws Exception {
        String type = PropertyLoader.loadProperty("testType");
        if (type.equalsIgnoreCase("MOBILE")) {
            clickAtCoordinate(driverW, 10, 100).sleep(1000);
        }
    }

    /**
     * Check the string is in lower case.
     *
     * @return boolean Returns true if the letters are in lower case, otherwise return false.
     * @throws Exception Throws exception if any error occurs.
     */
    public boolean isInLowerCase(String input) throws Exception {
        String regex = "^(?=.*?[\\p{L}&&[^\\p{Lu}]])(?=.*?\\d).*$";
        return input.matches(regex);
    }

    /**
     * Convert date from one format to another.
     *
     * @param inputDate The date that is to be re-formated.
     * @param inputDateFormat The input date format.
     * @param outputDateFormat The output date format.
     * @return String reformated date.
     * @throws Exception Throws exception if any error occurs.
     */
    public String convertDate(String inputDate, String inputDateFormat, String outputDateFormat) throws Exception {
        String reformattedStr = "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat);
            reformattedStr = outputFormat.format(inputFormat.parse(inputDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reformattedStr;
    }

    /**
     * get All list items text.
     *
     * @param list The webelement of list view.
     * @return ArrayList<String> Return array list of list view items text.
     * @throws Exception Throws exception if any error occurs.
     */
    public ArrayList<String> getAllListItemsText(WebElement list) throws Exception {
        waitForElement(list, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));

        ArrayList<String> allItems = new ArrayList<>();
        List<WebElement> rows = list.findElements(By.tagName("li"));
        for (WebElement row : rows) {
            allItems.add(row.getText());
        }
        return allItems;
    }

    /**
     * get All list items web element.
     *
     * @param list The webelement of list view.
     * @return List<WebElement> Return array list of list view items weblement.
     * @throws Exception Throws exception if any error occurs.
     */
    public List<WebElement> getAllListItemsWebElement(WebElement list) throws Exception {
        waitForElement(list, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));

        List<WebElement> rows = list.findElements(By.tagName("li"));
        return rows;
    }

    /**
     * Check item is exist in the list view.
     *
     * @param sText The contain text of the webelement.
     * @return boolean Return true if item found otherwise return false.
     * @throws Exception Throws exception if any error occurs.
     */
    public boolean isItemExistInListView(WebElement list, String sText) throws Exception {
        waitForElement(list, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));

        int rowIndex = 0;
        boolean found = false;
        List<WebElement> rows = list.findElements(By.tagName("li"));
        for (WebElement row : rows) {
            if (StringUtils.contains(row.getText(), sText)) {
                rowIndex = rows.indexOf(row);
                rowIndex = rowIndex + 1;
                found = true;
                break;
            }
        }
        if (!found) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get search item web element from listview.
     *
     * @param itemName The contain text of the webelement.
     * @return WebElement The webelement of the search item.
     * @throws Exception Throws exception if any error occurs.
     */
    public WebElement getWebElementOfListItem(WebElement list, String itemName) throws Exception {
        waitForElement(list, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));

        List<WebElement> rows = list.findElements(By.tagName("li"));
        for (WebElement row : rows) {
            if (StringUtils.contains(row.getText(), itemName)) {
                return row;
            }
        }
        return null;
    }

    /**
     * Click search item from listview.
     *
     * @param itemName The contain text of the webelement.
     * @return WebElement The webelement of the search item.
     * @throws Exception Throws exception if any error occurs.
     */
    public boolean performClickOnListItem(WebElement list, String itemName) throws Exception {
        waitForElement(list, Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")));

        boolean found = false;
        List<WebElement> rows = list.findElements(By.tagName("li"));
        for (WebElement row : rows) {
            if (StringUtils.contains(row.getText(), itemName)) {
                found = true;
                performClickByJavaScript(row).sleep(1000);
                break;
            }
        }

        if (!found) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if the test type is webview or browser.
     *
     * @return boolean Returns true if it is webview otherwise returns false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean isWebView() throws Exception {
        String testType = PropertyLoader.loadProperty("testType");
        if (testType.equalsIgnoreCase("WEBVIEW")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get all digits of any number.
     *
     * @param number The number.
     * @return ArrayList<Integer> Returns the arraylist of the digits of number.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public ArrayList<Integer> getDigitsOfAnyNumber(int number) throws Exception {
        ArrayList<Integer> digits = new ArrayList<Integer>();

        while (number > 0) {
            int d = number / 10;
            int k = number - d * 10;
            number = d;
            digits.add(k);
        }

        return getReverseArrayList(digits);
    }

    /**
     * Get the arraylist of reverse order.
     *
     * @param ArrayList<T> The arraylist.
     * @return ArrayList<T> Returns the arraylist of reverse order.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public <T> ArrayList<T> getReverseArrayList(ArrayList<T> list) throws Exception {
        int length = list.size();
        ArrayList<T> result = new ArrayList<T>(length);

        for (int i = length - 1; i >= 0; i--) {
            result.add(list.get(i));
        }

        return result;
    }

    /**
     * find the given element.
     *
     * @param element The expected webelement.
     * @return boolean Returns true if element found otherwise returns false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean findGivenElement(WebElement element) throws Exception {
        try {
            if (element.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check the device type.
     *
     * @param os type of os.
     * @return boolean Returns true if it is OS otherwise returns false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean isOsType(OS os) throws Exception {
        String ostype = PropertyLoader.loadProperty("osType");
        if (os.toString().toLowerCase().equals(ostype.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check the device type.
     *
     * @param os it may be WINDOWS, LINUX, MAC.
     * @return boolean Returns true if it is OS otherwise returns false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean isEnvironmentType(ENVIRONMENT_WINDOWS environment) throws Exception {
        String environmentType = PropertyLoader.loadProperty("environmentType");
        if (environment.toString().toLowerCase().equals(environmentType.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Scroll into view by JavaScript.
     *
     * @param element The desire webelement.
     * @return W The instance of current page.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public W scrollInoViewByJavaScript(WebElement element) throws Exception {
        JavascriptExecutor je = (JavascriptExecutor) driverW;
        je.executeScript("arguments[0].scrollIntoView(true);", element);
        sleep(2000);
        return (W) this;
    }

    /**
     * Scroll into view by coordinate.
     *
     * @param element The desire webelement.
     * @return W The instance of current page.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public W scrollInoViewByCoordinate(WebElement element) throws Exception {
        Coordinates coordinate = ((Locatable) element).getCoordinates();
        coordinate.onPage();
        coordinate.inViewPort();
        sleep(2000);
        return (W) this;
    }
}