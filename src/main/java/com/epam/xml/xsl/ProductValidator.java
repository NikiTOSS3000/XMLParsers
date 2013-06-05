package com.epam.xml.xsl;

import com.epam.xml.util.ConfigurationManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class ProductValidator {

    private static String nameError = "";
    private static String producerError = "";
    private static String colorError = "";
    private static String modelError = "";
    private static String priceError = "";
    private static String dateError = "";

    public static void reset() {
        nameError = "";
        producerError = "";
        colorError = "";
        modelError = "";
        priceError = "";
        dateError = "";
    }

    public static boolean isValid() {
        if ("".equals(nameError) && "".equals(producerError) && "".equals(colorError)
                && "".equals(modelError) && "".equals(priceError) && "".equals(dateError)) {
            return true;
        }
        return false;
    }

    public static boolean name(String s) {
        nameError = "";
        if (s == null || "".equals(s)) {
            nameError = "Name is absent";
        }
        return exists(s);
    }

    public static boolean color(String s) {
        colorError = "";
        if (s == null || "".equals(s)) {
            colorError = "Color is absent";
        }
        return exists(s);
    }

    public static boolean producer(String s) {
        producerError = "";
        if (s == null || "".equals(s)) {
            producerError = "Producer is absent";
        }
        return exists(s);
    }

    public static boolean exists(String s) {
        return s != null && !"".equals(s);
    }

    public static boolean date(String s) {
        dateError = "";
        SimpleDateFormat formatter = new SimpleDateFormat(ConfigurationManager.getStr("date.format"));
        try {
            formatter.parse(s);
        } catch (ParseException e) {
            dateError = "Date must be in format (dd-MM-yyyy)";
            return false;
        }
        return true;
    }

    public static boolean model(String s) {
        modelError = "";
        boolean b = s.matches(ConfigurationManager.getStr("model.format"));
        if (!b) {
            modelError = "Model must be in format (exmpl. AD102)";
        }
        return b;
    }

    public static boolean price(String s, String inStock) {
        priceError = "";
        int i = 0;
        boolean in = Boolean.parseBoolean(inStock);
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            if (in) {
                priceError = "Price and stock information are invalid";
            }
            return !in;
        }
        if (i < 0 || !in) {
            priceError = "Price and stock information are invalid";
        }
        return i > 0 && in;
    }

    private ProductValidator() {
    }

    public static String getNameError() {
        return nameError;
    }

    public static String getProducerError() {
        return producerError;
    }

    public static String getColorError() {
        return colorError;
    }

    public static String getModelError() {
        return modelError;
    }

    public static String getPriceError() {
        return priceError;
    }

    public static String getDateError() {
        return dateError;
    }
}
