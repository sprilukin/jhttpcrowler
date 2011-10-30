package jhttpcrowler.utils.xml.xslt;

import jhttpcrowler.utils.xml.XmlUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.w3c.dom.Node;

import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link XsltUtil}
 *
 * @author Sergey Prilukin
 */
public class XsltUtilTest {
    private String resultXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<transform>\n" +
            "  <record>\n" +
            "<username>MP123456</username>\n" +
            "<fullname>John Smith</fullname>\n" +
            "</record>\n" +
            "  <record>\n" +
            "<username>PK123456</username>\n" +
            "<fullname>Jim Who</fullname>\n" +
            "</record>\n" +
            "</transform>\n";

    @Test
    public void testXsltFromInputStream() throws Exception {
        InputStream xml = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("jhttpcrowler/utils/xml/xslt.xml");
        InputStream xsl = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("jhttpcrowler/utils/xml/xslt.xsl");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XsltUtil.transform(xml, xsl, os);
        String result = new String(os.toByteArray());
        assertEquals(resultXml.replaceAll("[\\r\\n\\s]+", ""), result.replaceAll("[\\r\\n\\s]+", ""));
    }

    @Test
    public void testXsltFromNodeSource() throws Exception {
        InputStream xml = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("jhttpcrowler/utils/xml/xslt.xml");
        InputStream xsl = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("jhttpcrowler/utils/xml/xslt.xsl");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XsltUtil.transform(XmlUtil.toXml(xml), XmlUtil.toXml(xsl), os);
        String result = new String(os.toByteArray());
        assertEquals(resultXml.replaceAll("[\\r\\n\\s]+", ""), result.replaceAll("[\\r\\n\\s]+", ""));
    }

    @Test
    public void testXsltForNodeOutput() throws Exception {
        InputStream xml = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("jhttpcrowler/utils/xml/xslt.xml");
        InputStream xsl = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("jhttpcrowler/utils/xml/xslt.xsl");

        Node node = XmlUtil.newXml();

        XsltUtil.transform(xml, xsl, node);
        String result = XsltUtil.xmlToString(node);
        assertEquals(resultXml.replaceAll("[\\r\\n\\s]+", ""), result.replaceAll("[\\r\\n\\s]+", ""));
    }

    @Test(expected = TransformerException.class)
    public void testXsltIncorrectSource() throws Exception {
        String xml = "bad xml";
        InputStream xsl = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("jhttpcrowler/utils/xml/xslt.xsl");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XsltUtil.transform(xml, xsl, os);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testXsltNotSupportedSource() throws Exception {
        Object xml = new Object();
        InputStream xsl = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("jhttpcrowler/utils/xml/xslt.xsl");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XsltUtil.transform(xml, xsl, os);
    }

    @Test
    public void testXmlToString() throws Exception {
        InputStream xml = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("jhttpcrowler/utils/xml/xslt.xml");

        String origResult = new String(IOUtils.toByteArray(xml));

        String result = XsltUtil.xmlToString(origResult);
        assertEquals(origResult.replaceAll("[\\r\\n\\s]+", ""), result.replaceAll("[\\r\\n\\s]+", ""));
    }
}
