/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.Random;

/**
 *
 * @author xandi
 */
public class StringCorrection {

    public static String cleanStringForDisplay(String s) {

        //  UniversitÃ© du QuÃ©bec Ã  MontrÃ©al. "Ab
        s = s.replace("Ã„", "Ä");
        s = s.replace("Ã¤", "ä");
        //s = s.replace("Ã¤", "");
        s = s.replace("Ã–„", "Ö");
        s = s.replace("Ã–", "Ö");
        s = s.replace("Ã¼", "ö");
        s = s.replace("Ã¶", "ö");
        s = s.replace("Ã¶", "ö");
        s = s.replace("Ã¼", "ü");
        s = s.replace("ï¿½", "ü");
        s = s.replace("ÃŸ", "ß");
        s = s.replace("â€™", "'");
        s = s.replace("â€œ", "\"");
        s = s.replace("â€", "\"");
        s = s.replace("â€™", "\"");
        s = s.replace("â€œ", "\"");
        s = s.replace("â€", "\"");
        s = s.replace("#dpos;", "\"");
        s = s.replace("#apos;", "'");

        //   #dpos;          
        //  #apos; 
        // here all russian texts
        return s;
    }

    public static String cleanStringForSql(String s) {

        s = s.replace("\"", "#dpos;");
        s = s.replace("'", "#apos;");

        return s;
    }

    public static String cleanStringForFileName(String s) {

        String random = getString(50);

        s = s.replace(".", random);
        s = s.replace("\"", "-");
        s = s.replace("'", "-");
        s = s.replace("?", "-");
        s = s.replace("!", "-");
        s = s.replace("@", "-");
        s = s.replace("#", "-");
        s = s.replace("$", "-");
        s = s.replace("%", "-");
        s = s.replace("&", "-");
        s = s.replace("+", "-");
        s = s.replace("=", "-");
        s = s.replace(" ", "-");

        s = s.replace("О", "o");
        s = s.replace("Е", "e");
        s = s.replace("А", "a");
        s = s.replace("И", "i");
        s = s.replace("Н", "n");
        s = s.replace("Т", "t");
        s = s.replace("С", "s");
        s = s.replace("Л", "l");
        s = s.replace("В", "b");
        s = s.replace("Р", "r");
        s = s.replace("К", "k");
        s = s.replace("М", "m");
        s = s.replace("Д", "d");
        s = s.replace("П", "p");
        s = s.replace("Ы", "i");
        s = s.replace("У", "u");
        s = s.replace("Б", "b");
        s = s.replace("Я", "j");
        s = s.replace("Ь", "b");
        s = s.replace("Г", "g");
        s = s.replace("З", "s");
        s = s.replace("Ч", "z");
        s = s.replace("Й", "i");
        s = s.replace("Ж", "sch");
        s = s.replace("Х", "ch");
        s = s.replace("Ш", "sch");
        s = s.replace("Ю", "ju");
        s = s.replace("Ц", "z");
        s = s.replace("Э", "e");
        s = s.replace("Щ", "sch");
        s = s.replace("Ф", "f");
        s = s.replace("Ё", "e");
        s = s.replace("Ъ", "b");

        s = s.replace("о", "o");
        s = s.replace("е", "e");
        s = s.replace("а", "a");
        s = s.replace("и", "i");
        s = s.replace("н", "n");
        s = s.replace("т", "t");
        s = s.replace("с", "s");
        s = s.replace("л", "l");
        s = s.replace("в", "b");
        s = s.replace("р", "r");
        s = s.replace("к", "k");
        s = s.replace("м", "m");
        s = s.replace("д", "d");
        s = s.replace("п", "p");
        s = s.replace("ы", "i");
        s = s.replace("у", "u");
        s = s.replace("б", "b");
        s = s.replace("я", "j");
        s = s.replace("ь", "b");
        s = s.replace("г", "g");
        s = s.replace("з", "s");
        s = s.replace("ч", "z");
        s = s.replace("й", "i");
        s = s.replace("ж", "sch");
        s = s.replace("х", "ch");
        s = s.replace("ш", "sch");
        s = s.replace("ю", "ju");
        s = s.replace("ц", "z");
        s = s.replace("э", "e");
        s = s.replace("щ", "sch");
        s = s.replace("ф", "f");
        s = s.replace("ё", "e");
        s = s.replace("ъ", "b");

        String oneChar;
        boolean tst = true;
        int numOfchar;
        String ret = "";

        for (int i = 0; i < s.length(); i++) {

            oneChar = s.substring(i, i + 1);

            numOfchar = s.charAt(i);

            tst = false;
            
            if (numOfchar >= 48 && numOfchar <= 57) {
                tst = true;
            }

            if (numOfchar >= 65 && numOfchar <= 90) {
                tst = true;
            }

            if (numOfchar >= 97 && numOfchar <= 122) {
                tst = true;
            }

            if (numOfchar >= 1072 && numOfchar <= 1103) {
                System.out.println(numOfchar);
                oneChar = getString(1);
                System.out.println(oneChar);
                tst = true;
            }

            if (numOfchar >= 1040 && numOfchar <= 1071) {
                oneChar = getString(1);
                tst = true;
            }

            if (numOfchar >= 192 && numOfchar <= 255) {
                tst = true;
            }

            if (numOfchar == 246) {
                tst = true;
            }

            if (numOfchar == 228) {
                tst = true;
            }

            if (numOfchar == 252) {
                tst = true;
            }

            if (numOfchar == 223) {
                tst = true;
            }

            if (numOfchar == 214) {
                tst = true;
            }

            if (numOfchar == 196) {
                tst = true;
            }

            if (numOfchar == 220) {
                tst = true;
            }

            if (numOfchar == 38) {
                tst = true;
            }

            if (numOfchar == 59) {
                tst = true;
            }
            if (numOfchar == 8211) {
                oneChar = getString(1);
                tst = true;
            }
            if (numOfchar == 45) {
                tst = true;
            }
            if (numOfchar == 8217) {
                oneChar = getString(1);
                tst = true;
            }
            if (numOfchar == 1105) {
                oneChar = getString(1);
                tst = true;
            }
            if (numOfchar == 1110) {
                oneChar = getString(1);
                tst = true;
            }
            if (numOfchar == 1111) {
                oneChar = getString(1);
                tst = true;
            }
            if (numOfchar == 96) {
                tst = true;
            }
            if (numOfchar == 8722) {
                oneChar = getString(1);
                tst = true;
            }

            if (tst) {
                ret += oneChar;
            } else {
                ret += "-";
            }
        }

        ret = ret.replace(random, ".");
        return ret;
    }

    private static String getString(int l) {

        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < l; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }

}
