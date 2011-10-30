package jhttpcrowler.utils.strings;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.List;

/**
 * Test class for testing {@link StringUtil}
 *
 * @author Sergey Prilukin
 */
public class StringUtilTest {

    @Test
    public void testEqualsIgnoreCharset() throws Exception {
        String testString = "тест";

        assertTrue(StringUtil.equalsIgnoreCharset(testString, new String(testString.getBytes("UTF-8"), "windows-1251")));
    }

    @Test
    public void testSearchAll() throws Exception {
        String testedString = "hello 12345";
        String regexp = "([\\d]{2})";

        List<String> searchResult = StringUtil.searchAll(testedString, regexp, 1, 0);
        assertNotNull(searchResult);
        assertEquals(2, searchResult.size());
        assertEquals("12", searchResult.get(0));
        assertEquals("34", searchResult.get(1));
    }
}
