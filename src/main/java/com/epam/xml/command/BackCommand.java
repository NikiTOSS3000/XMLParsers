package com.epam.xml.command;

import com.epam.xml.controller.XMLServlet;
import com.epam.xml.resources.Constants;
import com.epam.xml.util.ConfigurationManager;
import com.epam.xml.util.IOUtil;
import com.epam.xml.xsl.TransformerService;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public final class BackCommand implements ICommand{
    
    private final TransformerService xslService = TransformerService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String directory = XMLServlet.getPath();
        String xml = directory + ConfigurationManager.getStr("XML_PATH");
        String category = request.getParameter(Constants.CATEGORY);
        String subcategory = request.getParameter(Constants.SUBCATEGORY);
        String subcommand = request.getParameter(Constants.SUBCOMMAND);
        HashMap<String, Object> param = null;
        String xsl = Constants.PRODUCTS;
        String xslPath = directory + ConfigurationManager.getStr("PRODUCTS_XSL");
        if (subcategory != null) {
            if (Constants.BACK.equals(subcommand)) {
                param = new HashMap<String, Object>();
                xsl = Constants.CATEGORY;
                xslPath = directory + ConfigurationManager.getStr("CATEGORY_XSL");
                param.put(Constants.CATEGORY_NAME, category);
            } else {
                xsl = Constants.SUBCATEGORY;
                xslPath = directory + ConfigurationManager.getStr("SUBCATEGORY_XSL");
                param = new HashMap<String, Object>();
                param.put(Constants.CATEGORY_NAME, category);
                param.put(Constants.SUBCATEGORY_NAME, subcategory);
            }
        } else if (category != null) {
            if (!Constants.BACK.equals(subcommand)) {
                param = new HashMap<String, Object>();
                xsl = Constants.CATEGORY;
                xslPath = directory + ConfigurationManager.getStr("CATEGORY_XSL");
                param.put(Constants.CATEGORY_NAME, category);
            }
        } else if (Constants.BACK.equals(subcommand)) {
            return ConfigurationManager.getStr("INDEX_PAGE_PATH");
        }
        xslService.getReadLock().lock();
        String transformedResponse = xslService.getTransformedResponse(xml, xsl, xslPath, param);
        xslService.getReadLock().unlock();
        if (IOUtil.writeResponse(transformedResponse, response)) {
            return null;
        }
        return null;
    }
}
