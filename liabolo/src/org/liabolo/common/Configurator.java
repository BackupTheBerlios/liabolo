package org.liabolo.common;


import java.io.*;
import org.jconfig.*;
import org.jconfig.handler.*;


public class Configurator {

//   private static Properties config = new Properties();
	private static ConfigurationManager cm = ConfigurationManager.getInstance();
	private static Configuration config;
	private static XMLFileHandler handler;
//	private static Configuration lang;
//	private static String locale = "german";
    public static boolean configured = false;

    private static final String header = "This is the main configuration file for Liabolo";

    public static String configMainDir = "../config";
    public static String logMainDir = "../log";
    public static String userDir = System.getProperty("user.home");
    public static File liaboloDir;
	

    public static void config() {
//        try {
            if(System.getProperty("liabolo.config")!=null)
                configMainDir = System.getProperty("liabolo.config");
            if(System.getProperty("liabolo.log")!=null)
                logMainDir = System.getProperty("liabolo.log");
            if(System.getProperty("exist.home")==null)
                System.setProperty("exist.home","../exist");
              
            File configFile = new File(configMainDir+"/config.xml");
			handler = new XMLFileHandler();
			handler.setFile(configFile);
			try {
			cm.load(handler,"liaboloConfig");
			config = cm.getConfiguration("liaboloConfig");
			} catch (Exception e){
				System.err.println(e);
			}
			
			// check, if a liabolo-dir exist in user-dir. If not, create it and
			// copy user-specific files
			liaboloDir = new File(userDir, ".liabolo");
			if (!liaboloDir.exists()) {
				System.out.println("Liabolo-Directory not found! Creating it...");
				liaboloDir.mkdir();
			} else System.out.println("Liabolo-Directory exists!");
			System.out.println("Copy files...");
			fileCopy(configFile, "config.xml");
			// copy eXist files
			File collections = new File("exist/data","collections.dbx");
			File dom = new File("exist/data","dom.dbx");
			File elements = new File("exist/data","elements.dbx");
			File words = new File("exist/data","words.dbx");
			
			fileCopy(collections,"collections.dbx");
			fileCopy(dom,"dom.dbx");
			fileCopy(elements,"elements.dbx");
			fileCopy(words,"words.dbx");
			


            configured = true;

    }

    public static String getProperty(String key) {
        return config.getProperty(key);
    }
    
    public static String getProperty(String key, String value, String category){
    	return config.getProperty(key, value, category);
    }
    
    public static int getIntProperty(String key, int value, String category){
    	return config.getIntProperty(key, value, category);
    }

    public static void setProperty(String key, String value, String category){
        config.setProperty(key, value, category);
    }
    
    public static void setIntProperty(String key, int value, String category) {
    	config.setIntProperty(key, value, category);
    }
    
/*    public static String getString(String key){
    	return lang.getProperty(key,"not found",locale);
    }
*/

    public static void removeProperty(String key, String category){
        config.removeProperty(key, category);
    }


    public static boolean store(){
    	ConfigurationManager cm = ConfigurationManager.getInstance();
        try {
			
            cm.save(handler, config);
            return true;
        } catch (Exception e) {
            System.err.println("File "+configMainDir+"/config.xml not found!!");
            return false;
        }
    }
    
    private static void fileCopy(File source, String d){

    	File dest = new File(liaboloDir, d);
    	if (dest.exists()) return; // no need to copy file
    	
    	FileInputStream fis;
    	BufferedInputStream bis;
    	FileOutputStream fos;
    	BufferedOutputStream bos;
    	byte[] b;
    	
    	
    	try { // übergebene Dateien öffnen
    		fis = new FileInputStream(source);
    		fos = new FileOutputStream(dest);
    	} catch (FileNotFoundException ex) {
    		throw new Error("File not found");
    	}
    	
    	// Dateien umlenken auf Puffer
    	bis = new BufferedInputStream(fis);
    	bos = new BufferedOutputStream(fos);
    	int read;
    	try { // Datei lesen, schreiben und schließen
    		b = new byte[bis.available()];
    		while (true){
    			read = bis.read(b);
    			if (read == -1) break;
    			bos.write(b);
    		
    		}
    		bis.close();
    		bos.close();
    	} catch (IOException e) {
    		System.out.println("Files could not be copied!");
    	}
    } 

    	


}
