package com.reversecoder.automationtemplate.util;

import java.util.Properties;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class PropertyLoader {
    public static String loadProperty(String name) throws Exception {
        return loadProperty(name, System.getProperty("application.properties", Constants.APPLICATION_PROPERTIES));
    }

    public static String loadErrorProperty(String name) throws Exception {
        return loadProperty(name, System.getProperty("application.properties", Constants.ERROR_PROPERTIES));
    }

    public static String loadProperty(String name, String fromResource) throws Exception {
        Properties props = new Properties();
        props.load(PropertyLoader.class.getResourceAsStream(fromResource));

        return props.getProperty(name);
    }

    public static Boolean isScreenShotEnable() throws Exception {
        try {
            String enableScreenshot = PropertyLoader.loadProperty(Constants.ENABLE_SS_KEY);
            return enableScreenshot.equals("1") || enableScreenshot.equalsIgnoreCase("true");
        } catch (Exception e) {
            return false;
        }
    }

}