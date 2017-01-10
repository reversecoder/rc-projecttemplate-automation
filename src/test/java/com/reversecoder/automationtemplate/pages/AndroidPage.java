package com.reversecoder.automationtemplate.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.reversecoder.automationtemplate.dto.page.AndroidPageDto;
import com.reversecoder.automationtemplate.util.CSVHandler;
import com.reversecoder.automationtemplate.util.CSVHandler.ResourceType;

public class AndroidPage extends AbstractPage<AndroidPage> {
	
    public AndroidPageDto androidPageDto;

	@FindBy(xpath = "//android.widget.EditText[contains(@resource-id,'com.bjit.testautomation:id/edt_input')]")
	public WebElement inputText;

	@FindBy(xpath = "//android.widget.Button[contains(@resource-id,'com.bjit.testautomation:id/btn_submit')]")
	public WebElement btnSubmit;

	public AndroidPage(WebDriver driver) throws Exception {
		super(driver);
        androidPageDto = (AndroidPageDto) new CSVHandler().getTestData("androidPage", new AndroidPageDto(), ResourceType.PAGE_DATA)
                .get(0);
	}
}
