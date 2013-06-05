<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:validation="xalan://com.epam.xml.xsl.ProductValidator"
                xmlns:x="http://www.epam.com/products" exclude-result-prefixes="x">
    <xsl:include href="pageTemplate.xsl" />
    <xsl:output method="html"/>
    <xsl:param name="categoryName"/>
    <xsl:param name="subcategoryName"/>
    <xsl:param name="errorMsg"/>
    <xsl:param name="name"/>
    <xsl:param name="producer"/>
    <xsl:param name="color"/>
    <xsl:param name="model"/>
    <xsl:param name="date"/>
    <xsl:param name="price"/>
    <xsl:param name="instock"/>
    
    <xsl:template match="/" name="addXsl">
        <xsl:param name="errorMsg" />
        <xsl:param name="fromSave" />
        <xsl:variable name="body">
            <form action="controller" style="padding: 10px;">
                <dl>
                    <dt>
                        <label for="name">
                            Name
                        </label>
                    </dt>
                    <dd>
                        <input type="text" id="name" name="name" value="{$name}"/>
                        <span style = "color:red"><xsl:value-of select="validation:getNameError()" /></span>
                    </dd>
                    <dt>
                        <label for="producer">
                            Producer</label>
                    </dt>
                    <dd>
                        <input id="producer" type="text" name="producer" value="{$producer}"/>
                        <span style = "color:red"><xsl:value-of select="validation:getProducerError()" /></span>
                    </dd>
                    <dt>
                        <label for="model">
                            Model</label>
                    </dt>
                    <dd>
                        <input id="model" type="text" name="model" value="{$model}"/>
                        <span style = "color:red"><xsl:value-of select="validation:getModelError()" /></span>
                    </dd>

                    <dt>
                        <label for="date">
                            Date</label>
                    </dt>
                    <dd>
                        <input type="text" id="date" name="date" value="{$date}"/>
                        <span style = "color:red"><xsl:value-of select="validation:getDateError()" /></span>
                    </dd>

                    <dt>
                        <label for="color">
                            Color</label>
                    </dt>
                    <dd>
                        <input type="text" id="color" name="color" value="{$color}"/>
                        <span style = "color:red"><xsl:value-of select="validation:getColorError()" /></span>
                    </dd>
                    <dt>
                        <label for="instock">
                            In Stock</label>
                    </dt>
                    <dd>
                        <input type="checkbox" id="instock" name="instock">
                            <xsl:if test="$instock='true'">
                                <xsl:attribute name="checked">yes</xsl:attribute>
                            </xsl:if>
                        </input>
                        
                    </dd>
                    <dt>
                        <label for="price">
                            Price</label>
                    </dt>
                    <dd>
                        <input id="price" type="text" name="price" value="{$price}"/>
                        <span style = "color:red"><xsl:value-of select="validation:getPriceError()" /></span>
                    </dd>
                </dl>
                <input type="hidden" name="command" value="XSLT"/>
                <input type="hidden" name="category" value="{$categoryName}"/>
                <input type="hidden" name="subcategory" value="{$subcategoryName}"/>
                <input type="hidden" name="subcommand" value="save"/>
                <input type="submit" value="Save" size="6"/>
                <xsl:text>&#xA0;&#xA0;&#xA0;</xsl:text>
                <input type="button" value="Cancel"
           onclick="location.href = 'controller?command=XSLT&amp;category={$categoryName}&amp;subcategory={$subcategoryName}&amp;subcommand=cancel'"/>
            </form>
        </xsl:variable>
        <xsl:call-template name="page">
            <xsl:with-param name="body" select="$body" />
        </xsl:call-template>
    </xsl:template>
</xsl:stylesheet>
