package mj.platformer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * The FileReader class.
 *
 * @author Maguel
 */
public class CustomFileReader {

    /**
     * Reads a file from the given path and puts it's contents in a String
     * array. Lines starting with the character '#' are skipped.
     *
     * @param filePath
     * @return the file data in a String ArrayList
     */
    public ArrayList<String> readFile(String filePath) {
        ArrayList<String> stringDataList = new ArrayList<>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        try {
            InputStream is = cl.getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            addLines(br, stringDataList);
            is.close();
        } catch (Exception e) {
            System.out.println("Error when attempting to read a file into an array: " + e.getMessage());
        }

        return stringDataList;
    }

    public void addLines(BufferedReader br, ArrayList<String> stringDataList) throws IOException {
        String dataLine;
        while ((dataLine = br.readLine()) != null) {
            if (!dataLine.startsWith("#")) {
                stringDataList.add(dataLine);
            }
        }
    }
}
