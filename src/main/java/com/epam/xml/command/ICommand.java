package com.epam.xml.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ICommand {
    
    public String execute(HttpServletRequest request, HttpServletResponse response);
}
