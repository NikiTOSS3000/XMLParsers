<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:x="http://www.epam.com/products" exclude-result-prefixes="x">
    <xsl:output method="html" />
    <xsl:template name="page">
        <xsl:param name="body" />
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html></xsl:text>
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta charset="utf-8" />
                <title>XML Parsers</title>
                <meta name="keywords" content="" />
                <meta name="description" content="" />
                <link rel="stylesheet" href="style.css" type="text/css"
                      media="screen, projection" />
            </head>

            <body>

                <div id="wrapper">

                    <div id="header">
                        <div id="links">
                            <a>
                                <xsl:attribute name="href">controller?command=SAX</xsl:attribute>SAX
                            </a>
                            <xsl:text disable-output-escaping="yes">&amp;emsp;</xsl:text>
                            <a>
                                <xsl:attribute name="href">controller?command=DOM</xsl:attribute>DOM
                            </a>
                            <xsl:text disable-output-escaping="yes">&amp;emsp;</xsl:text>
                            <a>
                                <xsl:attribute name="href">controller?command=StAX</xsl:attribute>StAX
                            </a>
                            <xsl:text disable-output-escaping="yes">&amp;emsp;</xsl:text>
                            <a>
                                <xsl:attribute name="href">controller?command=XSLT</xsl:attribute>XSLT
                            </a>
                            <xsl:text disable-output-escaping="yes">&amp;emsp;</xsl:text>
                        </div>
                    </div>
                    <section id="middle">
                        <div id="container">
                            <div id="content">
                                <xsl:copy-of select="$body" />
                            </div>
                        </div>

                    </section>

                </div>

                <div id="footer">
                    Copyright
                    <xsl:text disable-output-escaping='yes'> &amp;copy</xsl:text>
                    EPAM 2012. All rights
                    reserved.
                </div>

            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>