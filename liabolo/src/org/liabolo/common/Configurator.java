package org.liabolo.common;


import java.io.File;
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
	

    public static void config() {
//        try {
            if(System.getProperty("liabolo.config")!=null)
                configMainDir = System.getProperty("liabolo.config");
            if(System.getProperty("liabolo.log")!=null)
                logMainDir = System.getProperty("liabolo.log");
            if(System.getProperty("exist.home")==null)
                System.setProperty("exist.home","../exist");
              
            File file = new File(configMainDir+"/config.xml");
			handler = new XMLFileHandler();
			handler.setFile(file);
			try {
			cm.load(handler,"liaboloConfig");
			config = cm.getConfiguration("liaboloConfig");
			} catch (Exception e){
				System.err.println(e);
			}


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


}
