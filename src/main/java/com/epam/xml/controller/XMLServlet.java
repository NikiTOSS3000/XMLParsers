package com.epam.xml.controller;

import com.epam.xml.command.CommandFactory;
import com.epam.xml.command.ICommand;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public final class XMLServlet extends HttpServlet {

    static final private Logger logger = Logger.getLogger(XMLServlet.class);
    private static String path = null;

    public XMLServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        ICommand iCommand = CommandFactory.getInstance().getCommand(command);
        String page = iCommand.execute(req, resp);
        if (page != null) {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }

    public void init(ServletConfig servletConfig) {
        ServletContext context = servletConfig.getServletContext();
        path = context.getRealPath("/");
    }

    public static String getPath() {
        return path;
    }
}
