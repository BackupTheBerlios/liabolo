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

import org.liabolo.client.offline.Gui;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class Logger {

    /** Holds the name of the class this logger belongs to. */
    private String className;

    private static MessageReceiver receiver = null;

    private static int debugLevel = 4;

    private static int stackTraceLevel = 4;

    private boolean PAUSED = false;

    private static boolean headerWritten= false;

 	private static File file;






    /**
     * Creates a new instance of <CODE>Logger</CODE>.
     *
     * @param   classToLog  the class which needs this logger object
     */
    public Logger(Class classToLog) {
        file = new File(Configurator.logMainDir+"/log-"+getFormatedDate()+".txt");

        className = classToLog.getName();

		if (!headerWritten) {

			try {
				FileWriter fw = new FileWriter( file, true);
				fw.write("\n***************************************************************************\n");
				fw.write("Start logging for Liabolo-Session started on: " + new Date().toString()+"\n" );
				fw.write("***************************************************************************\n");
				fw.close();

			} catch (FileNotFoundException e){
			} catch (IOException e){
			}
		headerWritten = true;
		}
        debugLevel = Integer.parseInt(Configurator.getProperty("debugLevel"));
        stackTraceLevel = Integer.parseInt(Configurator.getProperty("stackTraceLevel"));

        System.out.println("Logger configured in "+classToLog.getName()+"! debugLevel="+debugLevel);
    }


    /**
     * Provides a <CODE>Logger</CODE> object logging for exactly the class which
     * calls this method.
     *
     * @param   c   the class which calls the logger
     * @return  a   new <CODE>Logger</CODE> for the calling class
     */
    public static Logger getLogger(Class c) {

        return new Logger(c);
    }


    /**
     * Logs an information to standard out as described above.
     *
     * @param   info    the information to be logged
     */
    public void info(String info) {
    	try {
			FileWriter fw = new FileWriter( file, true);
			fw.write(new Date().toString() + " - INFO - " + className + ":  " + info+ "\n");
			fw.close();
    	} catch (FileNotFoundException e){
    	} catch (IOException e){
    	}

        if (receiver != null && !PAUSED)
            receiver.setReceiverMessage(info);
    }

    /**
     * Logs an debug info to standard out as described above.
     *
     * @param   debug    the debug info to be logged
     * @param   level    the debug level
     */
    public void debug(String debug, int level) {
        if (level <= debugLevel) {
			try {
				FileWriter fw = new FileWriter( file, true);
				fw.write(new Date().toString() + " - DEBUG - " + className + ":  " + debug+ "\n");
				fw.close();

				} catch (FileNotFoundException e){
				} catch (IOException e){
				}
//          System.err.println(new Date() + " - DEBUG - " + className + ":  " + debug);
        }
    }

    public void debug(Exception e) {
        if (stackTraceLevel <= debugLevel && !PAUSED) {
            System.err.println(new Date());
            e.printStackTrace();
        }
    }

    /**
     * Logs an error to standard out as described above.
     *
     * @param   error    the error to be logged
     */
    public void error(String error) {
		try {
            FileWriter fw = new FileWriter( file, true);
            fw.write(new Date().toString() + " - ERROR - " + className + ":  " + error + "\n");
            fw.close();

			} catch (FileNotFoundException e){
			} catch (IOException e){
			}
        if (receiver != null)
            receiver.setReceiverMessage(error);
    }

    /**
     * Setter for property errorOn. Sets logging on error level on (<CODE>true</CODE>) or off
     * (<CODE>false</CODE>).
     *
     * @param   level  the debuglevel which determines how detailed the debug information should be
     */
    public static void setDebugLevel(int level) {
        debugLevel = level;
    }

    public static int getDebugLevel() {
        return debugLevel;
    }

    /**
     *  Resumes the temporary paused logging
     */
    public void resume(){
        PAUSED = false;
    }

    /**
     * Stoppes the logging for a short time to reduce the info-message count
     */
    public void pause(){
        PAUSED = true;
    }

    public static void registerAsReceiver(MessageReceiver receiver) {
        Logger.receiver = receiver;
    }

    public static String getFormatedDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy'-'MM'-'dd");
		java.util.Date today = new java.util.Date();
		String foramttedDate = formatter.format(today);
		return foramttedDate;
    }
}
