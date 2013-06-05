package com.epam.xml.parsers;

import com.epam.xml.model.Category;
import com.epam.xml.model.Product;
import com.epam.xml.model.Products;
import com.epam.xml.model.Subcategory;
import com.epam.xml.resources.Constants;
import com.epam.xml.util.ConfigurationManager;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public final class ProductsSAXParser {

    private final static Logger logger = Logger.getLogger("com.epam.xml.parsers");

    private ProductsSAXParser() {
    }

    public static Products parse(String fileName) {
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            ProductsHandler productsHandler = new ProductsHandler();
            reader.setContentHandler(productsHandler);
            reader.parse(fileName);
            return productsHandler.getProducts();
        } catch (SAXException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }

    static class ProductsHandler implements ContentHandler {

        private final static Logger logger = Logger.getLogger("com.epam.xml.parsers");
        private String current = null;
        private Category currentCategory = null;
        private Subcategory currentSubcategory = null;
        private Product currentProduct = null;
        private Products products;

        public ProductsHandler() {
            products = new Products();
        }

        public Products getProducts() {
            return products;
        }

        @Override
        public void setDocumentLocator(Locator locator) {
        }

        @Override
        public void startDocument() throws SAXException {
        }

        @Override
        public void endDocument() throws SAXException {
        }

        @Override
        public void startPrefixMapping(String prefix, String uri) throws SAXException {
        }

        @Override
        public void endPrefixMapping(String prefix) throws SAXException {
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            current = localName;
            if (Constants.CATEGORY.equals(current)) {
                currentCategory = new Category();
                currentCategory.setName(atts.getValue(Constants.NAME));
            } else if (Constants.SUBCATEGORY.equals(current)) {
                currentSubcategory = new Subcategory();
                currentSubcategory.setName(atts.getValue(Constants.NAME));
            } else if (Constants.PRODUCT.equals(current)) {
                currentProduct = new Product();
                currentProduct.setName(atts.getValue(Constants.NAME));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            current = localName;
            if (Constants.CATEGORY.equals(current)) {
                products.getCategories().add(currentCategory);
            } else if (Constants.SUBCATEGORY.equals(current)) {
                currentCategory.getSubcategories().add(currentSubcategory);
            } else if (Constants.PRODUCT.equals(current)) {
                currentSubcategory.getProducts().add(currentProduct);
            }
            current = null;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String characters = new String(ch, start, length).trim();
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
        }

        @Override
        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        }

        @Override
        public void processingInstruction(String target, String data) throws SAXException {
        }

        @Override
        public void skippedEntity(String name) throws SAXException {
        }
    }
}
