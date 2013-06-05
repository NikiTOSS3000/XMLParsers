<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:x="http://www.epam.com/products" 
                xmlns:validation="xalan://com.epam.xml.xsl.ProductValidator"
                exclude-result-prefixes="x">
    <xsl:output method="xml"/>
    <xsl:include href="add.xsl" />
    
    <xsl:param name="categoryName"/>
    <xsl:param name="subcategoryName"/>
    <xsl:param name="name"/>
    <xsl:param name="producer"/>
    <xsl:param name="color"/>
    <xsl:param name="model"/>
    <xsl:param name="date"/>
    <xsl:param name="price"/>
    <xsl:param name="instock"/>
    <xsl:param name="test" select="0"/>
    
    <xsl:template match="/">
        <xsl:variable name="error">
            <xsl:if test="validation:name($name) = false">
					&lt;br/> Name is absent
            </xsl:if>
            <xsl:if test="validation:producer($producer)=false">
					&lt;br/> Producer is not specified
            </xsl:if>
            <xsl:if test="validation:color($color)=false">
					&lt;br/> Color is not specified
            </xsl:if>
            <xsl:if test="validation:model($model)=false">
					&lt;br/> Model is incorrect
            </xsl:if>
            <xsl:if test="validation:date($date)=false">
					&lt;br/> Date is incorrect
            </xsl:if>
            <xsl:if test="validation:price($price, $instock)=false">
					&lt;br/> Price and stock information are invalid
            </xsl:if>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="$error=''">
                <xsl:apply-templates />
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="addXsl">
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template name="add">
        <xsl:element name="product" namespace="{namespace-uri()}">
            <xsl:attribute name="name">
                <xsl:value-of select="$name" />
            </xsl:attribute>
            <xsl:element name="producer" namespace="{namespace-uri()}">
                <xsl:value-of select="$producer" />
            </xsl:element>
            <xsl:element name="model" namespace="{namespace-uri()}">
                <xsl:value-of select="$model" />
            </xsl:element>
            <xsl:element name="date" namespace="{namespace-uri()}">
                <xsl:value-of select="$date" />
            </xsl:element>
            <xsl:element name="color" namespace="{namespace-uri()}">
                <xsl:value-of select="$color" />
            </xsl:element>
            <xsl:choose>
                <xsl:when test="$price='' or $price=0">
                    <xsl:element name="instock" namespace="{namespace-uri()}">
                        <xsl:value-of select="$instock" />
                    </xsl:element>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:element name="price" namespace="{namespace-uri()}">
                        <xsl:value-of select="$price" />
                    </xsl:element>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="x:subcategory">
        <xsl:copy>
            <xsl:apply-templates select="@* | *" />
            <xsl:if test="@name=$subcategoryName and ../@name=$categoryName">
                <xsl:call-template name="add" />
            </xsl:if>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>