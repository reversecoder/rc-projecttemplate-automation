package com.reversecoder.automationtemplate.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reversecoder.automationtemplate.dto.data.UserDto;
import com.reversecoder.automationtemplate.pages.AndroidPage;
import com.reversecoder.automationtemplate.util.DataParameters;
import com.reversecoder.automationtemplate.util.PropertyLoader;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class AndroidPageTest extends BaseTest {
    private AndroidPage androidPage;

    @Test(priority = 1, dataProviderClass = DataParameters.class, dataProvider = "DP_tc_01_019", groups = {
            "browser_desktop", "browser_mobile", "native_android", "native_ios", "webview_android", "webview_ios" }, enabled = true)
    public void tc_01_019_CheckUiContentsOfAndroidPageTest(UserDto data) throws Exception {
        androidPage = new AndroidPage(getDriver());

        Assert.assertTrue(androidPage.isElementDisplayed(androidPage.btnSubmit),
                PropertyLoader.loadErrorProperty("page.item.is.not.found"));
        Assert.assertTrue(androidPage.isElementDisplayed(androidPage.inputText),
                PropertyLoader.loadErrorProperty("page.item.is.not.found"));

        androidPage.fillInputField(androidPage.inputText, "Hello World");
        androidPage.performClick(androidPage.btnSubmit);
    }
}