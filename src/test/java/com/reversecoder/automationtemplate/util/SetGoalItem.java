package com.reversecoder.automationtemplate.util;

import org.openqa.selenium.WebElement;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class SetGoalItem extends ListItem {

    public SetGoalItem(String itemName, WebElement itemWebElement, int itemPosition, boolean itemStatus) {
        super(itemName, itemWebElement, itemPosition, itemStatus);
    }

}
