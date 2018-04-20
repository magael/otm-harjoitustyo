
package mj.platformer.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

public class LevelDataReader {
    
    public ArrayList<String> readLevelFile(String filePath) throws Exception {
        String lvlDataLine = "";
        ArrayList<String> lvlData = new ArrayList<>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream is = cl.getResourceAsStream(filePath);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((lvlDataLine = br.readLine()) != null) {
                while (lvlDataLine.startsWith("#")) {
                    lvlDataLine = br.readLine();
                }
                lvlData.add(lvlDataLine);
            }
        } catch (Exception e) {
            e.printStackTrace(new PrintStream(System.out));
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return lvlData;
    }
}
