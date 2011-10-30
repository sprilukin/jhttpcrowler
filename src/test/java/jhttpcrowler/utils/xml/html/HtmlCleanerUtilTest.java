package jhttpcrowler.utils.xml.html;

import jhttpcrowler.utils.xml.xpath.XpathUtil;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link HtmlCleanerUtil}
 *
 * @author Sergey Prilukin
 */
public class HtmlCleanerUtilTest {

    @Test
    public void testHtmlToXml() throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("jhttpcrowler/utils/xml/bad_html.html");
        String cleaned = (String) HtmlCleanerUtil.clean(is, HtmlCleanerConst.TO_PRETTY_STRING);
        assertNotNull(cleaned);
        String str = XpathUtil.string(cleaned, "*//h1/text()");
        assertEquals("CONTENT", str);
    }

}
