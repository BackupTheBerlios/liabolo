package org.liabolo.common;

import org.liabolo.client.offline.Gui;
import org.liabolo.exception.ServiceNotAvailableException;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class TextItem {
	
	
	public static final short DC_CREATOR = 0;
	public static final short DC_TITLE = 1;
	public static final short DC_PUBLISHER = 2;
	public static final short DC_DATE = 3;
	public static final short DC_RELATION = 4;
	public static final short DC_SOURCE = 5;
	//public static final short UNIOL_LOCATION = 6;
	public static final short DC_COVERAGE = 6; // Location
	public static final short DC_LANGUAGE = 7;
	public static final short DC_DESCRIPTION = 8;
	public static final short DC_SUBJECT = 9;
	public static final short DC_IDENTIFIER = 10;
	public static final short DC_TYPE = 11;		//Automated
	public static final short DC_FORMAT = 12; // Disable
	public static final short DC_CONTRIBUTORS = 13;// Disable
	public static final short DC_RIGHTS = 14; // Disable

    public static final short TEXTFIELD = 0;
    public static final short TEXTAREA = 1;
	public static final short SIMPLE_COMBOBOX = 2;
    public static final short DATE_TEXTFIELD = 3;
    
	private short DCid = 0;
	private String name = "";
	private String desc = "";
	private short type = 0;

    public TextItem() {
    }

    public TextItem(short DCid, String name, String desc, short type) {
		this.DCid = DCid;
        this.name = name;
        this.desc = desc;
        this.type = type;

    }
   
	public short getDCid() {
		return DCid;
	}

	public void setDCid(short DCid) {
		this.DCid = DCid;
	} 

    public String getLocalizedName() {
        return Gui.lang.getString(name);
    }

    public String getKeyName() {
        return name;
    }    
    
    public void setName(String name) {
        this.name = name;
    }

    public String getLocalizedDescription() {
        return Gui.lang.getString(desc);
    }
    
    public String getKeyDescription() {
        return desc;
    }    

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public static ArrayList getGenericMediaTypeItems() throws ServiceNotAvailableException {
        if (Gui.lang != null) {
            ArrayList items = new ArrayList();

            	items.add(new TextItem(TextItem.DC_CREATOR, "creator", "creator_desc", TextItem.TEXTFIELD));
				items.add(new TextItem(TextItem.DC_TITLE,"title", "title_desc", TextItem.TEXTFIELD));
				items.add(new TextItem(TextItem.DC_PUBLISHER,"publisher", "publisher_desc", TextItem.TEXTFIELD));
				items.add(new TextItem(TextItem.DC_DATE, "date", "date_desc", TextItem.DATE_TEXTFIELD));
				items.add(new TextItem(TextItem.DC_RELATION,"relation","relation_desc", TextItem.TEXTFIELD));
				items.add(new TextItem(TextItem.DC_SOURCE, "source", "source_desc", TextItem.TEXTFIELD));
				items.add(new TextItem(TextItem.DC_COVERAGE,"coverage", "coverage_desc", TextItem.SIMPLE_COMBOBOX));
				items.add(new TextItem(TextItem.DC_LANGUAGE,"language", "language_desc", TextItem.TEXTFIELD));
                items.add(new TextItem(TextItem.DC_DESCRIPTION,"description", "description_desc", TextItem.TEXTAREA));
				items.add(new TextItem(TextItem.DC_SUBJECT, "subject", "subject_desc", TextItem.TEXTFIELD));
				items.add(new TextItem(TextItem.DC_IDENTIFIER, "identifier", "identifier_desc", TextItem.TEXTFIELD));
				items.add(new TextItem(TextItem.DC_FORMAT, "format", "format_desc", TextItem.TEXTFIELD));
                items.add(new TextItem(TextItem.DC_CONTRIBUTORS, "contributors", "contributors_desc", TextItem.TEXTFIELD));
               	// new TextItem(Gui.lang.getString("type"), Gui.lang.getString("type_desc"), TextItem.TEXTFIELD),
                items.add(new TextItem(TextItem.DC_RIGHTS,"rights", "rights_desc", TextItem.TEXTFIELD));

            return items;
        }else{
            throw new ServiceNotAvailableException("Language in Gui is not initialized!");
        }
    }


}
