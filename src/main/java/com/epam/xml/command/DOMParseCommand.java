package com.epam.xml.command;

import com.epam.xml.controller.XMLServlet;
import com.epam.xml.model.Products;
import com.epam.xml.parsers.ProductsDOMParser;
import com.epam.xml.util.ConfigurationManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DOMParseCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Products products = ProductsDOMParser.parse(XMLServlet.getPath() + ConfigurationManager.getStr("XML_PATH"));
        request.setAttribute("products", products);
        return ConfigurationManager.getStr("RESULT_PAGE_PATH");
    }
}
