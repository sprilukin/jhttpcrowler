package jhttpcrowler.utils.file;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test for FileUtil
 *
 * @author Sergey Pilukin
 */
public class FileUtilTest {

    private byte[] bytesToSave;

    @Before
    public void initialize() throws Exception {
        InputStream fileResource = Thread.currentThread().getContextClassLoader().getResourceAsStream("jhttpcrowler/utils/file/test.txt");

        bytesToSave = IOUtils.toByteArray(fileResource);

        fileResource.close();
    }

    @Test
    public void testFileUtil() throws Exception {
        String pathToSave = "/test.txt";
        FileUtil.saveBytesToFile(bytesToSave, pathToSave);

        String wholeFile = FileUtil.readFileToString(pathToSave);
        String line1 = wholeFile.replaceAll("\\r\\n", "\n").split("\\n")[0];
        String line2 = wholeFile.replaceAll("\\r\\n", "\n").split("\\n")[1];

        assertNotNull(line1);
        assertNotNull(line2);

        System.out.println("file.encoding: " + System.getProperty("file.encoding"));
        assertEquals("hello world!", line1);
        //assertEquals("привет мир!", line2);

        File testFile = new File(pathToSave);
        assertTrue(testFile.delete());
    }
}
