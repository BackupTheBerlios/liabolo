/*
 * Created on 15.01.2004 by Easy (Stefan Willer)
 *
 * Copyright (c) Projektgruppe P30 Uni Oldenburg Germany
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file COPYING.  If not, write to
 * the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 *
 */
package org.liabolo.common;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.liabolo.service.Dispatcher;
import org.liabolo.exception.ServiceNotAvailableException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class MetaData {

    private static Logger log = Logger.getLogger(MetaData.class);

    public static final short DC_IDENTIFIER = 0;
    public static final short DC_TITLE = 1;
    public static final short DC_CREATOR = 2;
    public static final short DC_SUBJECT = 3;
    public static final short DC_DESCRIPTION = 4;
    public static final short DC_PUBLISHER = 5;
    public static final short DC_CONTRIBUTORS = 6;
    public static final short DC_DATE = 7;
    public static final short DC_TYPE = 8;
    public static final short DC_FORMAT = 9;
    public static final short DC_SOURCE = 10;
    public static final short DC_LANGUAGE = 11;
    public static final short DC_RELATION = 12;
    public static final short DC_COVERAGE = 13;
    public static final short DC_RIGHTS = 14;
    public static final short LIABOLO_BRANCH = 15;
    public static final short LIABOLO_SIGNATURE = 17;


    protected String dc_identifier = "";

    protected String dc_title = " ";
    protected String dc_creator = " ";
    protected String dc_subject = " ";
    protected String dc_description = " ";
    protected String dc_publisher = " ";
    protected String dc_contributors = " ";
    protected Date dc_date = null;
    protected String dc_type = " ";
    protected String dc_format = " ";
    protected String dc_source = " ";
    protected String dc_language = " ";
    protected String dc_relation = " ";
    protected String dc_coverage = " ";
    protected String dc_rights = " ";
    protected String liabolo_branch = " ";
    protected String liabolo_signature = " ";

/*
    //dc_xx_creationTime and uniOl_xx_creationTime only for merging used
    protected String dc_identifier_creationTime = "0";
    protected String dc_title_creationTime = "0";
    protected String dc_creator_creationTime = "0";
    protected String dc_subject_creationTime = "0";
    protected String dc_description_creationTime = "0";
    protected String dc_publisher_creationTime = "0";
    protected String dc_contributors_creationTime = "0";
    protected String dc_date_creationTime = "0";
    protected String dc_type_creationTime = "0";
    protected String dc_format_creationTime = "0";
    protected String dc_source_creationTime = "0";
    protected String dc_language_creationTime = "0";
    protected String dc_relation_creationTime = "0";
    protected String dc_coverage_creationTime = "0";
    protected String dc_rights_creationTime = "0";
    protected String uniol_branch_creationTime = "0";
    protected String uniol_signature_creationTime = "0";
*/
    //if an update occures, the new time is stored
    private String localUpdateTime = "0";
    private String lastKnownDbTime = "0";

    private String workspace = "0";

	private static short[] attributeSequenceList= {	DC_CREATOR,
													DC_TITLE,
													LIABOLO_SIGNATURE,
													DC_COVERAGE,
													DC_SUBJECT,
													DC_DESCRIPTION,
													DC_DATE,
													DC_PUBLISHER,
													DC_IDENTIFIER,
													DC_SOURCE,
													DC_LANGUAGE,
													DC_RELATION,
													DC_RIGHTS,
													DC_TYPE,
													DC_FORMAT,
													DC_CONTRIBUTORS,
													LIABOLO_BRANCH				
													};
    /**
     * Only for creating an existing Metadata Object by given dbTime!</br>
     * New Metadata should be created by the static Method 'MetaData.createNewMetaData()'.
     * @param dbTime
     */
    public MetaData(String dbTime) {
        log.debug("Creation of an existing Metadata-Object!!", 9);
        lastKnownDbTime = dbTime;
        localUpdateTime = dbTime;
    }

    private MetaData(Signature signature){
        log.debug("Creation of a new Metadata-Object!!", 9);
        //setDc_identifier(Dispatcher.getServerTimeInMillis(Configurator.getProperty("timeServerURL")));
        setLiabolo_signature(signature);
    }

    public static MetaData createNewMetaData(String branch, String url){
        MetaData newMetaData = null;
        Signature signature = SignatureGenerator.setSignature(branch, url);
        if(signature!=null){
            newMetaData = new MetaData(signature);
            newMetaData.setLiabolo_branch(branch);
        }
        return newMetaData;
    }

    //################################################################################
    //# getter and setter for all items, but setter for identifier
    //################################################################################
	public static short[] getAttributeSequenceList(){
		return attributeSequenceList;
	}
	
	public static void setAttributeSequenceList(short[] ses) {
		attributeSequenceList = ses;
	}


    public String getDc_identifier() {
        return dc_identifier;
    }

    public void setDc_identifier(String dc_identifier) {
        if (!dc_identifier.equals("")) {
            setLocalUpdateTimeForItem(DC_IDENTIFIER);
            this.dc_identifier = dc_identifier;
        }
    }

    public String getDc_title() {
        return dc_title;
    }

    public void setDc_title(String dc_title) {
        if (!dc_title.equals("") && !dc_title.equals(this.dc_title)) {
            setLocalUpdateTimeForItem(DC_TITLE);
            this.dc_title = dc_title;
        }
    }

    public String getDc_creator() {
        return dc_creator;
    }

    public void setDc_creator(String dc_creator) {
        if (!dc_creator.equals("") && !dc_creator.equals(this.dc_creator)) {
            setLocalUpdateTimeForItem(DC_CREATOR);
            this.dc_creator = dc_creator;
        }
    }

    public String getDc_subject() {
        return dc_subject;
    }

    public void setDc_subject(String dc_subject) {
        if (!dc_subject.equals("") && !dc_subject.equals(this.dc_subject)) {
            setLocalUpdateTimeForItem(DC_SUBJECT);
            this.dc_subject = dc_subject;
        }
    }

    public String getDc_description() {
        return dc_description;
    }

    public void setDc_description(String dc_description) {
        if (!dc_description.equals("") && !dc_description.equals(this.dc_description)) {
            setLocalUpdateTimeForItem(DC_DESCRIPTION);
            this.dc_description = dc_description;
        }
    }

    public String getDc_publisher() {
        return dc_publisher;
    }

    public void setDc_publisher(String dc_publisher) {
        if (!dc_publisher.equals("") && !dc_publisher.equals(this.dc_publisher)) {
            setLocalUpdateTimeForItem(DC_PUBLISHER);
            this.dc_publisher = dc_publisher;
        }
    }

    public String getDc_contributors() {
        return dc_contributors;
    }

    public void setDc_contributors(String dc_contributors) {
        if (!dc_contributors.equals("") && !dc_contributors.equals(this.dc_contributors)) {
            setLocalUpdateTimeForItem(DC_CONTRIBUTORS);
            this.dc_contributors = dc_contributors;
        }
    }

    public Date getDc_date() {
        return dc_date;
    }

    public void setDc_date(Date dc_date) {
        if (dc_date!=null && !dc_date.equals(this.dc_date)) {
            setLocalUpdateTimeForItem(DC_DATE);
            this.dc_date = dc_date;
        }
    }

    public String getDc_type() {
        return dc_type;
    }

    public void setDc_type(String dc_type) {
        if (!dc_type.equals("") && !dc_type.equals(this.dc_type)) {
            setLocalUpdateTimeForItem(DC_TYPE);
            this.dc_type = dc_type;
        }
    }

    public String getDc_format() {
        return dc_format;
    }

    public void setDc_format(String dc_format) {
        if (!dc_format.equals("") && !dc_format.equals(this.dc_format)) {
            setLocalUpdateTimeForItem(DC_FORMAT);
            this.dc_format = dc_format;
        }
    }

    public String getDc_source() {
        return dc_source;
    }

    public void setDc_source(String dc_source) {
        if (!dc_source.equals("") && !dc_source.equals(this.dc_source)) {
            setLocalUpdateTimeForItem(DC_SOURCE);
            this.dc_source = dc_source;
        }
    }

    public String getDc_language() {
        return dc_language;
    }

    public void setDc_language(String dc_language) {
        if (!dc_language.equals("") && !dc_language.equals(this.dc_language)) {
            setLocalUpdateTimeForItem(DC_LANGUAGE);
            this.dc_language = dc_language;
        }
    }

    public String getDc_relation() {
        return dc_relation;
    }

    public void setDc_relation(String dc_relation) {
        if (!dc_relation.equals("") && !dc_relation.equals(this.dc_relation)) {
            setLocalUpdateTimeForItem(DC_RELATION);
            this.dc_relation = dc_relation;
        }
    }

    public String getDc_coverage() {
        return dc_coverage;
    }

    public void setDc_coverage(String dc_coverage) {
        if (!dc_coverage.equals("") && !dc_coverage.equals(this.dc_coverage)) {
            setLocalUpdateTimeForItem(DC_COVERAGE);
            this.dc_coverage = dc_coverage;
        }
    }

    public String getDc_rights() {
        return dc_rights;
    }

    public void setDc_rights(String dc_rights) {
        if (!dc_rights.equals("") && !dc_rights.equals(this.dc_rights)) {
            setLocalUpdateTimeForItem(DC_RIGHTS);
            this.dc_rights = dc_rights;
        }
    }

    public String getLiabolo_branch() {
        return liabolo_branch;
    }

    public void setLiabolo_branch(String liabolo_branch) {
        if (!liabolo_branch.equals("") && !liabolo_branch.equals(this.liabolo_branch)) {
            setLocalUpdateTimeForItem(LIABOLO_BRANCH);
            this.liabolo_branch = liabolo_branch;
        }
    }

    public String getLiabolo_signature() {
        return liabolo_signature;
    }

    public void setLiabolo_signature(Signature liabolo_signature) {
        if (!liabolo_signature.equals("") && !liabolo_signature.equals(this.liabolo_signature)) {
            setLocalUpdateTimeForItem(LIABOLO_SIGNATURE);
            this.liabolo_signature = liabolo_signature.getFullyQualifiedSignature();
        }
    }



    /**
     * Returns the actual creation or update time for this Metadata, which is stored in the db.</br>
     * This creation time has not to be up to date, because since the last checkout another person could have changed anything
     * @return time of local update of any metadata entry
     */
    public String getLocalUpdateTime() {
        return localUpdateTime;
    }

    /**
     * The dbTime from the moment of the checkout
     * @return last last known dbTime for synchronizing
     */
    public String getLastKnownDbTime() {
        return lastKnownDbTime;
    }

    public void setLastKnownDbTime(String lastKnownDbTime){
        this.lastKnownDbTime = lastKnownDbTime;
    }

    public void setLocalUpdateTime(String localUpdateTime){
        this.localUpdateTime = localUpdateTime;
    }

    public void isInWorkspace(boolean workspace){
        if(workspace)
            this.workspace = "1";
        else
            this.workspace = "0";
    }

    public boolean isInWorkspace(){
        if(this.workspace.equals("1"))
            return true;
        else
            return false;
    }

    private void setLocalUpdateTimeForItem(short metadataItem) {
        log.debug("Local update time will now setted, because MetadataItem '"+metadataItem+"' has changed his value!",9);
        String updateTime = Dispatcher.getServerTimeInMillis();
        //localUpdateTime = updateTime; // if a change occures, the whole document made a change
        localUpdateTime = updateTime;

        log.debug("LocalUpDateTime:"+localUpdateTime,7);
        log.debug("LastKnownUpdateTime OR actualDbTime:"+lastKnownDbTime,7);

    }

    /**
     * creates from a metadata object a dom tree
     * @return generated dom node
     * @throws ParserConfigurationException
     */
    public Node getDomContent() throws ParserConfigurationException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();

        Document doc = builder.newDocument();

        doc.appendChild(doc.createElement("mediatype"));
        Element root = doc.getDocumentElement();

        //only for update and merging use
        root.setAttribute("actualDbTime", lastKnownDbTime);
        root.setAttribute("localDbTime", localUpdateTime);
        root.setAttribute("workspace", workspace);

        Element titleElement = doc.createElement("title");
        titleElement.appendChild(doc.createTextNode(dc_title));
//        titleElement.setAttribute("updateTime", dc_title_creationTime);
        root.appendChild(titleElement);

        Element creatorElement = doc.createElement("creator");
        creatorElement.appendChild(doc.createTextNode(dc_creator));
//        creatorElement.setAttribute("updateTime", dc_creator_creationTime);
        root.appendChild(creatorElement);

        Element subjectElement = doc.createElement("subject");
        subjectElement.appendChild(doc.createTextNode(dc_subject));
//        subjectElement.setAttribute("updateTime", dc_subject_creationTime);
        root.appendChild(subjectElement);

        Element descriptionElement = doc.createElement("description");
        descriptionElement.appendChild(doc.createTextNode(dc_description));
//        descriptionElement.setAttribute("updateTime", dc_description_creationTime);
        root.appendChild(descriptionElement);

        Element publisherElement = doc.createElement("publisher");
        publisherElement.appendChild(doc.createTextNode(dc_publisher));
//        publisherElement.setAttribute("updateTime", dc_publisher_creationTime);
        root.appendChild(publisherElement);

        Element contributorsElement = doc.createElement("contributors");
        contributorsElement.appendChild(doc.createTextNode(dc_contributors));
//        contributorsElement.setAttribute("updateTime", dc_contributors_creationTime);
        root.appendChild(contributorsElement);

        Element dateElement = doc.createElement("date");
        if (dc_date != null) {
            dateElement.appendChild(doc.createTextNode(convertDate(dc_date)));
//            dateElement.setAttribute("updateTime", dc_date_creationTime);
        } else {
            dateElement.appendChild(doc.createTextNode(""));
//            dateElement.setAttribute("updateTime", dc_date_creationTime);
        }
        root.appendChild(dateElement);

        Element typeElement = doc.createElement("type");
        typeElement.appendChild(doc.createTextNode(dc_type));
//        typeElement.setAttribute("updateTime", dc_type_creationTime);
        root.appendChild(typeElement);

        Element formatElement = doc.createElement("format");
        formatElement.appendChild(doc.createTextNode(dc_format));
//        formatElement.setAttribute("updateTime", dc_format_creationTime);
        root.appendChild(formatElement);

        Element identifierElement = doc.createElement("identifier");
        identifierElement.appendChild(doc.createTextNode(dc_identifier));
//        identifierElement.setAttribute("updateTime", dc_identifier_creationTime);
        root.appendChild(identifierElement);

        Element sourceElement = doc.createElement("source");
        sourceElement.appendChild(doc.createTextNode(dc_source));
//        sourceElement.setAttribute("updateTime", dc_source_creationTime);
        root.appendChild(sourceElement);

        Element languageElement = doc.createElement("language");
        languageElement.appendChild(doc.createTextNode(dc_language));
//        languageElement.setAttribute("updateTime", dc_language_creationTime);
        root.appendChild(languageElement);

        Element relationElement = doc.createElement("relation");
        relationElement.appendChild(doc.createTextNode(dc_relation));
//        relationElement.setAttribute("updateTime", dc_relation_creationTime);
        root.appendChild(relationElement);

        Element coverageElement = doc.createElement("coverage");
        coverageElement.appendChild(doc.createTextNode(dc_coverage));
//        coverageElement.setAttribute("updateTime", dc_coverage_creationTime);
        root.appendChild(coverageElement);

        Element rightsElement = doc.createElement("rights");
        rightsElement.appendChild(doc.createTextNode(dc_rights));
//        rightsElement.setAttribute("updateTime", dc_rights_creationTime);
        root.appendChild(rightsElement);

        Element branchElement = doc.createElement("branch");
        branchElement.appendChild(doc.createTextNode(liabolo_branch));
//        branchElement.setAttribute("updateTime", uniol_branch_creationTime);
        root.appendChild(branchElement);

        Element signatureElement = doc.createElement("signature");
        signatureElement.appendChild(doc.createTextNode(liabolo_signature));
//        signatureElement.setAttribute("updateTime", uniol_signature_creationTime);
        root.appendChild(signatureElement);

        // Only for debugging
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        TransformerFactory factory = TransformerFactory.newInstance();
        try {
            Transformer transformer = factory.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(stream));

        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }

        log.debug(stream.toString(), 5);

        //return doc;
        return root;
    }

    /**
     * Produces a libitem from a given node
     * @param node
     * @return libitem
     */
    public static LibItem nodeToLibItem(Node node) {
        MetaData metaData = new MetaData(((Element) node).getAttribute("actualDbTime"));
        metaData.setLocalUpdateTime(((Element) node).getAttribute("localDbTime"));
        if(((Element) node).getAttribute("workspace")!=null && ((Element) node).getAttribute("workspace").equals("1"))
            metaData.isInWorkspace(true);
        else
            metaData.isInWorkspace(false);

        NodeList children = node.getChildNodes();
        for (int a = 0; a < children.getLength(); a++) {
            Node actChild = children.item(a);
            String nodeName = actChild.getNodeName();
            //log.debug("Node name:" + actChild.getNodeName(), 9);
            if (actChild.hasChildNodes()) {
                //log.debug(((Text) actChild.getFirstChild()).getData(), 9);
                Text firstChild = (Text) actChild.getFirstChild();
                String data;
                if (firstChild != null)
                    data = firstChild.getData();
                else
                    data = "";

                if (nodeName.equals("title")) {
                    metaData.dc_title = data;
//                    metaData.dc_title_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("creator")) {
                    metaData.dc_creator = data;
//                    metaData.dc_creator_creationTime=((Element) actChild).getAttribute("updateTime");
                }

                if (nodeName.equals("subject")) {
                    metaData.dc_subject = data;
//                    metaData.dc_subject_creationTime=((Element) actChild).getAttribute("updateTime");
                }

                if (nodeName.equals("description")) {
                    metaData.dc_description = data;
//                    metaData.dc_description_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("publisher")) {
                    metaData.dc_publisher = data;
//                    metaData.dc_publisher_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("contributors")) {
                    metaData.dc_contributors = data;
//                    metaData.dc_contributors_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("date")) {
                    metaData.dc_date = convertDate(data);
//                    metaData.dc_date_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("type")) {
                    metaData.dc_type = data;
//                    metaData.dc_type_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("format")) {
                    metaData.dc_format = data;
//                    metaData.dc_format_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("identifier")) {
                    metaData.dc_identifier = data;
//                    metaData.dc_identifier_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("source")) {
                    metaData.dc_source = data;
//                    metaData.dc_source_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("language")) {
                    metaData.dc_language = data;
//                    metaData.dc_language_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("relation")) {
                    metaData.dc_relation = data;
//                    metaData.dc_relation_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("coverage")) {
                    metaData.dc_coverage = data;
//                    metaData.dc_coverage_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("rights")) {
                    metaData.dc_rights = data;
//                    metaData.dc_rights_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("branch")) {
                    metaData.liabolo_branch = data;
//                    metaData.uniol_branch_creationTime=((Element) actChild).getAttribute("updateTime");
                }
                if (nodeName.equals("signature")) {
                    metaData.liabolo_signature = data;
//                    metaData.uniol_signature_creationTime=((Element) actChild).getAttribute("updateTime");
                }
            }
        }

        LibItem libItem = new LibItem();
        libItem.setMetaData(metaData);
        return libItem;
    }

    public static String convertDate(Date date){
        String formattedDate = "";
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy'-'MM'-'dd");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

        if(date!=null)
            formattedDate = formatter.format(date);

        return formattedDate;
    }

    public static Date convertDate(String date){
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy'-'MM'-'dd");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date formattedDate = null;
        try {
            if(date!=null && !date.equals("")){
                formattedDate = formatter.parse(date);
            }
        } catch (ParseException e) {
            log.error("Error occured while parsing Date ..");
        }
        return formattedDate;
    }

}
