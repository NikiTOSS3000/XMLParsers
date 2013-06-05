package com.epam.xml.xsl;

import com.epam.xml.controller.XMLServlet;
import com.epam.xml.resources.Constants;
import com.epam.xml.util.ConfigurationManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.Logger;

public final class ProductsTransformerService {
    private final static Logger logger = Logger.getLogger("com.epam.xml.xsl");
    private final HashMap<String, Templates> templates = new HashMap<String, Templates>();
    private final TransformerFactory factory = TransformerFactory.newInstance();
    private static ProductsTransformerService instance = null;
    
    private ProductsTransformerService() {
        String path = XMLServlet.getPath();
        addTemplate(Constants.PRODUCTS, path + ConfigurationManager.getStr("PRODUCTS_XSL"));
        addTemplate(Constants.CATEGORY, path + ConfigurationManager.getStr("CATEGORY_XSL"));
        addTemplate(Constants.SUBCATEGORY, path + ConfigurationManager.getStr("SUBCATEGORY_XSL"));
        addTemplate(Constants.ADD, path + ConfigurationManager.getStr("ADD_XSL"));
        addTemplate(Constants.SAVE, path + ConfigurationManager.getStr("SAVE_XSL"));
    }
    
    public void addTemplate(String key, String xsl) {
        try {       
            Source source = getSource(xsl);
            Templates template = factory.newTemplates(source);
            templates.put(key, template);
        } catch (TransformerConfigurationException ex) {
            logger.error(ex);
        } 
    }
    
    public Transformer getTransformer(String xsl) {
        Transformer transformer = null;
        try {
            transformer = templates.get(xsl).newTransformer();
        } catch (TransformerConfigurationException ex) {
            logger.error(ex);
        }
        return transformer;
    }
    
    public String transform(String file, String xsl, Map<String, Object> param) throws TransformerException {
        Transformer transformer = instance.getTransformer(xsl);
        StringWriter writer = new StringWriter();
        if (param != null) {
            Set<String> keySet = param.keySet();
            for (String key : keySet) {
                transformer.setParameter(key, param.get(key));
            }
        }
        transformer.transform(instance.getSource(file), new StreamResult(writer));
        return writer.toString();
    }
    
    public static ProductsTransformerService getInstance() {
        if (instance == null) {
            instance = new ProductsTransformerService();
        }
        return instance;
    }
    
    public Source getSource(String file) {
        Source source = null;
        try {
            source = new StreamSource(new FileInputStream(file), file);
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        }
        return source;
    }
}
