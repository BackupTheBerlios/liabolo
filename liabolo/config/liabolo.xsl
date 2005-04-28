<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"
            doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
            version="4.01"
            indent="yes" />

    <xsl:param name="outputId" select="csv"/> <!-- ID, die fuer ein bestimmtes ExportFormat-->

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$outputId = 0">
                <xsl:call-template name="csvOutput"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <!-- Hier das csv-Format-->
    <xsl:template name="csvOutput">
        <xsl:for-each select="/mediatype/title"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/creator"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/subject"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/description"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/publisher"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/contributers"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/date"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/type"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/format"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/identifier"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/source"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/language"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/relation"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/coverage"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/rights"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/branch"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="/mediatype/signature"><xsl:value-of select="."/>,</xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
