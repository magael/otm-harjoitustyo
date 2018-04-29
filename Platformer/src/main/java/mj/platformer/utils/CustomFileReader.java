
package mj.platformer.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * The FileReader class.
 * @author Maguel
 */
public class CustomFileReader {
    
    /**
     * Reads a file from the given path and puts it's contents in a String array.
     * Lines starting with the character '#' are skipped.
     * 
     * @param filePath
     * @return the file data in a String ArrayList
     * @throws Exception 
     */
    public ArrayList<String> readFile(String filePath) throws Exception {
        String dataLine = "";
        ArrayList<String> stringDataList = new ArrayList<>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream is = cl.getResourceAsStream(filePath);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            while ((dataLine = br.readLine()) != null) {
                if (!dataLine.startsWith("#")) {
                    stringDataList.add(dataLine);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(new PrintStream(System.out));
        } finally {
            if (is != null) {
                is.close();
            }
        }
        
        return stringDataList;
    }
}
