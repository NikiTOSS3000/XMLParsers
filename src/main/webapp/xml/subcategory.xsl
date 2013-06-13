<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:x="http://www.epam.com/products" exclude-result-prefixes="x">
    <xsl:include href="pageTemplate.xsl" />
    <xsl:output method="html"/>
    <xsl:param name="categoryName"/>
    <xsl:param name="subcategoryName"/>
    
    <xsl:template match="/x:products">
        <xsl:variable name="body">
            <table border="1" style="margin: 10px;">
                <tr>
                    <th>Name</th>
                    <th>Producer</th>
                    <th>Model</th>
                    <th>Date of issue</th>
                    <th>Color</th>
                    <th>Price/In Stock</th>
                    <xsl:for-each select="x:category[@name=$categoryName]/x:subcategory[@name=$subcategoryName]/x:product">
                        <tr>
                            <td>
                                <xsl:value-of select="@name" />
                            </td>
                            <td>
                                <xsl:value-of select="x:producer" />
                            </td>
                            <td>
                                <xsl:value-of select="x:model" />
                            </td>
                            <td>
                                <xsl:value-of select="x:date" />
                            </td>
                            <td>
                                <xsl:value-of select="x:color" />
                            </td>
                            <td>
                                <xsl:choose>
                                    <xsl:when test="x:price>0">
                                        <xsl:value-of select="x:price" />
                                    </xsl:when>
                                    <xsl:when test="x:instock='false'">
                                        NotInStock
                                    </xsl:when>
                                </xsl:choose>
                            </td>
                        </tr>
                    </xsl:for-each>
                </tr>
            </table>
            <br/>
            <input type="button" value="Back" style="margin-left:10px"
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
