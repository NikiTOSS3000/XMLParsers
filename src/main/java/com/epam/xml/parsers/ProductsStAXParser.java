package com.epam.xml.parsers;

import com.epam.xml.model.*;
import com.epam.xml.resources.Constants;
import com.epam.xml.util.ConfigurationManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public final class ProductsStAXParser {

    private final static Logger logger = Logger.getLogger("com.epam.xml.parsers");

    private ProductsStAXParser() {
    }

    public static Products parse(String file) {
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
            return process(reader);
        } catch (XMLStreamException ex) {
            logger.error(ex);
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        }
        return null;
    }

    private static Products process(XMLStreamReader reader) throws XMLStreamException {
        String current = null;
        Category currentCategory = null;
        Subcategory currentSubcategory = null;
        Product currentProduct = null;
        Products products = new Products();
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    current = reader.getLocalName();
                    if (Constants.CATEGORY.equals(current)) {
                        currentCategory = new Category();
                        currentCategory.setName(reader.getAttributeValue(null, Constants.NAME));
                    } else if (Constants.SUBCATEGORY.equals(current)) {
                        currentSubcategory = new Subcategory();
                        currentSubcategory.setName(reader.getAttributeValue(null, Constants.NAME));
                    } else if (Constants.PRODUCT.equals(current)) {
                        currentProduct = new Product();
                        currentProduct.setName(reader.getAttributeValue(null, Constants.NAME));
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    current = reader.getLocalName();
                    if (Constants.CATEGORY.equals(current)) {
                        products.getCategories().add(currentCategory);
                    } else if (Constants.SUBCATEGORY.equals(current)) {
                        currentCategory.getSubcategories().add(currentSubcategory);
                    } else if (Constants.PRODUCT.equals(current)) {
                        currentSubcategory.getProducts().add(currentProduct);
                    }
                    current = null;
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String characters = reader.getText();
                    if (Constants.PRODUCER.equals(current)) {
                        currentProduct.setProducer(characters);
                    } else if (Constants.MODEL.equals(current)) {
                        currentProduct.setModel(characters);
                    } else if (Constants.DATE.equals(current)) {
                        SimpleDateFormat formatter = new SimpleDateFormat(ConfigurationManager.getStr("date.format"));
                        Date date = null;
                        try {
                            date = formatter.parse(characters);
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }
                        currentProduct.setDate(date);
                    } else if (Constants.COLOR.equals(current)) {
                        currentProduct.setColor(characters);
                    } else if (Constants.INSTOCK.equals(current)) {
                        currentProduct.setInStock(Boolean.parseBoolean(characters));
                    } else if (Constants.PRICE.equals(current)) {
                        currentProduct.setPrice(Integer.parseInt(characters));
                    }
                    break;
            }
        }
        return products;
    }
}
