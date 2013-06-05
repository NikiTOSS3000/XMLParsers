package com.epam.xml.command;

import com.epam.xml.controller.XMLServlet;
import com.epam.xml.resources.Constants;
import com.epam.xml.util.ConfigurationManager;
import com.epam.xml.xsl.ProductValidator;
import com.epam.xml.xsl.ProductsTransformerService;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;

public final class XSLTCommand implements ICommand {

    private static Logger logger = Logger.getLogger("com.epam.xml.command");
    private final ProductsTransformerService xslService = ProductsTransformerService.getInstance();
    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final String CATEGORY_NAME = "categoryName";
    private final String SUBCATEGORY_NAME = "subcategoryName";
    private final String SUBCOMMAND = "subcommand";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String directory = XMLServlet.getPath();
        String xml = directory + ConfigurationManager.getStr("XML_PATH");
        String category = request.getParameter(Constants.CATEGORY);
        String subcategory = request.getParameter(Constants.SUBCATEGORY);
        String subcommand = request.getParameter(SUBCOMMAND);
        ProductValidator.reset();
        String xsl = Constants.PRODUCTS;
        HashMap<String, Object> param = null;
        if (Constants.ADD.equals(subcommand)) {
            xsl = Constants.ADD;
            param = new HashMap<String, Object>();
            param.put(CATEGORY_NAME, category);
            param.put(SUBCATEGORY_NAME, subcategory);
        } else if (Constants.SAVE.equals(subcommand)) {
            param = new HashMap<String, Object>();
            xsl = addProduct(xml, param, request, response);
            if (Constants.SUBCATEGORY.equals(xsl)) {
                String s = "controller?" + request.getQueryString().replaceAll("&" + SUBCOMMAND + ".+", "");
                sendRedirect(s, response);
            } else {
                return null;
            }
        } else if (Constants.CANCEL.equals(subcommand)) {
            xsl = Constants.SUBCATEGORY;
            param = new HashMap<String, Object>();
            param.put(CATEGORY_NAME, category);
            param.put(SUBCATEGORY_NAME, subcategory);
        } else if (subcategory != null) {
            if (Constants.BACK.equals(subcommand)) {
                param = new HashMap<String, Object>();
                xsl = Constants.CATEGORY;
                param.put(CATEGORY_NAME, category);
            } else {
                xsl = Constants.SUBCATEGORY;
                param = new HashMap<String, Object>();
                param.put(CATEGORY_NAME, category);
                param.put(SUBCATEGORY_NAME, subcategory);
            }
        } else if (category != null) {
            if (!Constants.BACK.equals(subcommand)) {
                param = new HashMap<String, Object>();
                xsl = Constants.CATEGORY;
                param.put(CATEGORY_NAME, category);
            }
        } else if (Constants.BACK.equals(subcommand)) {
            return ConfigurationManager.getStr("INDEX_PAGE_PATH");
        }
        lock.readLock().lock();
        String transformedResponse = getTransformedResponse(xml, xsl, param);
        lock.readLock().unlock();
        if (writeResponse(transformedResponse, response)) {
            return null;
        }
        return null;
    }

    private String addProduct(String xml, HashMap<String, Object> param, HttpServletRequest request, HttpServletResponse response) {
        String xsl = Constants.SAVE;
        param.put(CATEGORY_NAME, request.getParameter(Constants.CATEGORY));
        param.put(SUBCATEGORY_NAME, request.getParameter(Constants.SUBCATEGORY));
        param.put(Constants.NAME, request.getParameter(Constants.NAME));
        param.put(Constants.PRODUCER, request.getParameter(Constants.PRODUCER));
        param.put(Constants.MODEL, request.getParameter(Constants.MODEL));
        param.put(Constants.COLOR, request.getParameter(Constants.COLOR));
        param.put(Constants.DATE, request.getParameter(Constants.DATE));
        param.put(Constants.PRICE, request.getParameter(Constants.PRICE));
        String inStock = request.getParameter(Constants.INSTOCK);
        if ("on".equals(inStock)) {
            param.put(Constants.INSTOCK, "true");
        } else {
            param.put(Constants.INSTOCK, "false");
        }
        lock.readLock().lock();
        String transformedResponse = getTransformedResponse(xml, xsl, param);
        lock.readLock().unlock();
        if (!ProductValidator.isValid()) {
            writeResponse(transformedResponse, response);
            return null;
        } else {
            xsl = Constants.SUBCATEGORY;
            lock.writeLock().lock();
            writeInFile(transformedResponse, xml);
            lock.writeLock().unlock();
        }
        return xsl;
    }

    private String getTransformedResponse(String xml, String xsl, HashMap<String, Object> param) {
        String s = null;
        try {
            s = xslService.transform(xml, xsl, param);
        } catch (TransformerException ex) {
            logger.error(ex);
        }
        return s;
    }

    private boolean writeResponse(String s, HttpServletResponse response) {
        try {
            PrintWriter pw = response.getWriter();
            pw.write(s);
            pw.close();
        } catch (IOException ex) {
            logger.error(ex);
            return false;
        }
        return true;
    }

    private boolean sendRedirect(String s, HttpServletResponse response) {
        try {
            response.sendRedirect(s);
        } catch (IOException ex) {
            logger.error(ex);
            return false;
        }
        return true;
    }

    private boolean writeInFile(String s, String file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(s);
            writer.close();
            return true;
        } catch (IOException ex) {
            logger.error(ex);
            return false;
        }
    }
}
