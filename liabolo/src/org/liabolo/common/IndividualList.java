package org.liabolo.common;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Collection;

public class IndividualList {
    private Hashtable items;
    private String name;
    private String description;

    public IndividualList(String name, String description) {
        this.name = name;
        this.description = description;

        items = new Hashtable();
        description = "";
    }

    public String getListName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void addItem(String id, String description) {
        if (id != null)
            if (description != null)
                items.put(id, description);
            else
                items.put(id, "");
    }

    public void removeItem(String id) {
        items.remove(id);
    }

    public Hashtable getItems(){
        return this.items;
    }

    public int countItems(){
        return items.size();
    }

    public Node getDomContent() throws ParserConfigurationException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();

        Document doc = builder.newDocument();

        doc.appendChild(doc.createElement("individuallist"));
        Element root = doc.getDocumentElement();

        //only for update and merging use
        root.setAttribute("name", name);
        root.setAttribute("desc", description);

        String actKey;
        String desc;
        Enumeration keys = items.keys();
        while (keys.hasMoreElements()) {
            actKey = (String) keys.nextElement();
            if (items.get(actKey) != null)
                desc = (String) items.get(actKey);
            else
                desc = "";


            Element itemElem = ((Element) root.appendChild(doc.createElement("item")));
            itemElem.setAttribute("id", actKey);
            itemElem.setAttribute("desc", desc);
            root.appendChild(itemElem);
        }

        return root;
    }


    /**
     * Converts a node into an individualList
     * @param node
     * @return IndividualList  or null
     */
    public static IndividualList nodeToIndividualList(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            String listName = ((Element) node).getAttribute("name");
            String listDesc = ((Element) node).getAttribute("desc");

            IndividualList list = new IndividualList(listName, listDesc);

            NodeList children = node.getChildNodes();
            for (int a = 0; a < children.getLength(); a++) {
                Node actChild = children.item(a);
                if (actChild.getNodeType() == Node.ELEMENT_NODE) {
                    list.addItem(((Element) actChild).getAttribute("id"), ((Element) actChild).getAttribute("desc"));
                }
            }
            return list;
        } else {
            return null;
        }
    }


}
