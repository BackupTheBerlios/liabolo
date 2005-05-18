<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text"
            doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
            version="4.01"
            indent="no" />

	<xsl:param name="prolog"/>
	<xsl:param name="epilog"/>

	<xsl:template match="/">
		<xsl:if test="$prolog = 'true'">
			<xsl:call-template name="head"/>
		</xsl:if>
		<xsl:apply-templates/>
		<xsl:if test="$epilog = 'true'">
			<xsl:call-template name="foot"/>
		</xsl:if>
	</xsl:template>
	
		
	<xsl:template name="head">
		Begin... 
	</xsl:template>
	
	<xsl:template name="foot">
		End... 
	</xsl:template>
	
    <!-- Hier das csv-Format-->
    <xsl:template name="csvOutput" match="mediatype">
        <xsl:for-each select="title"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="creator"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="subject"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="description"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="publisher"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="contributers"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="date"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="type"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="format"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="identifier"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="source"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="language"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="relation"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="coverage"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="rights"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="branch"><xsl:value-of select="."/>,</xsl:for-each>
        <xsl:for-each select="signature"><xsl:value-of select="."/>,</xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
