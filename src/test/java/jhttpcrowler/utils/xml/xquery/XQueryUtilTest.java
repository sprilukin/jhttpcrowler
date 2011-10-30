package jhttpcrowler.utils.xml.xquery;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link jhttpcrowler.utils.xml.xslt.XsltUtil}
 *
 * @author Sergey Prilukin
 */
public class XQueryUtilTest {
    private String resultXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<male>Deep</male>" +
            "<male>Kumar</male>" +
            "<female>Deepali</female>";

    @Test
    public void testXsltFromInputStream() throws Exception {
        InputStream xml = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("jhttpcrowler/utils/xml/xpath.xml");
        InputStream xquery = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("jhttpcrowler/utils/xml/xquery.xq");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XQueryUtil.transform(xml, xquery, os);
        String result = new String(os.toByteArray());
        assertEquals(resultXml.replaceAll("[\\r\\n\\s]+", ""), result.replaceAll("[\\r\\n\\s]+", ""));
    }
}
