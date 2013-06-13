package com.epam.xml.util;

import java.util.ResourceBundle;

public final class ConfigurationManager {
    private static ResourceBundle resource = ResourceBundle.getBundle("com.epam.xml.resources.config");

    public static String getStr(String key) {
        return resource.getString(key);
    }
    
    private ConfigurationManager(){}
}
