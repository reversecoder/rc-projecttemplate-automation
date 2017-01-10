package com.reversecoder.automationtemplate.util;

import org.openqa.selenium.WebElement;

public class ListItem {

	private String itemName = "";
	private WebElement itemWebElement = null;
	private int itemPosition = -1;
	private boolean itemStatus = false;

	public ListItem(String itemName, WebElement itemWebElement, int itemPosition, boolean itemStatus) {
		this.itemName = itemName;
		this.itemWebElement = itemWebElement;
		this.itemPosition = itemPosition;
		this.itemStatus = itemStatus;
	}

	public ListItem(String itemName, WebElement itemWebElement) {
		this(itemName, itemWebElement, -1, false);
		this.itemName = itemName;
		this.itemWebElement = itemWebElement;
		this.itemPosition = itemPosition;
		this.itemStatus = itemStatus;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public WebElement getItemWebElement() {
		return itemWebElement;
	}

	public void setItemWebElement(WebElement itemWebElement) {
		this.itemWebElement = itemWebElement;
	}

	public int getItemPosition() {
		return itemPosition;
	}

	public void setItemPosition(int itemPosition) {
		this.itemPosition = itemPosition;
	}

	public boolean isItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(boolean itemStatus) {
		this.itemStatus = itemStatus;
	}
}
