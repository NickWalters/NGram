/*
 * Utility function for reading a file into a list of strings
 * 
 * e.g. calling FileIO.readFile("f.txt");
 * where file f.txt contains "abc\nde\n\ngh\n" 
 * will return an array list <"abc", "de", "", "gh">
 * 
 * @author Rachel Cardell-Oliver
 * based on Barnes and Koelling ResponseReader class
 * @version May 2017
 */

import java.io.*;
import java.util.*;


public class FileIO 
{   
    /**
     * Read a file from the current directory into a list
     * @param String filename name of the file to be read
     * @throws FileNotFoundException, IOException
     * @return ArrayList<String> of lines from the file
     */
    public static ArrayList<String> readFile(String filename) 
        throws FileNotFoundException, IOException
    {
        ArrayList<String> filelines = new ArrayList<String>();

        BufferedReader reader = 
            new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        while(line != null) {
            filelines.add(line);
            line = reader.readLine();            
        }
        reader.close();
        return filelines;
    }
}