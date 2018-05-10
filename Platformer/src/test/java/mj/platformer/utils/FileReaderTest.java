package mj.platformer.utils;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileReaderTest {

    private CustomFileReader fr;
    private String filePath;

    @Before
    public void setUp() {
        fr = new CustomFileReader();
        filePath = "test/test_file.txt";
    }

    @Test
    public void readFileReturnsCorrectData() throws Exception {
        ArrayList<String> dataArray = fr.readFile(filePath);
        assertEquals("0123", dataArray.get(0));
        assertEquals("1, 2, 3", dataArray.get(1));
        assertEquals("asdasd", dataArray.get(2));
        assertEquals(3, dataArray.size());
    }
    
    @Test(expected = Exception.class)
    public void throwsExceptionIfBadFilePath() throws Exception {
        String filePath = "wrong/no_file.bad";
        ArrayList<String> dataArray = fr.readFile(filePath);
    }
}
