
package org.liabolo.client.offline.common;

import java.awt.Container;
import java.awt.Cursor;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;

/**
 * 
 * @author Jurij Henne
 *
 * This is an inplementation of a link loader, which loads a desired URL in a specified container(JEditorPane)
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LinkLoader implements Runnable
{
    /** Container for URL */
    private JEditorPane html;
    /** URL to be shown */
    private URL  url;
    /** Cursor to be shown while loading URL */
    private Cursor cursor;

	/**
	 *  Creates a new link loader 
	 * @param html Container for URL
	 * @param url URL to be shown
	 * @param cursor Cursor to be shown while loading URL
	 */ 
	public LinkLoader(JEditorPane html, URL url, Cursor cursor ) 
    {
        this.html = html;
        this.url = url;
        this.cursor = cursor;
    }

	/** Implementation of inherited method run() */
    public void run() 
    {
	    if( url == null ) 
	    {
    		// restore the original cursor
	    	if(cursor!=null)
	    		html.setCursor( cursor );

    		// PENDING(prinz) remove this hack when
    		// automatic validation is activated.
    		Container parent = html.getParent();
    		parent.repaint();
        }
        else 
        {
    		Document doc = html.getDocument();
	    	try {
		        html.setPage( url );
    		}
    		catch( IOException ioe ) 
    		{
    		    html.setDocument( doc );
    		}
    		finally
    		{
    		    // schedule the cursor to revert after
	    	    // the paint has happended.
		        url = null;
    		    SwingUtilities.invokeLater( this );
	    	}
	    }
	}
}


