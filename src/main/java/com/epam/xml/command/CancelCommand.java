package com.epam.xml.command;

import com.epam.xml.controller.XMLServlet;
import com.epam.xml.resources.Constants;
import com.epam.xml.util.ConfigurationManager;
import com.epam.xml.util.IOUtil;
import com.epam.xml.xsl.TransformerService;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CancelCommand implements ICommand {

    private final TransformerService xslService = TransformerService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String directory = XMLServlet.getPath();
        String xml = directory + ConfigurationManager.getStr("XML_PATH");
        String category = request.getParameter(Constants.CATEGORY);
        String subcategory = request.getParameter(Constants.SUBCATEGORY);
        String subcommand = request.getParameter(Constants.SUBCOMMAND);
        String xsl = Constants.SUBCATEGORY;
        String xslPath = directory + ConfigurationManager.getStr("SUBCATEGORY_XSL");
        HashMap<String, Object> param = new HashMap<String, Object>();
        xsl = Constants.SUBCATEGORY;
        param.put(Constants.CATEGORY_NAME, category);
        param.put(Constants.SUBCATEGORY_NAME, subcategory);
        xslService.getReadLock().lock();
        String transformedResponse = xslService.getTransformedResponse(xml, xsl, xslPath, param);
        xslService.getReadLock().unlock();
        if (IOUtil.writeResponse(transformedResponse, response)) {
            return null;
        }
        return null;
    }
}
