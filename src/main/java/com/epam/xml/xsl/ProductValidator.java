package com.epam.xml.xsl;

import com.epam.xml.util.ConfigurationManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class ProductValidator {

    private String nameError = "";
    private String producerError = "";
    private String colorError = "";
    private String modelError = "";
    private String priceError = "";
    private String dateError = "";

    public boolean isValid() {
        if (nameError.isEmpty() && producerError.isEmpty() && colorError.isEmpty()
                && modelError.isEmpty() && priceError.isEmpty() && dateError.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean name(String s) {
        nameError = "";
        if (s == null || "".equals(s)) {
            nameError = "Name is absent";
        }
        return exists(s);
    }

    public boolean color(String s) {
        colorError = "";
        if (s == null || "".equals(s)) {
            colorError = "Color is absent";
        }
        return exists(s);
    }

    public boolean producer(String s) {
        producerError = "";
        if (s == null || "".equals(s)) {
            producerError = "Producer is absent";
        }
        return exists(s);
    }

    public boolean exists(String s) {
        return s != null && !"".equals(s);
    }

    public boolean date(String s) {
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

    public boolean model(String s) {
        modelError = "";
        boolean b = s.matches(ConfigurationManager.getStr("model.format"));
        if (!b) {
            modelError = "Model must be in format (exmpl. AD102)";
        }
        return b;
    }

    public boolean price(String s, String inStock) {
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

    public ProductValidator() {
    }

    public String getNameError() {
        return nameError;
    }

    public String getProducerError() {
        return producerError;
    }

    public String getColorError() {
        return colorError;
    }

    public String getModelError() {
        return modelError;
    }

    public String getPriceError() {
        return priceError;
    }

    public String getDateError() {
        return dateError;
    }
}
