<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:x="http://www.epam.com/products" exclude-result-prefixes="x">
    <xsl:include href="pageTemplate.xsl" />
    <xsl:output method="html"/>
    <xsl:param name="categoryName"/>
    <xsl:param name="subcategoryName"/>
    
    <xsl:template match="/x:products">
        <xsl:variable name="body">
            <xsl:for-each select="x:category[@name=$categoryName]/x:subcategory[@name=$subcategoryName]/x:product">
                <h3>
                    <xsl:value-of select="@name" />
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="x:producer" />
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="x:model" />
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="x:date" />
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="x:color" />
                    <xsl:text> </xsl:text>
                    <xsl:choose>
                        <xsl:when test="x:price>0">
                            <xsl:value-of select="x:price" />
                        </xsl:when>
                        <xsl:when test="x:instock='false'">
                            NotInStock
                        </xsl:when>
                    </xsl:choose>
                </h3>
                <hr/>
            </xsl:for-each>
            <br/>
            <input type="button" value="Back"
           onclick="location.href = 'controller?command=XSLT&amp;category={$categoryName}&amp;subcategory={$subcategoryName}&amp;subcommand=back'"/>
            <xsl:text>&#xA0;&#xA0;&#xA0;</xsl:text>
            <input type="button" value="Add"
           onclick="location.href = 'controller?command=XSLT&amp;category={$categoryName}&amp;subcategory={$subcategoryName}&amp;subcommand=add'"/>
        </xsl:variable>
        <xsl:call-template name="page">
            <xsl:with-param name="body" select="$body" />
        </xsl:call-template>
    </xsl:template>
</xsl:stylesheet>
