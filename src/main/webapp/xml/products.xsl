<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:x="http://www.epam.com/products" exclude-result-prefixes="x">
    <xsl:include href="pageTemplate.xsl" />
    <xsl:output method="html"/>
    
    <xsl:template match="/x:products">
        <xsl:variable name="body">
            <xsl:for-each select="x:category">
                <h3>
                    <a>
                        <xsl:attribute name="href">controller?command=XSLT&amp;category=<xsl:value-of select="@name" /></xsl:attribute>
                        <xsl:value-of select="@name" />
                    </a> 
                    <xsl:text disable-output-escaping="yes"> (</xsl:text>
                    <xsl:value-of select="count(x:subcategory/x:product)" />
                    <xsl:text disable-output-escaping="yes">)</xsl:text>
                </h3>
            </xsl:for-each>
            <br/>
            <input type="button" value="Back"
           onclick="location.href = 'controller?command=XSLT&amp;subcommand=back'"/>
        </xsl:variable>
        <xsl:call-template name="page">
            <xsl:with-param name="body" select="$body" />
        </xsl:call-template>
    </xsl:template>
</xsl:stylesheet>
