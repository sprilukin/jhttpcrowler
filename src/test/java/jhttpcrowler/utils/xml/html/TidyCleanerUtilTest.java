package jhttpcrowler.utils.xml.html;

import jhttpcrowler.utils.xml.xpath.XpathUtil;
import org.junit.Test;
import org.w3c.dom.Node;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link jhttpcrowler.utils.xml.html.TidyCleanerUtil}
 *
 * @author Sergey Prilukin
 */
public class TidyCleanerUtilTest {

    @Test
    public void testHtmlToXml() throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("jhttpcrowler/utils/xml/bad_html.html");
        Node cleaned = TidyCleanerUtil.toXml(is);

        assertNotNull(cleaned);
        String str = XpathUtil.string(cleaned, "*//h1/text()");
        assertEquals("CONTENT", str);
    }

}
