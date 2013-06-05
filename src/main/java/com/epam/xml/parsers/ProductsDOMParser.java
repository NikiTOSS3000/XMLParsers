package com.epam.xml.parsers;

import com.epam.xml.model.Category;
import com.epam.xml.model.Product;
import com.epam.xml.model.Products;
import com.epam.xml.model.Subcategory;
import com.epam.xml.resources.Constants;
import com.epam.xml.util.ConfigurationManager;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class ProductsDOMParser {

    private final static Logger logger = Logger.getLogger("com.epam.xml.parsers");

    private ProductsDOMParser() {
    }

    public static Products parse(String filename) {
        Document document = parseDocument(filename);
        if (document == null) {
            return null;
        }
        Element root = document.getDocumentElement();
        NodeList categoryList = root.getElementsByTagName(Constants.CATEGORY);
        ArrayList<Category> categories = new ArrayList<Category>();
        int numberOfCategories = categoryList.getLength();
        for (int i = 0; i < numberOfCategories; i++) {
            Element categoryElement = (Element) categoryList.item(i);
            NodeList subcategoryList = categoryElement.getElementsByTagName(Constants.SUBCATEGORY);
            ArrayList<Subcategory> subcategories = new ArrayList<Subcategory>();
            int numberOfSubcategories = subcategoryList.getLength();
            for (int j = 0; j < numberOfSubcategories; j++) {
                Element subcategoryElement = (Element) subcategoryList.item(j);
                NodeList productList = subcategoryElement.getElementsByTagName(Constants.PRODUCT);
                ArrayList<Product> products = new ArrayList<Product>();
                int numberOfProducts = productList.getLength();
                for (int k = 0; k < numberOfProducts; k++) {
                    Element productElement = (Element) productList.item(k);
                    Product product = new Product();
                    product.setName(productElement.getAttribute(Constants.NAME));
                    String price = getChildValue(productElement, Constants.PRICE);
                    if (price != null) {
                        product.setPrice(Integer.parseInt(price));
                    } else {
                        product.setInStock(Boolean.parseBoolean(getChildValue(productElement, Constants.INSTOCK)));
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat(ConfigurationManager.getStr("date.format"));
                    Date date = null;
                    try {
                        date = formatter.parse(getChildValue(productElement, Constants.DATE));
                    } catch (ParseException e) {
                        logger.error(e.getMessage());
                    }
                    product.setDate(date);
                    product.setColor(getChildValue(productElement, Constants.COLOR));
                    product.setModel(getChildValue(productElement, Constants.MODEL));
                    product.setProducer(getChildValue(productElement, Constants.PRODUCER));
                    products.add(product);
                }
                Subcategory subcategory = new Subcategory();
                subcategory.setName(subcategoryElement.getAttribute(Constants.NAME));
                subcategory.setProducts(products);
                subcategories.add(subcategory);
            }
            Category category = new Category();
            category.setName(categoryElement.getAttribute(Constants.NAME));
            category.setSubcategories(subcategories);
            categories.add(category);
        }
        Products products = new Products();
        products.setCategories(categories);
        return products;
    }

    private static Element getChild(Element parent, String childName) {
        NodeList nodeList = parent.getElementsByTagName(childName);
        Element child = (Element) nodeList.item(0);
        return child;
    }

    private static String getChildValue(Element parent, String childName) {
        Element child = getChild(parent, childName);
        if (child == null) {
            return null;
        }
        Node node = child.getFirstChild();
        String val = node.getNodeValue();
        return val;
    }

    private static Document parseDocument(String filename) {
        DOMParser parser = null;
        try {
            parser = new DOMParser();
            Document answer = null;
            synchronized (parser) {
                parser.parse(filename);
                answer = parser.getDocument();
                parser.reset();
            }
            return answer;
        } catch (SAXException e) {
            logger.error(e.getMessage());
            return null;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
