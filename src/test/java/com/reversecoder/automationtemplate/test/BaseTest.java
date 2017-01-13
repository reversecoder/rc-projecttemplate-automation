package com.reversecoder.automationtemplate.test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.reversecoder.automationtemplate.util.DateUtil;
import com.reversecoder.automationtemplate.util.PropertyLoader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class BaseTest {

    public static WebDriver driverWeb;
    DesiredCapabilities capabilities;

    public enum OS {
        WINDOWS, LINUX, MAC, ANDROID, IOS
    }

    public enum ENVIRONMENT_WINDOWS {
        FIREFOX, CHROME, IEXPLORER, SAFARI
    }

    public enum ENVIRONMENT_LINUX {
        CHROME, FIREFOX
    }

    public enum ENVIRONMENT_MAC {
        CHROME, FIREFOX, SAFARI
    }

    public enum ENVIRONMENT_ANDROID {
        CHROME, NATIVE, WEBVIEW
    }

    public enum ENVIRONMENT_IOS {
        SAFARI, NATIVE, WEBVIEW
    }

    public static enum Context {
        NATIVE("NATIVE"), WEBVIEW("WEBVIEW");

        public final String type;

        Context(String type) {
            this.type = type;
        }

        String getType() {
            return type;
        }
    }

    /**
     * Driver for any platform.
     *
     * @return WebDriver It returns driver as per project requirement.
     * @throws Exception Throws exception if any error occurs.
     */
    public WebDriver getDriver() throws Exception {
        if (isOsType(OS.WINDOWS)) {
            driverWeb = getDriverWindows();
        } else if (isOsType(OS.ANDROID)) {
            driverWeb = getDriverAndroid();
        } else if (isOsType(OS.IOS)) {
            driverWeb = getDriverIos();
        }
        return driverWeb;
    }

    /**
     * Driver for windows application.
     *
     * @return WebDriver The windows driver.
     * @throws Exception Throws exception if any error occurs.
     */
    public WebDriver getDriverWindows() throws Exception {
        if (driverWeb == null) {
            String browser = PropertyLoader.loadProperty("environmentType").toUpperCase();
            driverWeb = getBrowserWindows(ENVIRONMENT_WINDOWS.valueOf(browser));
            driverWeb.manage().timeouts().implicitlyWait(
                    Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")), TimeUnit.SECONDS);
            driverWeb.manage().window().maximize();
            driverWeb.manage().deleteAllCookies();
            driverWeb.get(PropertyLoader.loadProperty("urlSite"));
        }
        return driverWeb;
    }

    /**
     * Driver for android application.
     *
     * @return WebDriver The android driver.
     * @throws Exception Throws exception if any error occurs.
     */
    public WebDriver getDriverAndroid() throws Exception {
        if (driverWeb == null) {
            URL url = new URL(PropertyLoader.loadProperty("appiumHost"));
            driverWeb = new AndroidDriver(url, this.getCapabilities());
            driverWeb.manage().timeouts().implicitlyWait(
                    Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")), TimeUnit.SECONDS);
            if (isAndroidEnvironmentType(ENVIRONMENT_ANDROID.NATIVE)) {
                toggleContext(Context.NATIVE);
            } else if (isAndroidEnvironmentType(ENVIRONMENT_ANDROID.CHROME)) {
                driverWeb.manage().deleteAllCookies();
                driverWeb.get(PropertyLoader.loadProperty("urlSite"));
            } else if (isAndroidEnvironmentType(ENVIRONMENT_ANDROID.WEBVIEW)) {
                toggleContext(Context.WEBVIEW);
            }
        }
        return driverWeb;
    }

    /**
     * Driver for iOs application.
     *
     * @return WebDriver The iOs driver.
     * @throws Exception Throws exception if any error occurs.
     */
    public WebDriver getDriverIos() throws Exception {
        if (driverWeb == null) {
            URL url = new URL(PropertyLoader.loadProperty("appiumHost"));
            driverWeb = new IOSDriver(url, this.getCapabilities());
            driverWeb.manage().timeouts().implicitlyWait(
                    Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")), TimeUnit.SECONDS);
            if (isIosEnvironmentType(ENVIRONMENT_IOS.NATIVE)) {
                toggleContext(Context.NATIVE);
            } else if (isIosEnvironmentType(ENVIRONMENT_IOS.SAFARI)) {
                driverWeb.manage().deleteAllCookies();
                driverWeb.get(PropertyLoader.loadProperty("urlSite"));
            } else if (isIosEnvironmentType(ENVIRONMENT_IOS.WEBVIEW)) {
                toggleContext(Context.WEBVIEW);
            }
        }
        return driverWeb;
    }

    /**
     * Webdriver setting for windows.
     *
     * @param browser The type of browser.
     * @return WebDriver The iOs driver.
     * @throws Exception Throws exception if any error occurs.
     */
    public WebDriver getBrowserWindows(ENVIRONMENT_WINDOWS browser) throws Exception {
        switch (browser) {
        case CHROME:
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            options.addArguments("--disable-user-media-security=true");
            options.addArguments("--use-fake-ui-for-media-stream");
            options.addArguments("--allow-running-insecure-content");
            options.addArguments("--use-fake-device-for-media-stream");
            driverWeb = new ChromeDriver(options);
            break;
        case IEXPLORER:
            System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
            DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
            caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            driverWeb = new InternetExplorerDriver(caps);
            break;
        case SAFARI:
            SafariOptions opts = new SafariOptions();
            opts.setUseCleanSession(true);
            driverWeb = new SafariDriver(opts);
            break;
        default:
            driverWeb = new FirefoxDriver();
            break;
        }
        return driverWeb;
    }

    /**
     * Capabilities setting for automation.
     *
     * @return DesiredCapabilities Return desired capabilities for specific type of test.
     * @throws Exception Throws exception if any error occurs.
     */
    public DesiredCapabilities getCapabilities() throws Exception {
        if (capabilities == null) {
            String UDID = PropertyLoader.loadProperty("udid");
            String DEVICE_NAME = PropertyLoader.loadProperty("deviceName");

            capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, isOsType(OS.ANDROID) ? "Android" : "iOS");

            if (isOsType(OS.ANDROID)) {
                if (isAndroidEnvironmentType(ENVIRONMENT_ANDROID.CHROME)) {
                    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
                } else if (isAndroidEnvironmentType(ENVIRONMENT_ANDROID.WEBVIEW)
                        || isAndroidEnvironmentType(ENVIRONMENT_ANDROID.NATIVE)) {
                    File appDir = new File(System.getProperty("user.dir"));
                    File androidApp = new File(appDir, getAppPath());
                    String New_Command_Timeout = PropertyLoader.loadProperty("newCommandTimeout");

                    capabilities.setCapability("app", androidApp.getAbsolutePath());
                    capabilities.setCapability("appPackage", getAppPackage());
                    capabilities.setCapability("appActivity", getAppActivity());
                    capabilities.setCapability("newCommandTimeout", New_Command_Timeout);
                }
            }

            if (isOsType(OS.IOS)) {
                capabilities.setCapability("udid", UDID);
                if (isIosEnvironmentType(ENVIRONMENT_IOS.WEBVIEW)) {
                    String BUNDLE_ID = PropertyLoader.loadProperty("bundle_id");
                    capabilities.setCapability("bundleId", BUNDLE_ID);
                } else if (isIosEnvironmentType(ENVIRONMENT_IOS.SAFARI)) {
                    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.SAFARI);
                }
            }
        }

        return capabilities;
    }

    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass() throws Exception {
        System.out.println("[" + new DateUtil().toDateTimeStr() + "] " + this.getClass().getName());
        System.out.println("[" + new DateUtil().toDateTimeStr() + "] ............................");
    }

    @BeforeMethod(alwaysRun = true)
    public void baseBeforeMethod(Method method) throws Exception {
        System.out.println("[" + new DateUtil().toDateTimeStr() + "] " + method.getName());
    }

    @AfterSuite(alwaysRun = true)
    public void tearSuite() throws Exception {
        if (driverWeb != null) {
            driverWeb.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void closeBrowser() {
        if (driverWeb != null) {
            driverWeb.quit();
            driverWeb = null;
        }
    }

    /**
     * Return the context.
     *
     * @param startsWith Text of context type.
     * @return driverAppium The appium driver.
     * @throws Exception Throws exception if any error occurs.
     */
    public String getContext(String startsWith, AppiumDriver driverAppium) throws Exception {
        Set<String> contexts = driverAppium.getContextHandles();
        for (String s : contexts) {
            if (s.startsWith(startsWith))
                return s;
        }
        return null;
    }

    /**
     * Switching among contexts.
     *
     * @param type The context type we want to switch.
     * @throws Exception Throws exception if any error occurs.
     */
    public void toggleContext(Context type) throws Exception {
        AppiumDriver driverAppium = (AppiumDriver) driverWeb;
        Thread.sleep(30000);
        String contextString = getContext(type.getType(), driverAppium);
        if (contextString == null)
            return;
        driverAppium.context(contextString);
    }

    /**
     * Check the keyboar is displayed or not.
     *
     * @return boolean Returns true if keyboard is displayed otherwise returns false.
     * @throws Exception Throws exception if any error occurs.
     */
    public boolean isKeyboardDisplayed() throws Exception {
        AppiumDriver driverAppium = (AppiumDriver) driverWeb;
        try {
            driverAppium.hideKeyboard();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Enable or disable wifi of android device.
     *
     * @return boolean Return true if wifi is enabled otherwise return false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean enableWifi(boolean enable) throws Exception {
        toggleContext(Context.NATIVE);
        AndroidDriver androidDriver = (AndroidDriver) driverWeb;
        NetworkConnectionSetting networkConnection = androidDriver.getNetworkConnection();

        networkConnection.setData(enable);
        networkConnection.setWifi(enable);
        androidDriver.setNetworkConnection(networkConnection);

        boolean isWifiEnabled = false;
        if (enable && networkConnection.wifiEnabled()) {
            isWifiEnabled = true;
        } else if (!enable && !networkConnection.wifiEnabled()) {
            isWifiEnabled = true;
        } else {
            isWifiEnabled = false;
        }
        toggleContext(Context.WEBVIEW);
        return isWifiEnabled;
    }

    /**
     * Check if wifi is enabled or disable.
     *
     * @return boolean Return true if wifi is enabled otherwise return false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean isWifiEnabled() throws Exception {
        toggleContext(Context.NATIVE);
        AndroidDriver androidDriver = (AndroidDriver) driverWeb;
        NetworkConnectionSetting networkConnection = androidDriver.getNetworkConnection();
        boolean isWifiEnabled = networkConnection.wifiEnabled();
        toggleContext(Context.WEBVIEW);
        return isWifiEnabled;
    }

    /**
     * Enable Airplane mode of android device.
     *
     * @return boolean Return true if airPlane mode is enabled otherwise return false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public void enableAirplaneMode() throws Exception {
        toggleContext(Context.NATIVE);
        AndroidDriver androidDriver = (AndroidDriver) driverWeb;
        NetworkConnectionSetting networkConnection = androidDriver.getNetworkConnection();
        networkConnection.setAirplaneMode(true);
        androidDriver.setNetworkConnection(networkConnection);
        toggleContext(Context.WEBVIEW);
        Thread.sleep(3000);
    }

    /**
     * Disable Airplane mode of android device.
     *
     * @return boolean Return true if airPlane mode is enabled otherwise return false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public void disableAirplaneMode() throws Exception {
        toggleContext(Context.NATIVE);
        AndroidDriver androidDriver = (AndroidDriver) driverWeb;
        NetworkConnectionSetting networkConnection = androidDriver.getNetworkConnection();
        networkConnection.setAirplaneMode(false);
        networkConnection.setWifi(true);
        androidDriver.setNetworkConnection(networkConnection);
        toggleContext(Context.WEBVIEW);
        Thread.sleep(3000);
    }

    /**
     * Check if airPlane mode is enabled or disable.
     *
     * @return boolean Return true if airPlane mode is enabled otherwise return false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean isAirPalenModeEnabled() throws Exception {
        toggleContext(Context.NATIVE);
        AndroidDriver androidDriver = (AndroidDriver) driverWeb;
        NetworkConnectionSetting networkConnection = androidDriver.getNetworkConnection();
        boolean isAirPlaneModeEnabled = networkConnection.airplaneModeEnabled();
        toggleContext(Context.WEBVIEW);
        return isAirPlaneModeEnabled;
    }

    /**
     * Restore down browser.
     *
     * @throws Exception Throws exceptions if any error occurs.
     */
    public void restoreDownBrowser() throws Exception {
        Dimension n = new Dimension(360, 592);
        getDriverWindows().manage().window().setSize(n);
        Thread.sleep(3000);
    }

    /**
     * Maximize browser.
     *
     * @throws Exception Throws exceptions if any error occurs.
     */
    public void maximizeBrowser() throws Exception {
        getDriverWindows().manage().window().maximize();
        Thread.sleep(3000);
    }

    /**
     * Check the os type.
     *
     * @param os The type of OS.
     * @return boolean Returns true if it is required OS otherwise returns false.
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
     * Check the android environment type.
     *
     * @param environment The type of environment.
     * @return boolean Returns true if it is required environment otherwise returns false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean isAndroidEnvironmentType(ENVIRONMENT_ANDROID environment) throws Exception {
        String environmentType = PropertyLoader.loadProperty("environmentType");
        if (environment.toString().toLowerCase().equals(environmentType.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check the windows environment type.
     *
     * @param environment The type of environment.
     * @return boolean Returns true if it is required environment otherwise returns false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean isWindowsEnvironmentType(ENVIRONMENT_WINDOWS environment) throws Exception {
        String environmentType = PropertyLoader.loadProperty("environmentType");
        if (environment.toString().toLowerCase().equals(environmentType.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check the linux environment type.
     *
     * @param environment The type of environment.
     * @return boolean Returns true if it is required environment otherwise returns false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean isLinuxEnvironmentType(ENVIRONMENT_LINUX environment) throws Exception {
        String environmentType = PropertyLoader.loadProperty("environmentType");
        if (environment.toString().toLowerCase().equals(environmentType.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check the MAC environment type.
     *
     * @param environment The type of environment.
     * @return boolean Returns true if it is required environment otherwise returns false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean isMacEnvironmentType(ENVIRONMENT_MAC environment) throws Exception {
        String environmentType = PropertyLoader.loadProperty("environmentType");
        if (environment.toString().toLowerCase().equals(environmentType.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check the ios environment type.
     *
     * @param environment The type of environment.
     * @return boolean Returns true if it is required environment otherwise returns false.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public boolean isIosEnvironmentType(ENVIRONMENT_IOS environment) throws Exception {
        String environmentType = PropertyLoader.loadProperty("environmentType");
        if (environment.toString().toLowerCase().equals(environmentType.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the application path.
     *
     * @return String Returns the current path of stored app.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public String getAppPath() throws Exception {
        try {
            if (isOsType(OS.ANDROID)) {
                if (isAndroidEnvironmentType(ENVIRONMENT_ANDROID.NATIVE)) {
                    return PropertyLoader.loadProperty("appPathNative");
                } else if (isAndroidEnvironmentType(ENVIRONMENT_ANDROID.WEBVIEW)) {
                    return PropertyLoader.loadProperty("appPathWebView");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the application package.
     *
     * @return String Returns the current app package.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public String getAppPackage() {
        try {
            if (isOsType(OS.ANDROID)) {
                if (isAndroidEnvironmentType(ENVIRONMENT_ANDROID.NATIVE)) {
                    return PropertyLoader.loadProperty("appPackageNative");
                } else if (isAndroidEnvironmentType(ENVIRONMENT_ANDROID.WEBVIEW)) {
                    return PropertyLoader.loadProperty("appPackageWebView");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the application launcher activity.
     *
     * @return String Returns the current app launcher activity.
     * @throws Exception Throws exceptions if any error occurs.
     */
    public String getAppActivity() {
        try {
            if (isOsType(OS.ANDROID)) {
                if (isAndroidEnvironmentType(ENVIRONMENT_ANDROID.NATIVE)) {
                    return PropertyLoader.loadProperty("appActivityNative");
                } else if (isAndroidEnvironmentType(ENVIRONMENT_ANDROID.WEBVIEW)) {
                    return PropertyLoader.loadProperty("appActivityWebView");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
