<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<link rel="stylesheet" type="text/css" href="style.css">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>XMLParsers</title></head>
    <body>
        <div id="wrapper">
            <div id="header"> 
                <div id="links">
                    <a href="controller?command=SAX">SAX</a>&emsp;
                    <a href="controller?command=DOM">DOM</a>&emsp;
                    <a href="controller?command=StAX">StAX</a>&emsp;
                    <a href="controller?command=XSLT">XSLT</a>&emsp;
                </div>
            </div>
            <section id="middle">
                <ol>
                    <c:forEach var="category" items="${products.categories}">
                        <div class="category"> 
                            CATEGORY &emsp;${category.name}
                            <c:forEach var="subcategory" items="${category.subcategories}">
                                <div class="subcategory"> 
                                    SUBCATEGORY &emsp; ${subcategory.name}
                                    <c:forEach var="product" items="${subcategory.products}">
                                        <div class="product"> 
                                            PRODUCT &emsp;${product.name} &emsp;${product.producer} &emsp;${product.model}&emsp; 
                                            <fmt:formatDate value="${product.date}" pattern="dd-mm-yyyy"/>&emsp;
                                            ${product.color} &emsp;${product.price!=0 ? product.price : 'not in stock'}
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </ol>
            </section>
        </div>
        <div id="footer"> Copyright &copy; EPAM 2012. All rights reserved. </div>
    </body>
</html>