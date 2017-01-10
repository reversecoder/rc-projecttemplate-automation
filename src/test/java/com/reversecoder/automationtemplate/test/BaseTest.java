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

public class BaseTest {

	private final File appDir = new File(System.getProperty("user.dir"));
	private final File androidApp = new File(appDir, getAppPath());
	public static WebDriver driverWeb;
	DesiredCapabilities capabilities;

	public enum BROWSER {
		FIREFOX, CHROME, IEXPLORER, SAFARI
	}

	public enum TEST {
		BROWSER_DESKTOP, BROWSER_MOBILE, WEBVIEW, NATIVE
	}

	public enum DEVICE {
		ANDROID, IOS
	}

	public enum OS {
		WINDOWS, LINUX, MAC
	}

	public WebDriver getDriver() throws Exception {
		if (isTestType(TEST.BROWSER_DESKTOP)) {
			driverWeb = getDriverBrowserDesktop();
		} else if (isTestType(TEST.BROWSER_MOBILE)) {
			driverWeb = getDriverBrowserMobile();
		} else if (isTestType(TEST.WEBVIEW)) {
			driverWeb = getDriverWebView();
		} else if (isTestType(TEST.NATIVE)) {
			driverWeb = getDriverNative();
		}
		return driverWeb;
	}

	public WebDriver getDriverBrowserDesktop() throws Exception {
		if (driverWeb == null) {
			String browser = PropertyLoader.loadProperty("browserType").toUpperCase();
			driverWeb = getBrowser(BROWSER.valueOf(browser));
			driverWeb.manage().timeouts().implicitlyWait(
					Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")), TimeUnit.SECONDS);
			driverWeb.manage().window().maximize();
			driverWeb.manage().deleteAllCookies();
			driverWeb.get(PropertyLoader.loadProperty("urlSite"));
		}
		return driverWeb;
	}

	public WebDriver getDriverBrowserMobile() throws Exception {
		if (driverWeb == null) {
			URL url = new URL(PropertyLoader.loadProperty("appiumHost"));
			if (isDeviceType(DEVICE.ANDROID)) {
				driverWeb = new AndroidDriver(url, this.getCapabilities());
			} else {
				driverWeb = new IOSDriver(url, this.getCapabilities());
			}
			driverWeb.manage().timeouts().implicitlyWait(
					Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")), TimeUnit.SECONDS);
			driverWeb.manage().deleteAllCookies();
			driverWeb.get(PropertyLoader.loadProperty("urlSite"));
		}
		return driverWeb;
	}

	/**
	 * Driver for android application.
	 * 
	 * @return driver Appium
	 * @throws Exception
	 */
	public WebDriver getDriverWebView() throws Exception {
		if (driverWeb == null) {
			URL url = new URL(PropertyLoader.loadProperty("appiumHost"));
			if (isDeviceType(DEVICE.ANDROID)) {
				driverWeb = new AndroidDriver(url, this.getCapabilities());
			} else {
				driverWeb = new IOSDriver(url, this.getCapabilities());
			}
			driverWeb.manage().timeouts().implicitlyWait(
					Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")), TimeUnit.SECONDS);
			toggleContext(Context.WEBVIEW);

		}
		return driverWeb;
	}

	/**
	 * Driver for android application.
	 * 
	 * @return driver Appium
	 * @throws Exception
	 */
	public WebDriver getDriverNative() throws Exception {
		if (driverWeb == null) {
			URL url = new URL(PropertyLoader.loadProperty("appiumHost"));
			if (isDeviceType(DEVICE.ANDROID)) {
				driverWeb = new AndroidDriver(url, this.getCapabilities());
			} else {
				driverWeb = new IOSDriver(url, this.getCapabilities());
			}
			driverWeb.manage().timeouts().implicitlyWait(
					Integer.valueOf(PropertyLoader.loadProperty("implicit_timeout_sec")), TimeUnit.SECONDS);
			toggleContext(Context.NATIVE);
		}
		return driverWeb;
	}

	public WebDriver getBrowser(BROWSER browser) {
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

	public DesiredCapabilities getCapabilities() throws Exception {
		if (capabilities == null) {
			String UDID = PropertyLoader.loadProperty("udid");
			String DEVICE_NAME = PropertyLoader.loadProperty("deviceName");

			capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
					isDeviceType(DEVICE.ANDROID) ? "Android" : "iOS");
			if (!isTestType(TEST.WEBVIEW) && !isTestType(TEST.NATIVE)) {
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,
						isDeviceType(DEVICE.ANDROID) ? BrowserType.CHROME : "safari");
			}
			if (isDeviceType(DEVICE.IOS)) {
				capabilities.setCapability("udid", UDID);
			}
			if (isTestType(TEST.WEBVIEW) && isDeviceType(DEVICE.IOS)) {
				String BUNDLE_ID = PropertyLoader.loadProperty("bundle_id");
				capabilities.setCapability("bundleId", BUNDLE_ID);
			}
			if (isTestType(TEST.WEBVIEW) || isTestType(TEST.NATIVE)) {
				if (isDeviceType(DEVICE.ANDROID)) {
					String New_Command_Timeout = PropertyLoader.loadProperty("newCommandTimeout");

					capabilities.setCapability("app", androidApp.getAbsolutePath());
					capabilities.setCapability("appPackage", getAppPackage());
					capabilities.setCapability("appActivity", getAppActivity());
					capabilities.setCapability("newCommandTimeout", New_Command_Timeout);
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

	private String getContext(String startsWith, AppiumDriver driverAppium) {
		Set<String> contexts = driverAppium.getContextHandles();
		for (String s : contexts) {
			if (s.startsWith(startsWith))
				return s;
		}
		return null;
	}

	public void toggleContext(Context type) throws Exception {
		AppiumDriver driverAppium = (AppiumDriver) driverWeb;
		Thread.sleep(30000);
		String contextString = getContext(type.getType(), driverAppium);
		if (contextString == null)
			return;
		driverAppium.context(contextString);
	}

	public boolean isKeyboardDisplayed() {
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
	 * @throws Exception
	 *             Throws exceptions if any error occurs.
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
	 * @throws Exception
	 *             Throws exceptions if any error occurs.
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
	 * @return boolean Return true if wifi is enabled otherwise return false.
	 * @throws Exception
	 *             Throws exceptions if any error occurs.
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
	 * @return boolean Return true if wifi is enabled otherwise return false.
	 * @throws Exception
	 *             Throws exceptions if any error occurs.
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
	 * @return boolean Return true if wifi is enabled otherwise return false.
	 * @throws Exception
	 *             Throws exceptions if any error occurs.
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
	 * @throws Exception
	 *             Throws exceptions if any error occurs.
	 */
	public void restoreDownBrowser() throws Exception {
		Dimension n = new Dimension(360, 592);
		getDriverBrowserDesktop().manage().window().setSize(n);
		Thread.sleep(3000);
	}

	/**
	 * Maximize browser.
	 *
	 * @throws Exception
	 *             Throws exceptions if any error occurs.
	 */
	public void maximizeBrowser() throws Exception {
		getDriverBrowserDesktop().manage().window().maximize();
		Thread.sleep(3000);
	}

	/**
	 * Check the browser type.
	 *
	 * @param browser
	 *            it may be FIREFOX, CHROME, IEXPLORER, SAFARI.
	 * @return boolean Returns true if it is BROWSER otherwise returns false.
	 * @throws Exception
	 *             Throws exceptions if any error occurs.
	 */
	public boolean isBrowserType(BROWSER browser) throws Exception {
		String browsertype = PropertyLoader.loadProperty("browserType");
		if (browser.toString().toLowerCase().equals(browsertype.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check the test type.
	 *
	 * @param test
	 *            it may be DESKTOP, WEB, WEBVIEW.
	 * @return boolean Returns true if it is TEST otherwise returns false.
	 * @throws Exception
	 *             Throws exceptions if any error occurs.
	 */
	public boolean isTestType(TEST test) throws Exception {
		String testtype = PropertyLoader.loadProperty("testType");
		if (test.toString().toLowerCase().equals(testtype.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check the device type.
	 *
	 * @param device
	 *            it may be ANDROID, IOS.
	 * @return boolean Returns true if it is DEVICE otherwise returns false.
	 * @throws Exception
	 *             Throws exceptions if any error occurs.
	 */
	public boolean isDeviceType(DEVICE device) throws Exception {
		String devicetype = PropertyLoader.loadProperty("deviceType");
		if (device.toString().toLowerCase().equals(devicetype.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check the device type.
	 *
	 * @param os
	 *            it may be WINDOWS, LINUX, MAC.
	 * @return boolean Returns true if it is OS otherwise returns false.
	 * @throws Exception
	 *             Throws exceptions if any error occurs.
	 */
	public boolean isOsType(OS os) throws Exception {
		String ostype = PropertyLoader.loadProperty("osType");
		if (os.toString().toLowerCase().equals(ostype.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getAppPath(){
		try {
			if(isTestType(TEST.NATIVE)){
				return PropertyLoader.loadProperty("appPathNative");
			}else if(isTestType(TEST.WEBVIEW)){
				return PropertyLoader.loadProperty("appPathWebView");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public String getAppPackage(){
		try {
			if(isTestType(TEST.NATIVE)){
				return PropertyLoader.loadProperty("appPackageNative");
			}else if(isTestType(TEST.WEBVIEW)){
				return PropertyLoader.loadProperty("appPackageWebView");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAppActivity(){
		try {
			if(isTestType(TEST.NATIVE)){
				return PropertyLoader.loadProperty("appActivityNative");
			}else if(isTestType(TEST.WEBVIEW)){
				return PropertyLoader.loadProperty("appActivityWebView");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
