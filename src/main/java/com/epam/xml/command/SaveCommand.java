package com.epam.xml.command;

import com.epam.xml.controller.XMLServlet;
import com.epam.xml.resources.Constants;
import com.epam.xml.util.ConfigurationManager;
import com.epam.xml.util.IOUtil;
import com.epam.xml.xsl.ProductValidator;
import com.epam.xml.xsl.TransformerService;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class SaveCommand implements ICommand {

    private final TransformerService xslService = TransformerService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String directory = XMLServlet.getPath();
        String xml = directory + ConfigurationManager.getStr("XML_PATH");
        String category = request.getParameter(Constants.CATEGORY);
        String subcategory = request.getParameter(Constants.SUBCATEGORY);
        String subcommand = request.getParameter(Constants.SUBCOMMAND);
        String xslPath = directory + ConfigurationManager.getStr("SAVE_XSL");
        HashMap<String, Object> param = new HashMap<String, Object>();
        String xsl = addProduct(xml, xslPath, param, request, response);
        if (Constants.SUBCATEGORY.equals(xsl)) {
            String s = "controller?" + request.getQueryString().replaceAll("&" + Constants.SUBCOMMAND + ".+", "");
            IOUtil.sendRedirect(s, response);
        } else {
            return null;
        }
        return null;
    }
    
    private String addProduct(String xml, String xslPath, HashMap<String, Object> param, HttpServletRequest request, HttpServletResponse response) {
        String xsl = Constants.SAVE;
        param.put(Constants.CATEGORY_NAME, request.getParameter(Constants.CATEGORY));
        param.put(Constants.SUBCATEGORY_NAME, request.getParameter(Constants.SUBCATEGORY));
        param.put(Constants.NAME, request.getParameter(Constants.NAME));
        param.put(Constants.PRODUCER, request.getParameter(Constants.PRODUCER));
        param.put(Constants.MODEL, request.getParameter(Constants.MODEL));
        param.put(Constants.COLOR, request.getParameter(Constants.COLOR));
        param.put(Constants.DATE, request.getParameter(Constants.DATE));
        param.put(Constants.PRICE, request.getParameter(Constants.PRICE));
        ProductValidator validator = new ProductValidator();
        param.put(Constants.VALIDATOR, validator);
        String inStock = request.getParameter(Constants.INSTOCK);
        if ("on".equals(inStock)) {
            param.put(Constants.INSTOCK, "true");
        } else {
            param.put(Constants.INSTOCK, "false");
        }
        xslService.getReadLock().lock();
        String transformedResponse = xslService.getTransformedResponse(xml, xsl, xslPath, param);
        xslService.getReadLock().unlock();
        if (!validator.isValid()) {
            IOUtil.writeResponse(transformedResponse, response);
            return null;
        } else {
            xsl = Constants.SUBCATEGORY;
            xslService.getWriteLock().lock();
            IOUtil.writeInFile(transformedResponse, xml);
            xslService.getWriteLock().unlock();
        }
        return xsl;
    }
}
