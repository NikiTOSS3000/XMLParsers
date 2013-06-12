package com.epam.xml.command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public final class CommandFactory {private static CommandFactory instance = null;
    HashMap<String, ICommand> commands = new HashMap<String, ICommand>();

    private CommandFactory() {
        commands.put("DOM", new DOMParseCommand());
        commands.put("SAX", new SAXParseCommand());
        commands.put("StAX", new StAXParseCommand());
        commands.put("XSLT", new XSLTCommand());
    }

    public ICommand getCommand(String action) {
        ICommand command = commands.get(action);
        if (command == null) {
            command = new NoCommand();
        }
        return command;
    }

    public static CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }
}
