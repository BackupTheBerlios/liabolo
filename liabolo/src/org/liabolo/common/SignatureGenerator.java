/*
 * Created on Jun 2, 2004 by Thorsten Schloermann
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
 */
package org.liabolo.common;


import java.net.URL;


public class SignatureGenerator {


    static private int number;
    static private String numberString;
    static private char letter1, letter2;
    static private boolean signatureRead = false;
    static boolean changeLetter1 = false;
    static boolean changeLetter2 = false;
    static boolean emptyError = false;

    private static Logger log = Logger.getLogger(SignatureGenerator.class);

    //Configurator.config();

    static private void readSignatureNumber() {

        number = Configurator.getIntProperty("signatureNumber", 0, "signature");
        letter1 = Configurator.getProperty("signatureLetters", "ZZ", "signature").charAt(0);
        letter2 = Configurator.getProperty("signatureLetters", "ZZ", "signature").charAt(1);
        signatureRead = true;
    }


    /** reads the last assigned signature and returns it incremented by one.
     * The returned signature is also stored as the new last assigned signature
     * @return String signature
     */

    public static Signature setSignature(String branch, String url){
        String signatureNumber = "";
 		url = url.replaceFirst("^.*://", "");
 
        //get the signature from the server, where the libitem should be stored
       if (!url.equals("")) {
 
            SignatureService service = new SignatureServiceLocator();
            try {
//TODO The signatureGenerator1 should refenced by each connection different
                SignatureSender sender = service.getSignatureSender(new URL(Configurator.getProperty("signatureGenerator1","", "connections")));
                signatureNumber = sender.getSignature();
            } catch (Exception e) {
                log.error("Could not retrieve signature from "+url+".");
                return null;
            }
        } else {//local signature

            if (!signatureRead) readSignatureNumber();
            number++;
            if (number == 10000) {
                number = 1;
                changeLetter();
                if (emptyError) return null;
            }
            numberString = Integer.toString(number);

            /* the following switch statement is for making the number String always
             four digits long (there may be a more elegant way, but I don't know of it) */
            switch (numberString.length()) {
                case 1:
                    numberString = "000" + numberString;
                    break;
                case 2:
                    numberString = "00" + numberString;
                    break;
                case 3:
                    numberString = "0" + numberString;
            }

            // set properties and store them to file
            Configurator.setProperty("signatureLetters", "" + letter1 + letter2, "signature");
            Configurator.setProperty("signatureNumber", numberString, "signature");
            Configurator.store();

            signatureNumber =  ""+ letter1 + letter2 + numberString;
        }
        return new Signature(branch, signatureNumber, url);
    }

    private static void changeLetter() {
        short charNr;
        if (letter2 != 'Z') {
            charNr = ((short) letter2);
            charNr++;
            letter2 = ((char) charNr);
        } else {
            if (letter1 == 'Z') emptyError = true;
            letter2 = 'A';
            charNr = ((short) letter1);
            charNr++;
            letter1 = ((char) charNr);
        }
    }

    /** sets the counter for the signature back to AA0000
     *
     *
     */

    public static void resetSignature() {

        Configurator.setProperty("signatureNumber", Integer.toString(0), "signature");
        Configurator.setProperty("signatureLetters", "AA", "signature");

    }


}
